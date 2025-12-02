ALTER TABLE appointments
RENAME COLUMN appointment_day TO appointment_date_time;

ALTER TABLE appointments
DROP COLUMN appointment_time;