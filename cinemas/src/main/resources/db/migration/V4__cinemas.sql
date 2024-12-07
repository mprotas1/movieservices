ALTER TABLE cinema_rooms DROP FOREIGN KEY fk_cinema_rooms_cinema_id;
ALTER TABLE cinemas DROP COLUMN `id`;
ALTER TABLE cinemas ADD COLUMN `uuid` BINARY(16) NOT NULL;
ALTER TABLE cinemas ADD PRIMARY KEY (`uuid`);
