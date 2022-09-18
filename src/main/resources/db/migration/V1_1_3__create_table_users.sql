CREATE TABLE users
(
    login              VARCHAR(255),
    role               VARCHAR(10),
    salt               VARCHAR(36),
    encrypted_password VARCHAR(255)
);