CREATE TABLE IF NOT EXISTS teachers(
   id bigint not null GENERATED ALWAYS AS IDENTITY,
   name VARCHAR(255),
   PRIMARY KEY(id)
);