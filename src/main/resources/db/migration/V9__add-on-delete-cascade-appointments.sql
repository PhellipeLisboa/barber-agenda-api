ALTER TABLE appointments DROP CONSTRAINT fk_appointments_agenda;

ALTER TABLE appointments
ADD CONSTRAINT fk_appointments_agenda FOREIGN KEY (agenda_id)
REFERENCES agenda(id) ON DELETE CASCADE;