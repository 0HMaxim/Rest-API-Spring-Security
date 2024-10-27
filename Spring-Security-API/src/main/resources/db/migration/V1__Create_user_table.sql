-- CREATE TABLE "user" (
--                         id INT AUTO_INCREMENT PRIMARY KEY,
--                         is_bloked BOOLEAN,
--                         password VARCHAR(255),
--                         role VARCHAR(50),
--                         username VARCHAR(100)
-- );


CREATE TABLE app_user (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          is_bloked BOOLEAN DEFAULT FALSE,
                          password VARCHAR(255),
                          role VARCHAR(50),
                          username VARCHAR(100)
);

