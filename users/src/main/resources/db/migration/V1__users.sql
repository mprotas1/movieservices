CREATE TABLE IF NOT EXISTS users (
   id SERIAL UNIQUE NOT NULL,
   email varchar(255) UNIQUE NOT NULL,
   password varchar(255) NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL UNIQUE NOT NULL,
    role_type varchar(100) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS user_roles (
    id SERIAL UNIQUE NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);