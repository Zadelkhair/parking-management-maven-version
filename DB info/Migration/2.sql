ALTER TABLE reservations
ADD state int DEFAULT 0;

ALTER TABLE reservations
ADD payment_state int DEFAULT 0;