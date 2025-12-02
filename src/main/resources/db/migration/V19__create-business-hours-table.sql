CREATE TABLE business_hours (
    id BIGSERIAL PRIMARY KEY,
    day VARCHAR(20) NOT NULL UNIQUE,
    opens_at TIME,
    closes_at TIME,
    closed BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO business_hours (day, opens_at, closes_at, closed) VALUES
('SUNDAY',    NULL,   NULL,   TRUE),
('MONDAY',    '09:00','18:00',FALSE),
('TUESDAY',   '09:00','18:00',FALSE),
('WEDNESDAY', '09:00','18:00',FALSE),
('THURSDAY',  '09:00','18:00',FALSE),
('FRIDAY',    '09:00','20:00',FALSE),
('SATURDAY',  '09:00','14:00',FALSE);
