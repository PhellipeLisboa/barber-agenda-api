ALTER TABLE appointments
ALTER COLUMN appointment_date_time TYPE TIMESTAMP
USING appointment_date_time::timestamp;