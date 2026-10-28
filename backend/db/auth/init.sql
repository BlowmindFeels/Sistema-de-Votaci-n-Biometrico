CREATE TABLE usuarios (
    id             SERIAL PRIMARY KEY,
    nombre         VARCHAR(120) NOT NULL,
    documento      VARCHAR(10)  NOT NULL UNIQUE,
    correo         VARCHAR(150) NOT NULL,
    password_hash  VARCHAR(100) NOT NULL,
    fecha_registro TIMESTAMPTZ  NOT NULL DEFAULT now()
);