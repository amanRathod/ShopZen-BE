-- ===============================
-- = Create enum "Order Status"
-- ===============================
CREATE TYPE orderStatus AS ENUM  (
    'CREATED',
    'PROCESSING',
    'SHIPPED',
    'DELIVERED',
    'CANCELLED'
);
-- ======================================

-- ===============================
-- = Create table "Order"
-- ===============================
 CREATE TABLE orders (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
   order_tracking_number varchar(255) DEFAULT NULL,
   total_price numeric(19,2) DEFAULT NULL,
   total_quantity int DEFAULT NULL,
   billing_address_id UUID DEFAULT NULL,
   customer_id UUID DEFAULT NULL,
   shipping_address_id UUID DEFAULT NULL,
   status OrderStatus,
   payment_method PaymentMethod,
   date_created timestamp(6) DEFAULT NULL,
   last_updated timestamp(6) DEFAULT NULL,
   UNIQUE (billing_address_id),
   UNIQUE (shipping_address_id),
   CONSTRAINT FK_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
   CONSTRAINT FK_billing_address_id FOREIGN KEY (billing_address_id) REFERENCES address (id),
   CONSTRAINT FK_shipping_address_id FOREIGN KEY (shipping_address_id) REFERENCES address (id)
 );
-- ======================================

-- ===============================
-- = Create table "Order Item"
-- ===============================
 CREATE TABLE order_item (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
   image varchar(255),
   description varchar(400),
   name varchar(255),
   quantity integer,
   unit_price numeric(19,2),
   order_id UUID,
   product_id UUID,
   CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
   CONSTRAINT fk_order_item_product_id FOREIGN KEY (product_id) REFERENCES product(id)
 );
 -- ======================================