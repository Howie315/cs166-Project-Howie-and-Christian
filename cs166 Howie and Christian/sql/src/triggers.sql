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
