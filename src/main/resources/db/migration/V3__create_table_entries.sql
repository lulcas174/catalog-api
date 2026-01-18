CREATE TABLE entries (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(15,2) NOT NULL,
    date DATE NOT NULL,
    subcategory_id BIGINT NOT NULL,
    comment TEXT,
    CONSTRAINT fk_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategories(id) ON DELETE RESTRICT
);
