alter table transaction add active bit not null;
alter table profile add active bit not null;
alter table user add active bit not null;
alter table user add language varchar(15) not null;
alter table transaction add unique index uq_transaction_name (name);
update user set user.language = 'PORTUGUESE';
update profile set active = true;
update user set active = true;
update transaction set active = true;

update transaction set name = 'profiles' where id = 1;
update transaction set name = 'users' where id = 2;

INSERT INTO transaction (id,code,name,url,active) VALUES (null,'103','change.password','/accesscontrol/users/change-Password-User.zul', true);