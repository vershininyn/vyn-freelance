--
-- TOC entry 3342 (class 0 OID 17562)
-- Dependencies: 217
-- Data for Name: scooter; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.scooter (id, title, charge, start_price, rent_price, is_already_under_rent)
VALUES (1, 'A123', 96, 30.00, 5.50, false);

INSERT INTO public.scooter (id, title, charge, start_price, rent_price, is_already_under_rent)
VALUES (2, 'F123', 96, 30.00, 5.50, false);

--
-- TOC entry 3340 (class 0 OID 17041)
-- Dependencies: 215
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (id, name, password, role, balance)
VALUES (1, 'admin', '$2a$10$chPTHqitengdvmo14yDIKu0qIYcdx0NmNCOHmAS1q.inzPWqvbPbS', 'ROLE_ADMIN', NULL);

INSERT INTO public."user" (id, name, password, role, balance)
--VALUES (2, 'user', '$2a$10$LodPlq9Xl2gqQ7D1CObw5OYpSAdS.qJ8hXWoefc0q4hLc.q.09eT6', 'ROLE_USER', 0.0);
VALUES (2, 'user', '$2a$10$LodPlq9Xl2gqQ7D1CObw5OYpSAdS.qJ8hXWoefc0q4hLc.q.09eT6', 'ROLE_USER', 0.0);

