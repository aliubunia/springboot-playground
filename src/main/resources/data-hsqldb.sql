SET TIME ZONE LOCAL;
INSERT INTO Office (id, city, country, open_from, open_until) VALUES (NEXT VALUE FOR office_seq, 'Berlin', 'DE', TIME '09:00:00+02:00', TIME '18:00:00+02:00');
INSERT INTO Office (id, city, country, open_from, open_until) VALUES (NEXT VALUE FOR office_seq, 'São Paulo', 'BR', TIME '09:00:00-03:00', TIME '18:00:00-03:00');
INSERT INTO Office (id, city, country, open_from, open_until) VALUES (NEXT VALUE FOR office_seq, 'Sydney', 'AU', TIME '09:00:00+10:00', TIME '18:00:00+10:00');
SET TIME ZONE LOCAL;