DROP INDEX IF EXISTS orderNumber;
DROP INDEX IF EXISTS productName;
DROP INDEX IF EXISTS unitsOrdered;
DROP INDEX IF EXISTS storeID;
DROP INDEX IF EXISTS customerID;
--order
CREATE INDEX orderNumber_index
ON Orders
USING BTREE
(orderNumber);

CREATE INDEX productName_index
ON Orders
USING BTREE
(productName);

CREATE INDEX unitsOrdered_index
ON Orders
USING BTREE
(unitsOrdered);

CREATE INDEX storeID_index
ON Orders
USING BTREE
(storeID);

CREATE INDEX customerID_index
ON Orders
USING BTREE
(customerID);