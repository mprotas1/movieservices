CREATE TABLE IF NOT EXISTS `cinemas` (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `address_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `addresses` (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `street` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `postal_code` VARCHAR(20) NOT NULL,
    `cinema_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `cinemas` ADD CONSTRAINT `fk_cinemas_address_id` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
ALTER TABLE `addresses` ADD CONSTRAINT `fk_addresses_cinema_id` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`);
