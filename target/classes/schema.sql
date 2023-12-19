CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age INT,
    email VARCHAR(255),
    password VARCHAR(255),
    is_active BOOLEAN
);
CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_role VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS users_user_roles (
    user_id BIGINT,
    user_roles_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (user_roles_id) REFERENCES user_roles(id)
);
CREATE TABLE IF NOT EXISTS offers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT,
    year INT,
    mileage INT,
    image_url VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    postedBy_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (postedBy_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    approved BOOLEAN,
    created TIMESTAMP,
    text TEXT NOT NULL,
    author_id BIGINT,
    offer_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (offer_id) REFERENCES offers(id)
);
