drop sequence if exists nextOrderNum;
create sequence nextOrderNum start with 501;

create or replace function updateNextOrderNum()
returns trigger as
$ord$
        begin
                NEW.orderNumber = nextval('nextOrderNum');
                return NEW;
        END;
$ord$
language plpgsql VOLATILE;

drop trigger if exists forOrders ON Orders;
create trigger forOrders after insert
on Orders for each row
execute procedure updateNextOrderNum();
