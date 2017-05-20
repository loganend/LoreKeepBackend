package lorekeep.user.web;

import lorekeep.user.Session;
import lorekeep.user.SessionRepository;
import lorekeep.user.UserRepository;
import lorekeep.user.User;
import lorekeep.user.web.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserRepository userRpository;

    @Autowired
    private SessionRepository sessionRepository;


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {

        final User user = userRpository.findByUserId(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("User not exist")));
        }
        return ResponseEntity
                .ok(new Response("info",new SignUpMessage(user.getUsername())));
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUserById(@PathVariable("id") Long id) {

        if (httpSession.getAttribute("userId") != null && !id.equals((Long)httpSession.getAttribute("userId"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response("error",new ErrorMessage("Cannot remove other user")));
        }
        userRpository.removeByUserId(id);
        return ResponseEntity.ok(new Response("info", "User has removed"));
    }


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity signin(@RequestBody JsonSignin body,  HttpServletResponse response) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error", new ErrorMessage("Invalid data")));
        }

//        final User user = userRpository.findByUsernameAndPassword(login, password);
        final User user = userRpository.findByUsername(login);

        if (user == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error", new ErrorMessage("Incorrect login/password")));
        }


        httpSession.setAttribute("userId", user.getUserId());
        httpSession.setAttribute("login", login);

        Session session = new Session();
        session.setUserId(user.getUserId());
        session.setSessionId(httpSession.getId());
        sessionRepository.save(session);

//        Cookie cookie = new Cookie("foo", httpSession.getId());
//        cookie.setMaxAge(10000);
//        response.addCookie(cookie);

        return ResponseEntity.ok()
                .body(new Response("info",new SessionMessage(httpSession.getId(), user.getUserId())));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody JsonSignup body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();
        final String phoneNumber = body.getName();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Response("error",new ErrorMessage("Invalid data")));
        }

        final User userExist = userRpository.findByUsername(login);
        if (userExist != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("error",new ErrorMessage("User with this login already exist")));
        }

        final User userEmail = userRpository.findByEmail(email);
        if (userEmail != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response("error",new ErrorMessage("User with this email already exist")));
        }

        User user = new User();
        user.setUsername(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        user = userRpository.save(user);

        return ResponseEntity.ok(new Response("info",new AuthMessage(user.getUserId())));
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ResponseEntity<?> getSession() {
        if (httpSession.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("You aren't authenticated. Session is null!")));
        }
        return ResponseEntity.ok()
                .body(new Response("info",new GetSessionMessage(httpSession.getId())));
    }

    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSession() {
        if (httpSession.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("You aren't authenticated. Session is null!")));
        }
        httpSession.invalidate();
        return ResponseEntity.ok()
                .body(new Response("info","You are log out!"));
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity isauth(@CookieValue("sessionId") String sessionId){

        Session session = sessionRepository.findBySessionId(sessionId);

        if(session == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response("error", new ErrorMessage("Access allowed only for registered users")));
        }

        return ResponseEntity.ok().body(new Response("User logged in", session.getUserId()));
    }
}
