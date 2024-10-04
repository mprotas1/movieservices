CREATE TABLE IF NOT EXISTS `cinema_rooms`
(
    `id`        BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `number`    INTEGER       NOT NULL,
    `capacity`  INTEGER       NOT NULL,
    `cinema_id` BIGINT        NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `cinema_rooms` ADD CONSTRAINT `fk_cinema_rooms_cinema_id` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`);