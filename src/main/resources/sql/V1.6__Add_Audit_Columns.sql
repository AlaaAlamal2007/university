DO $$
    BEGIN
        BEGIN
            ALTER TABLE if exists addresses ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists addresses ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists addresses ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists addresses ADD COLUMN modified_at timestamp without time zone;

            ALTER TABLE if exists students ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists students ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists students ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists students ADD COLUMN modified_at timestamp without time zone;

            ALTER TABLE if exists subjects ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists subjects ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists subjects ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists subjects ADD COLUMN modified_at timestamp without time zone;

            ALTER TABLE if exists teachers ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists teachers ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists teachers ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists teachers ADD COLUMN modified_at timestamp without time zone;

            ALTER TABLE if exists universities ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists universities ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists universities ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists universities ADD COLUMN modified_at timestamp without time zone;

            ALTER TABLE if exists users ADD COLUMN added_by VARCHAR(255);
            ALTER TABLE if exists users ADD COLUMN modified_by VARCHAR(255);
            ALTER TABLE if exists users ADD COLUMN created_at timestamp without time zone;
            ALTER TABLE if exists users ADD COLUMN modified_at timestamp without time zone;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column added already exists in table.';
        END;
    END;
$$

