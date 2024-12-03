CREATE TABLE USER_INFO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL
);
CREATE TABLE BILLING_HEADER (
    billing_id INT PRIMARY KEY,
    caller_name VARCHAR(100) NOT NULL,
    invoice_code VARCHAR(100) NOT NULL
);
INSERT INTO USER_INFO ( name, email, password, roles) VALUES ( 'john_doe','dummy@gmail.com','paswword','user');