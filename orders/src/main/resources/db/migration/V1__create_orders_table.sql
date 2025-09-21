CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items(id)
);
