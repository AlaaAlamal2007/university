-- Table: public.students

-- DROP TABLE IF EXISTS public.students;

CREATE TABLE IF NOT EXISTS public.students
(
id bigint NOT NULL DEFAULT nextval('students_id_seq'::regclass),
name character(50) COLLATE pg_catalog."default" NOT NULL,
gender character(50) COLLATE pg_catalog."default" NOT NULL,
graduated boolean NOT NULL,
payment_fee double precision,
email character(50) COLLATE pg_catalog."default",
university_id bigint,
address_id bigint,
birth_date timestamp without time zone,
graduated_date timestamp without time zone,
registration_date timestamp without time zone,
CONSTRAINT students_pkey PRIMARY KEY (id),
CONSTRAINT students_address_id_fkey FOREIGN KEY (address_id)
REFERENCES public.addresses (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID,
CONSTRAINT students_university_id_fkey FOREIGN KEY (university_id)
REFERENCES public.universities (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students
OWNER to postgres;
________________________________________________________
-- Table: public.addresses

-- DROP TABLE IF EXISTS public.addresses;

CREATE TABLE IF NOT EXISTS public.addresses
(
id bigint NOT NULL DEFAULT nextval('addresses_id_seq'::regclass),
city_name character(50) COLLATE pg_catalog."default",
street_name character(50) COLLATE pg_catalog."default",
street_number character(50) COLLATE pg_catalog."default",
CONSTRAINT addresses_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.addresses
OWNER to postgres;
_________________________________________________
-- Table: public.universities

-- DROP TABLE IF EXISTS public.universities;

CREATE TABLE IF NOT EXISTS public.universities
(
id bigint NOT NULL DEFAULT nextval('universities_id_seq'::regclass),
name character(50) COLLATE pg_catalog."default" NOT NULL,
university_type character(50) COLLATE pg_catalog."default" NOT NULL,
email character(50) COLLATE pg_catalog."default",
study_cost double precision,
address_id bigint,
start_operating_date timestamp without time zone,
CONSTRAINT universities_pkey PRIMARY KEY (id),
CONSTRAINT universities_address_id_fkey FOREIGN KEY (address_id)
REFERENCES public.addresses (id) MATCH SIMPLE
ON UPDATE NO ACTION
ON DELETE NO ACTION
NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.universities
OWNER to postgres;