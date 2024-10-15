ALTER TABLE cinema_rooms DROP COLUMN `id`;
ALTER TABLE cinema_rooms ADD COLUMN `uuid` BINARY(16) NOT NULL;
ALTER TABLE cinema_rooms ADD PRIMARY KEY (`uuid`);

CREATE TABLE IF NOT EXISTS `seats` (
    `id` BINARY(16) UNIQUE NOT NULL PRIMARY KEY,
    `row` INTEGER NOT NULL,
    `seat_number` INTEGER NOT NULL,
    `cinema_room_id` BINARY(16) NOT NULL
);

ALTER TABLE `seats` ADD CONSTRAINT `fk_seats_cinema_rooms` FOREIGN KEY (`cinema_room_id`) REFERENCES `cinema_rooms` (`uuid`);