INSERT INTO transaction (id,code,name,url,active) VALUES (null,'201','clients.record','/accesscontrol/clients/clients-list.zul',true);
INSERT INTO transaction (id,code,name,url,active) VALUES (null,'202','products.record','/accesscontrol/products/products-list.zul',true);
INSERT INTO transaction (id,code,name,url,active) VALUES (null,'203','services.record','/accesscontrol/services/services-list.zul',true);
INSERT INTO transaction (id,code,name,url,active) VALUES (null,'204','bank.record','/finance/banks/banks-list.zul',true);
INSERT INTO transaction (id,code,name,url,active) VALUES (null,'401','payments','/finance/payments/payments-list.zul',true);

INSERT INTO transaction (id, code, name, url, active) VALUES (null, '301', 'add.sale', '/accesscontrol/sales/add_sale.zul', true);

insert into profile_transaction values (1, 3);
insert into profile_transaction values (1, 4);
insert into profile_transaction values (1, 5);
insert into profile_transaction values (1, 6);
insert into profile_transaction values (1, 7);
insert into profile_transaction values (1, 8);
insert into profile_transaction values (1, (select id from transaction where code='401'));
insert into profile_transaction values (1, (select id from transaction where code='301'));