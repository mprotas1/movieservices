CREATE TABLE IF NOT EXISTS payments (
    ID uuid NOT NULL,
    reservation_id uuid NOT NULL,
    user_id bigint NOT NULL,
    amount float8 NOT NULL,
    status varchar(50) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (ID)
);