DROP TABLE IF EXISTS agenda_appointments;

ALTER TABLE appointments ADD COLUMN agenda_id SERIAL;

ALTER TABLE appointments ADD CONSTRAINT fk_appointments_agenda FOREIGN KEY (agenda_id) REFERENCES agenda(id);