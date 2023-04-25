--liquibase formatted sql

--changeset NikolayChukanov:1
--comment first migration

DROP TABLE loan_order;
DROP TABLE tariff;

CREATE TABLE tariff (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        type varchar(32) NOT NULL UNIQUE,
                        interest_rating varchar(16) NOT NULL
);

CREATE TABLE loan_order (
                            id BIGSERIAL PRIMARY KEY,
                            order_id varchar(64) NOT NULL UNIQUE,
                            user_id BIGINT NOT NULL,
                            tariff_id BIGINT NOT NULL REFERENCES tariff(id),
                            credit_rating real NOT NULL,
                            status varchar(32) NOT NULL,
                            time_insert timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            time_update timestamp NOT NULL
);

INSERT INTO tariff(type, interest_rating) VALUES ('CONSUMER', '12%');
INSERT INTO tariff(type, interest_rating) VALUES ('BUSINESS', '5%');
INSERT INTO tariff(type, interest_rating) VALUES ('MORTGAGE', '9%');
INSERT INTO tariff(type, interest_rating) VALUES ('STUDY', '7%');

INSERT INTO loan_order(order_id, user_id, tariff_id, credit_rating,
                       status, time_update)
VALUES ('3cf735f1-88f8-4379-85aae03e27118fe4', 123456789012, 1,
        0.75, 'IN_PROGRESS', current_timestamp);
INSERT INTO loan_order(order_id, user_id, tariff_id, credit_rating,
                       status, time_update)
VALUES ('2a7e152e-debe-11ed-b5ea-0242ac120002', 123456778012, 2,
        0.6, 'REFUSED', current_timestamp);
INSERT INTO loan_order(order_id, user_id, tariff_id, credit_rating,
                       status, time_update)
VALUES ('2a7e20d2-debe-11ed-b5ea-0242ac120002', 123445789012, 1,
        0.9, 'APPROVED', current_timestamp);
