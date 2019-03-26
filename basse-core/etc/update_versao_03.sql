CREATE TABLE client (
  id bigint(20) NOT NULL,
  name varchar(255) NOT NULL,
  address varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL, 
  date datetime NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE bank (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_bank (name)
);


CREATE TABLE product (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  price decimal(19,2) NOT NULL,
  limited bit(1) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE sale (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  invoice_number varchar(255) NOT NULL,
  client_id bigint(20) NOT NULL,
  paid_amount decimal(19,2) NOT NULL,
  received_amount decimal(19,2) NOT NULL,
  change_amount decimal(19,2) NOT NULL,
  payment_status varchar(255) NOT NULL,
  date datetime NOT NULL,
  user_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_sale_invoice (invoice_number),
  KEY fk_sale_client (client_id),
  KEY fk_sale_user (user_id),
  CONSTRAINT fk_sale_client FOREIGN KEY (client_id) REFERENCES client (id),
  CONSTRAINT fk_sale_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE product_sale_item (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  quantity_sold bigint(20) NOT NULL,
  price_unit decimal(19,2) NOT NULL,
  sale_id bigint(20) NOT NULL,
  product_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_product_sale (sale_id),
  KEY fk_product (product_id),
  CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT fk_product_sale FOREIGN KEY (sale_id) REFERENCES sale (id)
);

CREATE TABLE stock (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  quantity bigint(20) NOT NULL,
  product_id bigint(20) NOT NULL,
  last_update_time datetime NOT NULL;
  PRIMARY KEY (id),
  UNIQUE KEY uq_stock_product (product_id)
 );
 
CREATE TABLE payment (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  receipt_number varchar(255) NOT NULL,
  value decimal(19,2) NOT NULL,
  date date NOT NULL,
  form_of_payment varchar(255) NOT NULL,
  bank_id bigint(20) DEFAULT NULL,
  cheque_number varchar(255) DEFAULT NULL,
  client_id bigint(20) NOT NULL,
  sale_id bigint(20) NOT NULL,
  user_id bigint(20) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_payment_receipt (receipt_number),
  KEY fk_payment_bank (bank_id),
  KEY fk_payment_client (client_id),
  KEY fk_payment_sale (sale_id),
  KEY fk_payment_user (user_id),
  CONSTRAINT fk_payment_bank FOREIGN KEY (bank_id) REFERENCES bank (id),
  CONSTRAINT fk_payment_client FOREIGN KEY (client_id) REFERENCES client (id),
  CONSTRAINT fk_payment_sale FOREIGN KEY (sale_id) REFERENCES sale (id),
  CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES user (id)
);


 CREATE TABLE document_numeration (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  type varchar(255) NOT NULL,
  sequence bigint(20) NOT NULL,
  version bigint(20) NOT NULL,
  year int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uc_document_numeration (type,year)
);