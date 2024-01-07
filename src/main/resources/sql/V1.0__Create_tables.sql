-- Table: public.addresses

-- DROP TABLE IF EXISTS public.addresses;

CREATE TABLE IF NOT EXISTS public.addresses
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    city_name character varying(255) COLLATE pg_catalog."default",
    street_name character varying(255) COLLATE pg_catalog."default",
    street_number character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT addresses_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.addresses
    OWNER to postgres;

-- Table: public.universities

-- DROP TABLE IF EXISTS public.universities;

CREATE TABLE IF NOT EXISTS public.universities
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    university_type character(50) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    study_cost double precision,
    address_id bigint,
    start_operating_date timestamp without time zone,
    CONSTRAINT universities_pkey PRIMARY KEY (id),
    CONSTRAINT fk_address FOREIGN KEY (address_id)
        REFERENCES public.addresses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.universities
    OWNER to postgres;

    -- Table: public.students

    -- DROP TABLE IF EXISTS public.students;

    CREATE TABLE IF NOT EXISTS public.students
    (
        id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
        name character varying(255) COLLATE pg_catalog."default" NOT NULL,
        gender character(50) COLLATE pg_catalog."default" NOT NULL,
        graduated boolean NOT NULL,
        payment_fee double precision,
        email character varying(255) COLLATE pg_catalog."default",
        university_id bigint NOT NULL,
        address_id bigint,
        birth_date timestamp without time zone,
        graduated_date timestamp without time zone,
        registration_date timestamp without time zone,
        CONSTRAINT students_pkey PRIMARY KEY (id),
        CONSTRAINT fk_address FOREIGN KEY (address_id)
            REFERENCES public.addresses (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT fk_university FOREIGN KEY (university_id)
            REFERENCES public.universities (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.students
        OWNER to postgres;

