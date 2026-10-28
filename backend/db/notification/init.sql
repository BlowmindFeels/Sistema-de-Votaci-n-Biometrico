CREATE TABLE dispositivos (
    id          SERIAL PRIMARY KEY,
    usuario_id  INT          NOT NULL,
    token_fcm   VARCHAR(255) NOT NULL UNIQUE,
    fecha       TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE notificaciones (
    id          SERIAL PRIMARY KEY,
    usuario_id  INT          NOT NULL,
    mensaje     VARCHAR(255) NOT NULL,
    enviada     BOOLEAN      NOT NULL DEFAULT false,
    fecha       TIMESTAMPTZ  NOT NULL DEFAULT now()
);