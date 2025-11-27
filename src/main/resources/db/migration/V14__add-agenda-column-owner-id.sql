ALTER TABLE agenda ADD COLUMN owner_id UUID;

INSERT INTO users (id, name, email, password) VALUES ('00000000-0000-0000-0000-000000000001', 'sistema', 'sistema@local', 'sistema') ON CONFLICT (id) DO NOTHING;

UPDATE agenda SET owner_id = '00000000-0000-0000-0000-000000000001' WHERE owner_id IS NULL;

ALTER TABLE agenda ALTER COLUMN owner_id SET NOT NULL;

ALTER TABLE agenda ADD CONSTRAINT fk_agenda_owner FOREIGN KEY (owner_id) REFERENCES users(id);
