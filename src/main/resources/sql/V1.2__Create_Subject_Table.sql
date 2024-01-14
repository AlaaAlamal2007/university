CREATE TABLE IF NOT EXISTS subjects(
   id bigint not null GENERATED ALWAYS AS IDENTITY,
   name VARCHAR(255),
   marks_obtained bigint ,
   PRIMARY KEY(id)
)