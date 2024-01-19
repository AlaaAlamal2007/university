CREATE TABLE IF NOT EXISTS refresh_tokens(
   id bigint not null GENERATED ALWAYS AS IDENTITY,
   token VARCHAR(255),
   expiry_date TIMESTAMP without time zone,
   user_id bigint,
   PRIMARY KEY(id),
   CONSTRAINT fk_user FOREIGN KEY(user_id)
           REFERENCES users(id)
)
