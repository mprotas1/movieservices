CREATE TABLE IF NOT EXISTS "user" (
    id bigint NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
)