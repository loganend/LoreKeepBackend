package lorekeep.user.web.json;

public class AuthMessage {
    private Long userid;

    public AuthMessage(Long id) {
        this.userid = id;
    }

    public void setId(Long id) {
        this.userid = id;
    }

    public Long getId() {
        return userid;
    }
}
