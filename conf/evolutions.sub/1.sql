# --- !Ups
-- user
CREATE SEQUENCE user_id_seq START 1;
CREATE TABLE user (
  user_id integer DEFAULT nextval('user_id_seq') PRIMARY KEY,
name varchar(256) NOT NULL,
password varchar(256) NOT NULL
);

-- user_info
CREATE TABLE user_info (
  user_id integer NOT NULL,
  address varchar(256),
  postal_code varchar(256)
);

CREATE INDEX user_info_id_index ON user_info(user_id);

-- site-manager
CREATE SEQUENCE site_manager_id_seq START 1;
CREATE TABLE site_manager (
  site_manager_id integer DEFAULT nextval('site_manager_id_seq') PRIMARY KEY,
name varchar(256) NOT NULL,
password varchar(256) NOT NULL
);


-- order
CREATE SEQUENCE order_id_seq START 1;
CREATE TABLE order_t (
  order_id integer DEFAULT nextval('order_id_seq') PRIMARY KEY,
order_status integer,
user_id integer
);

-- used_coupon
CREATE TABLE used_coupon (
  order_id integer,
  coupon_id integer,
  discount integer
);
CREATE INDEX used_coupon_order_index ON used_coupon(order_id);
CREATE INDEX used_coupon_coupon_index ON used_coupon(coupon_id);

-- coupon
CREATE SEQUENCE coupon_id_seq START 1;
CREATE TABLE coupon (
  coupon_id integer DEFAULT nextval('coupon_id_seq') PRIMARY KEY,
coupon_number varchar(256),
discount integer
);

-- product
CREATE SEQUENCE product_id_seq START 1;
CREATE TABLE product (
  product_id integer DEFAULT nextval('product_id_seq') PRIMARY KEY,
  name varchar(256),
  price integer
);

-- product_info
CREATE TABLE product_info (
  product_id integer,
  description text
);
CREATE INDEX product_info_id_index ON product_info(product_id);

-- item
CREATE SEQUENCE item_id_seq START 1;
CREATE TABLE item (
  item_id integer DEFAULT nextval('item_id_seq') PRIMARY KEY,
order_id integer,
product_id integer,
price integer,
number integer,
update_date Timestamp
);

-- payment_info
CREATE SEQUENCE payment_id_seq START 1;
CREATE TABLE payment_info (
  payment_id integer DEFAULT nextval('payment_id_seq') PRIMARY KEY,
order_id integer,
is_payed integer,
payment_type integer,
price integer,
due_date Timestamp,
payment_date Timestamp
);
CREATE INDEX payment_info_order_index ON payment_info(order_id);

-- credit_pay
CREATE SEQUENCE credit_pay_id_seq START 1;
CREATE TABLE credit_pay (
  credit_pay_id integer DEFAULT nextval('credit_pay_id_seq') PRIMARY KEY,
payment_id integer
);
CREATE INDEX credit_pay_payment_id_index ON credit_pay(payment_id);

-- bank_pay
CREATE SEQUENCE bank_pay_id_seq START 1;
CREATE TABLE bank_pay (
  bank_pay_id integer DEFAULT nextval('bank_pay_id_seq') PRIMARY KEY,
payment_id integer,
bank_account varchar(32)
);
CREATE INDEX bank_pay_payment_id_index ON bank_pay(payment_id);

# --- !Downs