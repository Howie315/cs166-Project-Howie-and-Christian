COPY Users
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/users.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE users_userID_seq RESTART 101;

COPY Store
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/stores.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Product
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/products.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Warehouse
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/warehouse.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Orders
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/orders.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE orders_orderNumber_seq RESTART 501;


COPY ProductSupplyRequests
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/productSupplyRequests.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE productsupplyrequests_requestNumber_seq RESTART 11;

COPY ProductUpdates
FROM '/extra/hnguy431/cs166-Project-Howie-and-Christian/cs166 Howie and Christian/data/productUpdates.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE productupdates_updateNumber_seq RESTART 51;
