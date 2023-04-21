-- ===============================
-- = Create table "Product"
-- ===============================
CREATE TABLE IF NOT EXISTS product (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  sku VARCHAR(255),
  name VARCHAR(255),
  description VARCHAR(255),
  unit_price NUMERIC(13,2),
  image_url VARCHAR(255),
  active BOOLEAN DEFAULT TRUE,
  units_in_stock INTEGER,
  date_created TIMESTAMP(6),
  last_updated TIMESTAMP(6),
  category_id UUID NOT NULL REFERENCES product_category(id)
);
-- ======================================

-- ===============================
-- = Create enum "Category"
-- ===============================
CREATE TYPE category AS ENUM (
    'BOOKS',
    'ELECTRONICS',
    'CLOTHING',
    'HOME_APPLIANCES'
);
-- ======================================

-- ===============================
-- = Create table "Product Category"
-- ===============================
 CREATE TABLE IF NOT EXISTS product_category (
   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
   category_name Category
 );
-- ======================================