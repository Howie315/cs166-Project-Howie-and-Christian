drop sequence if exists nextOrderNum;
drop sequence if exists nextProductUpdateNum;
drop sequence if exists nextSupplyReqNum;
create sequence nextOrderNum start with 501;
create sequence nextProductUpdateNum start with 51;
create sequence nextSupplyReqNum start with 11;

create or replace function updateNextOrderNum()
returns trigger as 
$ord$
	begin
		NEW.orderNumber = nextval('nextOrderNum');
		return NEW;
	END;
$ord$
language plpgsql VOLATILE;


create or replace function updateNextProductUpdateNum()
returns trigger as
$ord$
        begin
                NEW.updateNumber = nextval('nextOrderNum');
                return NEW;
        END;
$ord$
language plpgsql VOLATILE;


create or replace function updateNextProductReqNum()
returns trigger as
$ord$
        begin

                NEW.requestNumber = nextval('nextSupplyReqNum');
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


drop trigger if exists forProductUpdates ON ProductUpdates;
create trigger forProductUpdates after insert
on ProductUpdates for each row
execute procedure updateNextProductUpdateNum();

drop trigger if exists forProductReqs ON ProductSupplyRequests;
create trigger forProductReqs after insert
on ProductSupplyRequests for each row
execute procedure updateNextProductReqNum();
=======
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



