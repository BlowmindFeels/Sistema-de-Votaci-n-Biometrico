CREATE TABLE votaciones (
    id           SERIAL PRIMARY KEY,
    titulo       VARCHAR(150) NOT NULL,
    descripcion  TEXT         NOT NULL,
    fecha_inicio TIMESTAMPTZ  NOT NULL,
    fecha_fin    TIMESTAMPTZ  NOT NULL,
    estado       VARCHAR(12)  NOT NULL
        CHECK (estado IN ('PROGRAMADA','ACTIVA','FINALIZADA','CANCELADA')),
    CHECK (fecha_fin > fecha_inicio)
);

CREATE TABLE opciones (
    id           SERIAL PRIMARY KEY,
    votacion_id  INT          NOT NULL REFERENCES votaciones(id),
    nombre       VARCHAR(150) NOT NULL,
    UNIQUE (id, votacion_id)
);

CREATE TABLE votos (
    id           SERIAL PRIMARY KEY,
    votacion_id  INT         NOT NULL,
    opcion_id    INT         NOT NULL,
    usuario_id   INT         NOT NULL,   -- id de Auth-Service (sin FK: es otra base)
    fecha        TIMESTAMPTZ NOT NULL DEFAULT now(),
    -- la opción debe pertenecer a esa votación
    FOREIGN KEY (opcion_id, votacion_id) REFERENCES opciones(id, votacion_id),
    -- VOTO ÚNICO: un usuario solo una vez por votación
    UNIQUE (votacion_id, usuario_id)
);

-- Datos de prueba (los mismos de la app)
INSERT INTO votaciones (titulo, descripcion, fecha_inicio, fecha_fin, estado) VALUES
 ('Cambio de empresa de vigilancia', 'Se decide si se mantiene la empresa actual o se contrata una nueva para el conjunto residencial.', now() - interval '5 days',  now() + interval '10 days', 'ACTIVA'),
 ('Remodelación del salón comunal',  '¿Se aprueba el presupuesto para remodelar el salón comunal?',                                    now() - interval '5 days',  now() + interval '10 days', 'ACTIVA'),
 ('Horario de la piscina',           'Definir el horario de uso de la piscina durante el fin de semana.',                              now() - interval '60 days', now() - interval '45 days', 'FINALIZADA'),
 ('Jornada de reciclaje',            'Elegir el día de la primera jornada de reciclaje del conjunto.',                                 now() + interval '5 days',  now() + interval '10 days', 'PROGRAMADA');

INSERT INTO opciones (votacion_id, nombre) VALUES
 (1,'Mantener la empresa actual'), (1,'Contratar SegurAndes'), (1,'Contratar VigiNorte'),
 (2,'A favor'), (2,'En contra'), (2,'Abstención'),
 (3,'8:00 a.m. a 6:00 p.m.'), (3,'10:00 a.m. a 8:00 p.m.'),
 (4,'Sábado'), (4,'Domingo');