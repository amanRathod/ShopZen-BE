
-- ===============================
-- = Create "uuid-ossp" extension in PostgreSQL
-- ===============================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- ======================================

-- ===============================
-- = Create table "Customer"
-- ===============================
 CREATE TABLE customer (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   role VARCHAR(10),
   image VARCHAR(255),
   status VARCHAR(10),
   billing_address_id UUID REFERENCES address(id),
    shipping_address_id UUID REFERENCES address(id)
 );
-- ======================================

-- ===============================
-- = Create table "Address"
-- ===============================
 CREATE TABLE address (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
   city varchar(255),
   country varchar(255),
   state varchar(255),
   street varchar(255),
   zip_code varchar(255)
   full_name varchar(255),
   mobile varchar(20),
   customer_id UUID REFERENCES customer(id)
 );
-- ======================================
