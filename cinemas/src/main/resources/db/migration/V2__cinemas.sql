CREATE TABLE IF NOT EXISTS `cinema_rooms`
(
    `id`        SERIAL UNIQUE NOT NULL,
    `number`    INTEGER       NOT NULL,
    `capacity`  INTEGER       NOT NULL,
    `cinema_id` BIGINT        NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`)
);