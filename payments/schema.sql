CREATE TABLE IF NOT EXISTS payments (
    ID varchar(255) NOT NULL,
    reservation_id varchar(255) NOT NULL,
    user_id INT NOT NULL,
    amount float8 NOT NULL,
    status varchar(50) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    PRIMARY KEY (ID)
);