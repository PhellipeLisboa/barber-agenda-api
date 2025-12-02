CREATE TABLE agenda (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    barber_name VARCHAR(255) NOT NULL,
    appointmentDay DATE NOT NULL,
    appointmentTime TIME NOT NULL
);


CREATE TABLE agenda_appointments (
    agenda_id SERIAL NOT NULL,
    appointment_id SERIAL NOT NULL,
    PRIMARY KEY (agenda_id, appointment_id),
    FOREIGN KEY (agenda_id) REFERENCES agenda (id),
    FOREIGN KEY (agenda_id) REFERENCES appointments (id)
);
