CREATE TABLE worker (
    id IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
        CHECK LENGTH(name) BETWEEN 2 AND 1000,
    birthday DATE
        CHECK (birthday >= '1900-01-01'),
    level VARCHAR NOT NULL
        CHECK level IN ('Trainee', 'Junior', 'Middle', 'Senior'),
    salary NUMERIC(100000, 0)
        CHECK (salary >= 100 AND salary <= 100000)
);

CREATE TABLE client (
    id IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
        CHECK LENGTH(name) BETWEEN 2 AND 1000
);

CREATE TABLE project (
    id IDENTITY PRIMARY KEY,
    client_id BIGINT NOT NULL,
    name VARCHAR NOT NULL
        CHECK LENGTH(name) BETWEEN 2 AND 1000,
    start_date DATE,
    finish_date DATE
);

ALTER TABLE project
ADD CONSTRAINT client_id_fk
FOREIGN KEY (client_id) REFERENCES client(id);

CREATE TABLE project_worker (
    project_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
);