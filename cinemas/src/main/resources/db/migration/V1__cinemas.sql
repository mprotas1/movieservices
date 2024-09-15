CREATE TABLE IF NOT EXISTS `cinemas` (
    `id` SERIAL UNIQUE NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `address_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`address_id`) REFERENCES `addresses`(`id`)
);

CREATE TABLE IF NOT EXISTS `addresses` (
    `id` SERIAL UNIQUE NOT NULL,
    `street` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `postal_code` VARCHAR(20) NOT NULL,
    `cinema_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cinema_id`) REFERENCES `cinemas`(`id`)
);