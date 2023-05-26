-- ===============================
-- =  Optimize searches for products by category ID on product table
-- ===============================
CREATE INDEX idx_product_category_id ON product (category_id);

-- ===============================
-- =  Create an index for the id column on the product table
-- ===============================
CREATE INDEX idx_product_id ON product (id);

-- ===============================
-- =  Create Trigram Indexing for wildcard search on product name
-- ===============================
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_product_name_trigram ON product USING gin (name gin_trgm_ops);


-- ===============================
-- =  Create indexes for the id column on the orders table
-- ===============================
CREATE INDEX idx_orders_id ON orders (id);


-- ===============================
-- =  Create indexes for the email column on the customer table
-- ===============================
CREATE INDEX idx_customer_email ON "customer" (email);

-- ===============================
-- =  Optimize searches for all states by country code on state table
-- ===============================
CREATE INDEX idx_state_country_id ON state (country_id);
CREATE INDEX idx_country_code ON country (code);

-- ===============================
-- =  Create indexes for the customer_id column on the address table
-- ===============================
CREATE INDEX idx_address_customer_id ON address (customer_id);

