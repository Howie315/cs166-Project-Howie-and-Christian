drop sequence if exists nextOrderNum;
create sequence nextOrderNum start with 501;

create or replace language plpsql;
create or replace function updateNextOrderNum()
returns trigger as
$ord$
        begin
                NEW.orderNumber = nextval('nextOrderNum');
                NEW.orderTime = CURRENT_TIMESTAMP(0);
                

                UPDATE Product
                SET numberOfUnits = numberOfUnits - NEW.unitsOrdered
                WHERE NEW.storeID = storeID
                AND NEW.productName = productName;
                return NEW;
        END;
$ord$
language plpgsql VOLATILE;

drop trigger if exists forOrders ON Orders;
create trigger forOrders after insert
on Orders for each row
execute procedure updateNextOrderNum();


--Supply request

drop sequence if exists sequence_products
CREATE SEQUENCE sequence_products START WITH 30;

CREATE OR REPLACE LANAGUAGE plpgsql;
CREATE OR REPLACE FUNCTION requestsProduct()
RETURNS "trigger" AS
$BODY$
BEGIN   
        --Inserting serials
        NEW.requestNumber = nextval("sequence_products");

        UPDATE Product 
                SET numberOfUnits = numberOfUnits + NEW.unitsRequested
                WHERE NEW.storeID = storeID
                AND NEW.productNamen = productName;
        RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

DROP TRIGGER IF EXISTS product_requestTrigger ON ProductSupplyRequests;
CREATE TRIGGER product_requestTrigger
BEFORE INSERT ON ProductSupplyRequests
FOR EACH ROW 
EXECUTE PROCEDURE requestsProduct;




