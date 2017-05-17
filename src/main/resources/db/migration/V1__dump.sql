CREATE TABLE user (
  user_id bigserial primary key,
  email varchar(255),
  password varchar(255),
  phone_number varchar(255),
  username varchar(255)
);

CREATE TABLE topic (
  topic_id  bigserial primary key,
  user_id bitint REFERENCES user(user_id),
  title varchar(255),
  image bytea,
  color varchar(255),
  creation_date date,
  last_used date,
  rating integer,
  changed boolean
);

CREATE TABLE note (
  note_id bigserial primary key,
  topic_id bigint REFERENCES topic(topic_id),
  comment varchar(255),
  content varchar(255),
  url varchar(255),
  creation_date date,
  last_used date,
  image bytea,
  rating varchar(255),
  changed boolean
)
