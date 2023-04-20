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