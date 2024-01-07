DO
$$
BEGIN
ALTER TABLE if exists addresses
    ALTER COLUMN street_number TYPE integer USING street_number::integer;
EXCEPTION
    WHEN undefined_column THEN RAISE NOTICE 'column street_number does not exist';
END
$$