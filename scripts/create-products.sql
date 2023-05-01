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
  category_id BIGINT NOT NULL REFERENCES product_category(id)
);
-- ======================================

-- ===============================
-- = Create enum "Category"
-- ===============================
CREATE TYPE category AS ENUM (
    'BOOKS',
    'ELECTRONICS',
    'CLOTHING',
    'ACTIVEWEAR'
    'GROCERY',
    'HEALTH',
    'ACCESSORIES',
    'BEAUTY',
    'SPORTS',
    'TOYS',
    'OTHER'
);
-- ======================================

-- ===============================
-- = Create table "Product Category"
-- ===============================
 CREATE TABLE IF NOT EXISTS product_category (
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   category_name VARCHAR(255) NULL,
   PRIMARY KEY (id)
 );
-- ======================================