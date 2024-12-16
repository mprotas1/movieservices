INSERT INTO roles (role_type)
SELECT 'USER'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE role_type = 'USER'
);

INSERT INTO roles (role_type)
SELECT 'MODERATOR'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE role_type = 'MODERATOR'
);

INSERT INTO roles (role_type)
SELECT 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE role_type = 'ADMIN'
);