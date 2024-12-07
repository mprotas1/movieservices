ALTER TABLE seats ADD COLUMN seat_type VARCHAR(100) NOT NULL DEFAULT 'STANDARD';
ALTER TABLE cinema_rooms DROP COLUMN cinema_id;
ALTER TABLE cinema_rooms ADD COLUMN  cinema_id BINARY(16) NOT NULL;
ALTER TABLE cinema_rooms ADD CONSTRAINT fk_cinema_rooms_cinema_id FOREIGN KEY (cinema_id) REFERENCES cinemas (uuid);