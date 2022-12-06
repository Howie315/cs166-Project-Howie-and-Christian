DROP INDEX IF EXISTS orderNumber;
DROP INDEX IF EXISTS productName;
DROP INDEX IF EXISTS storeID;
DROP INDEX IF EXISTS updateNumber;
DROP INDEX IF EXISTS storeID;
DROP INDEX IF EXISTS userID;



--Product Updates
CREATE INDEX numberUpdateIndex 
on ProductUpdates
USING BTREE
(updateNumber);

--Userid hash
CREATE INDEX userIDIndex
ON Users
USING HASH
(userID);


--Using btree will find the range for the queries
CREATE INDEX orderNumber_index
ON Orders
USING BTREE
(orderNumber);

-- this is depedning on store id and product table for updating product for manager
CREATE INDEX productName_index
ON Product
USING BTREE
(productName, storeID);



-- This is to search a speciic range for queries for finding specific store id for manager
CREATE INDEX storeID_index
ON Orders
USING BTREE
(storeID);


