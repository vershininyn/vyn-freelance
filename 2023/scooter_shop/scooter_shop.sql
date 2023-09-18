--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-06-21 18:57:36

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';


--
-- TOC entry 219 (class 1259 OID 17615)
-- Name: rent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rent (
    id integer NOT NULL,
    user_id integer NOT NULL,
    scooter_id integer NOT NULL,
    start_time timestamp without time zone NOT NULL
);


ALTER TABLE public.rent OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17614)
-- Name: rent_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.rent ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.rent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 217 (class 1259 OID 17562)
-- Name: scooter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scooter (
    id integer NOT NULL,
    title character varying(4) NOT NULL,
    charge smallint NOT NULL,
    start_price numeric(4,2) NOT NULL,
    rent_price numeric(4,2) NOT NULL
);


ALTER TABLE public.scooter OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17561)
-- Name: scooter_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.scooter ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.scooter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 17041)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(10) NOT NULL,
    balance numeric(10,2)
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 17040)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3344 (class 0 OID 17615)
-- Dependencies: 219
-- Data for Name: rent; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3342 (class 0 OID 17562)
-- Dependencies: 217
-- Data for Name: scooter; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.scooter (id, title, charge, start_price, rent_price) VALUES (2, 'A123', 96, 30.00, 5.50);


--
-- TOC entry 3340 (class 0 OID 17041)
-- Dependencies: 215
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (id, name, password, role, balance) VALUES (2, 'user', '$2a$10$qa6oDhndFEyRo4L0lQ5MIO.caVIM7qjhP8ef0B6Ut2pdgbxxrtfam', 'ROLE_USER', NULL);
INSERT INTO public."user" (id, name, password, role, balance) VALUES (1, 'admin', '$2a$12$cfDa7ESC4PByHHiJb4uocuf7MO.NPTm/Jh9d7SvhFimEG6EjIDUUi', 'ROLE_ADMIN', 0.00);


--
-- TOC entry 3350 (class 0 OID 0)
-- Dependencies: 218
-- Name: rent_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rent_id_seq', 7, true);


--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 216
-- Name: scooter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scooter_id_seq', 2, true);


--
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 2, true);


--
-- TOC entry 3192 (class 2606 OID 17619)
-- Name: rent rent_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rent
    ADD CONSTRAINT rent_pkey PRIMARY KEY (id);


--
-- TOC entry 3194 (class 2606 OID 17621)
-- Name: rent rent_scooter_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rent
    ADD CONSTRAINT rent_scooter_id_key UNIQUE (scooter_id);


--
-- TOC entry 3188 (class 2606 OID 17566)
-- Name: scooter scooter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scooter
    ADD CONSTRAINT scooter_pkey PRIMARY KEY (id);


--
-- TOC entry 3190 (class 2606 OID 17568)
-- Name: scooter scooter_title_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scooter
    ADD CONSTRAINT scooter_title_key UNIQUE (title);


--
-- TOC entry 3184 (class 2606 OID 17049)
-- Name: user user_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_name_key UNIQUE (name);


--
-- TOC entry 3186 (class 2606 OID 17047)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 17627)
-- Name: rent rent_scooter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rent
    ADD CONSTRAINT rent_scooter_id_fkey FOREIGN KEY (scooter_id) REFERENCES public.scooter(id);


--
-- TOC entry 3196 (class 2606 OID 17622)
-- Name: rent rent_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rent
    ADD CONSTRAINT rent_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id);


-- Completed on 2023-06-21 18:57:36

--
-- PostgreSQL database dump complete
--
