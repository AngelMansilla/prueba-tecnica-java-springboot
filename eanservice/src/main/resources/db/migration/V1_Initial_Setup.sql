CREATE TABLE provider (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    provider_code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE destination (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ean VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    provider_id BIGINT,
    destination_id BIGINT,
    FOREIGN KEY (provider_id) REFERENCES provider(id),
    FOREIGN KEY (destination_id) REFERENCES destination(id)
);
