DELETE FROM loan_order;
DELETE FROM tariff;

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
