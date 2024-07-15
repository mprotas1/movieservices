CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL AUTOINCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles (
    id bigint NOT NULL AUTOINCREMENT,
    name varchar(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS user_roles (
    id bigint NOT NULL AUTOINCREMENT,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);