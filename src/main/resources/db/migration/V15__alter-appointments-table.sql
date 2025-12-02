ALTER TABLE appointments ADD COLUMN customer_id UUID NOT NULL;
ALTER TABLE appointments ADD COLUMN professional_id UUID NOT NULL;
ALTER TABLE appointments ADD CONSTRAINT fk_appointment_customer FOREIGN KEY (customer_id) REFERENCES users(id);
ALTER TABLE appointments ADD CONSTRAINT fk_appointment_professional FOREIGN KEY (professional_id) REFERENCES users(id);
ALTER TABLE appointments DROP COLUMN customer_name;
ALTER TABLE appointments DROP COLUMN barber_name;