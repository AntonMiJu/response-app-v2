CREATE TABLE public.director(
    chat_id bigint PRIMARY KEY
);

CREATE TABLE public.gas_station(
    id text PRIMARY KEY,
    address text NOT NULL,
    chat_id bigint NOT NULL,
    brand text
);