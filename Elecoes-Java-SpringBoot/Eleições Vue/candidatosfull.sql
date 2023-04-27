--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 12.2

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
-- Name: candidato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.candidato (
    ca_id integer NOT NULL,
    ca_numero integer,
    ca_nome character varying(40),
    par_id integer
);


ALTER TABLE public.candidato OWNER TO postgres;

--
-- Name: candidatos_ca_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.candidatos_ca_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.candidatos_ca_id_seq OWNER TO postgres;

--
-- Name: candidatos_ca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.candidatos_ca_id_seq OWNED BY public.candidato.ca_id;


--
-- Name: cargo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cargo (
    car_id integer NOT NULL,
    car_descr character varying(40),
    ca_id integer
);


ALTER TABLE public.cargo OWNER TO postgres;

--
-- Name: cargo_car_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cargo_car_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cargo_car_id_seq OWNER TO postgres;

--
-- Name: cargo_car_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cargo_car_id_seq OWNED BY public.cargo.car_id;


--
-- Name: eleicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eleicao (
    ele_id integer NOT NULL,
    ele_tipo character varying(30),
    ele_ano numeric(4,0)
);


ALTER TABLE public.eleicao OWNER TO postgres;

--
-- Name: eleicao_ele_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eleicao_ele_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.eleicao_ele_id_seq OWNER TO postgres;

--
-- Name: eleicao_ele_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.eleicao_ele_id_seq OWNED BY public.eleicao.ele_id;


--
-- Name: partido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partido (
    par_id integer NOT NULL,
    par_nome character varying(15)
);


ALTER TABLE public.partido OWNER TO postgres;

--
-- Name: partido_par_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.partido_par_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.partido_par_id_seq OWNER TO postgres;

--
-- Name: partido_par_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.partido_par_id_seq OWNED BY public.partido.par_id;


--
-- Name: votos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.votos (
    ca_id integer NOT NULL,
    ele_id integer NOT NULL,
    vot_total integer,
    vot_id integer NOT NULL
);


ALTER TABLE public.votos OWNER TO postgres;

--
-- Name: votos_vot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.votos_vot_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.votos_vot_id_seq OWNER TO postgres;

--
-- Name: votos_vot_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.votos_vot_id_seq OWNED BY public.votos.vot_id;


--
-- Name: candidato ca_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.candidato ALTER COLUMN ca_id SET DEFAULT nextval('public.candidatos_ca_id_seq'::regclass);


--
-- Name: cargo car_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargo ALTER COLUMN car_id SET DEFAULT nextval('public.cargo_car_id_seq'::regclass);


--
-- Name: eleicao ele_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eleicao ALTER COLUMN ele_id SET DEFAULT nextval('public.eleicao_ele_id_seq'::regclass);


--
-- Name: partido par_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido ALTER COLUMN par_id SET DEFAULT nextval('public.partido_par_id_seq'::regclass);


--
-- Name: votos vot_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.votos ALTER COLUMN vot_id SET DEFAULT nextval('public.votos_vot_id_seq'::regclass);


--
-- Data for Name: candidato; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.candidato VALUES (1, 40004, 'Carlinhos do Açougue', 1);
INSERT INTO public.candidato VALUES (2, 23604, 'Marilda Fofoqueira', 2);
INSERT INTO public.candidato VALUES (3, 12332, 'Bernadete da Silva', 2);
INSERT INTO public.candidato VALUES (5, 10300, 'Tadeu da lanchonete', 3);
INSERT INTO public.candidato VALUES (6, 23444, 'Zé do Gato ', 2);
INSERT INTO public.candidato VALUES (7, 56855, 'Maria das Galinhas', 1);
INSERT INTO public.candidato VALUES (9, 15000, 'Bruninho da Gaita', 1);
INSERT INTO public.candidato VALUES (10, 15015, 'Joãozinho Bafo de Onça', 3);
INSERT INTO public.candidato VALUES (11, 13013, 'Valdir Carpinteiro', 3);
INSERT INTO public.candidato VALUES (14, 12333, 'Maria Fofoqueira', 3);
INSERT INTO public.candidato VALUES (4, 13133, 'Fininho do Dogão
', 2);


--
-- Data for Name: cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cargo VALUES (1, 'presidente clube dos pedreiros', 1);
INSERT INTO public.cargo VALUES (2, 'policial militar
', 2);
INSERT INTO public.cargo VALUES (3, 'advogado', 2);
INSERT INTO public.cargo VALUES (4, 'presidente OAB regional', 2);
INSERT INTO public.cargo VALUES (5, 'feirante', 3);
INSERT INTO public.cargo VALUES (6, 'representante de bairro', 3);


--
-- Data for Name: eleicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.eleicao VALUES (1, 'prefeito', 2020);
INSERT INTO public.eleicao VALUES (2, 'vereador', 2016);


--
-- Data for Name: partido; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.partido VALUES (1, 'PDT');
INSERT INTO public.partido VALUES (2, 'PSDB');
INSERT INTO public.partido VALUES (3, 'PODEMOS');
INSERT INTO public.partido VALUES (4, 'PFL');


--
-- Data for Name: votos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.votos VALUES (1, 1, 1200, 1);
INSERT INTO public.votos VALUES (1, 2, 568, 2);
INSERT INTO public.votos VALUES (2, 2, 100, 3);


--
-- Name: candidatos_ca_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.candidatos_ca_id_seq', 14, true);


--
-- Name: cargo_car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cargo_car_id_seq', 6, true);


--
-- Name: eleicao_ele_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eleicao_ele_id_seq', 2, true);


--
-- Name: partido_par_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.partido_par_id_seq', 4, true);


--
-- Name: votos_vot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.votos_vot_id_seq', 3, true);


--
-- Name: candidato candidatos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.candidato
    ADD CONSTRAINT candidatos_pkey PRIMARY KEY (ca_id);


--
-- Name: cargo cargo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (car_id);


--
-- Name: eleicao eleicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eleicao
    ADD CONSTRAINT eleicao_pkey PRIMARY KEY (ele_id);


--
-- Name: partido partido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_pkey PRIMARY KEY (par_id);


--
-- Name: votos votos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.votos
    ADD CONSTRAINT votos_pkey PRIMARY KEY (vot_id);


--
-- Name: candidato candidato_par_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.candidato
    ADD CONSTRAINT candidato_par_id_fkey FOREIGN KEY (par_id) REFERENCES public.partido(par_id) NOT VALID;


--
-- Name: cargo cargo_ca_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_ca_id_fkey FOREIGN KEY (ca_id) REFERENCES public.candidato(ca_id);


--
-- Name: votos eleicao_ca_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.votos
    ADD CONSTRAINT eleicao_ca_id_fkey FOREIGN KEY (ca_id) REFERENCES public.candidato(ca_id);


--
-- Name: votos eleicao_ele_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.votos
    ADD CONSTRAINT eleicao_ele_id_fkey FOREIGN KEY (ele_id) REFERENCES public.eleicao(ele_id);


--
-- PostgreSQL database dump complete
--

