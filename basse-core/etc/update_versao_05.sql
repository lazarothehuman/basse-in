insert into bank(id, name, active) values (null,'Millennium BIM',true);
insert into bank(id, name, active) values (null,'BCI',true);
insert into bank(id, name, active) values (null,'Banco Terra',true);
insert into bank(id, name, active) values (null,'Barclays',true);
insert into bank(id, name, active) values (null,'Standard Bank',true);
insert into bank(id, name, active) values (null,'Capital Bank',true);
insert into bank(id, name, active) values (null,'FNB Moçambique',true);
insert into bank(id, name, active) values (null,'Moza Banco',true);
insert into bank(id, name, active) values (null,'Banco Único',true);
insert into bank(id, name, active) values (null,'Banco de Moçambique',true);
insert into bank(id, name, active) values (null,'Banco ABC',true);

CREATE TABLE supplier (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL, 
  email varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
)engine=innodb;

CREATE TABLE technician (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL, 
  email varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
)engine=innodb;

CREATE TABLE client (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL, 
  email varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
)engine=innodb;

CREATE TABLE product (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  category varchar(255) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
)engine=innodb;

CREATE TABLE product_price (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  unit_cost decimal(19,2) NOT NULL,
  registry_date date NOT NULL,
  product_id bigint(20) NOT NULL,
  supplier_id bigint(20) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_supplier (supplier_id),
  KEY fk_product (product_id),
  CONSTRAINT fk_product_foreign_key FOREIGN KEY (product_id) REFERENCES product(id),
  CONSTRAINT fk_supplier_foreign_key FOREIGN KEY (supplier_id) REFERENCES supplier(id)
)engine=innodb;

CREATE TABLE sale (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	total_cost decimal(19,2) NOT NULL,
	date date NOT NULL,
	active  bit(1) NOT NULL,
	registrator_id  bigint(20) NOT NULL,
	client_id  bigint(20) DEFAULT NULL,
	PRIMARY KEY ( id ),
	KEY  registrator_id  ( registrator_id ),
	KEY  client_id  ( client_id ),
	CONSTRAINT  sale_ibfk_1  FOREIGN KEY ( registrator_id ) REFERENCES  user  ( id ),
	CONSTRAINT  sale_ibfk_2  FOREIGN KEY ( client_id ) REFERENCES  client  ( id )
)engine=innodb;

CREATE TABLE  sale_item  (
   id  bigint(20) NOT NULL AUTO_INCREMENT,
   cost  decimal(19,2) NOT NULL,
   sale_id  bigint(20) NOT NULL,
   product_price_id  bigint(20) DEFAULT NULL,
   active  bit(1) NOT NULL,
  PRIMARY KEY ( id ),
  KEY  sale_id  ( sale_id ),
  KEY  product_price_id  ( product_price_id ),
  CONSTRAINT  sale_item_ibfk_1  FOREIGN KEY ( sale_id ) REFERENCES  sale  ( id ),
  CONSTRAINT  sale_item_ibfk_2  FOREIGN KEY ( product_price_id ) REFERENCES  product_price  ( id )
) ENGINE=InnoDB;

CREATE TABLE request (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  client_id bigint(20) NOT NULL,
  request_date date  NOT NULL,
  status varchar(255) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_client (client_id),
  CONSTRAINT fk_client_foreign_key FOREIGN KEY (client_id) REFERENCES client(id)
)engine=innodb;

CREATE TABLE request_item(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	request_id bigint(20) NOT NULL,
	quantity integer not null,
	product_id bigint(20) not null,
	active bit(1) NOT NULL,
	PRIMARY KEY (id),
	KEY fk_request_on_item (request_id),
	KEY fk_product_on_item (product_id),
	CONSTRAINT fk_product_foreign_on_item FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT fk_request_foreign_key_on_item FOREIGN KEY (request_id) REFERENCES request(id)
)engine=innodb;

CREATE TABLE levy (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  request_id bigint(20) NOT NULL,
  occurance_date date  NOT NULL,
  notes varchar(255) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_request (request_id),
  CONSTRAINT fk_request_foreign_key FOREIGN KEY (request_id) REFERENCES request(id)
)engine=innodb;

CREATE TABLE levy_item(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	levy_id bigint(20) NOT NULL,
	quantity integer not null,
	product_price_id bigint(20) not null,
	active bit(1) NOT NULL,
	PRIMARY KEY (id),
	KEY fk_product_price_levy (product_price_id),
	KEY fk_levy_product (levy_id),
	CONSTRAINT fk_product_price_foreign FOREIGN KEY (product_price_id) REFERENCES product_price(id),
	CONSTRAINT fk_levy_foreign_key FOREIGN KEY (levy_id) REFERENCES levy(id)
)engine=innodb;

CREATE TABLE levy_product_price (
  levy_id bigint(20) NOT NULL,
  product_price_id bigint(20) NOT NULL,
  KEY fk_levy_product_price (levy_id),
  KEY fk_product_price_levy (product_price_id),
  CONSTRAINT fk_levy_product_foreign_key FOREIGN KEY (levy_id) REFERENCES levy(id),
  CONSTRAINT fk_product_price_levy_foreign FOREIGN KEY (product_price_id) REFERENCES product_price(id)
)engine=innodb;

CREATE TABLE technician_levy (
  levy_id bigint(20) NOT NULL,
  technician_id bigint(20) NOT NULL,
  KEY fk_levy_tech (levy_id),
  KEY fk_techinician_levy (technician_id),
  CONSTRAINT fk_levy_tech_foreign_key FOREIGN KEY (levy_id) REFERENCES levy(id),
  CONSTRAINT fk_techinician_levy_foreign FOREIGN KEY (technician_id) REFERENCES technician(id)
)engine=innodb;

CREATE TABLE  company  (
   id  bigint(20) NOT NULL AUTO_INCREMENT,
   name  varchar(255) NOT NULL,
   address  varchar(255) NOT NULL,
   phone  varchar(255) NOT NULL,
   nuit  varchar(255) NOT NULL,
   email  varchar(255) NOT NULL,
   logo  mediumblob,
   invoice_note  varchar(2000) DEFAULT NULL,
   vat_tax  decimal(19,2) NOT NULL,
   bank_details varchar(255) NOT NULL,
  PRIMARY KEY ( id )
) ENGINE=InnoDB;




