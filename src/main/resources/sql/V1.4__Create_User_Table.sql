CREATE TABLE IF NOT EXISTS users(
   id bigint not null GENERATED ALWAYS AS IDENTITY,
   first_name VARCHAR(255),
   Last_name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   PRIMARY KEY(id)
)
