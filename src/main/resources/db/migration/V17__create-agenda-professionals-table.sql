CREATE TABLE agenda_professionals (

    agenda_id BIGINT NOT NULL,
    professional_id UUID NOT NULL,
    CONSTRAINT fk_agenda FOREIGN KEY (agenda_id) REFERENCES agenda(id) ON DELETE CASCADE,
    CONSTRAINT fk_professional FOREIGN KEY (professional_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT pk_agenda_professionals PRIMARY KEY (agenda_id, professional_id)

);