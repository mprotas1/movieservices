ALTER TABLE addresses DROP FOREIGN KEY fk_addresses_cinema_id;
ALTER TABLE cinemas DROP FOREIGN KEY fk_cinemas_address_id;
ALTER TABLE cinemas DROP COLUMN `address_id`;
DROP TABLE IF EXISTS addresses;

ALTER TABLE cinemas ADD COLUMN `city` VARCHAR(255) NOT NULL;
ALTER TABLE cinemas ADD COLUMN `street` VARCHAR(255) NOT NULL;
ALTER TABLE cinemas ADD COLUMN `postal_code` VARCHAR(255) NOT NULL;