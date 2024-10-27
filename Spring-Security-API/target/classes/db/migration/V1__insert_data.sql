INSERT INTO "app_user" (is_bloked, password, role, username, id) VALUES ('admin', 'admin123', 'ROLE_USER', false);
INSERT INTO "app_user" (is_bloked, password, role, username, id) VALUES ('user', 'user123', 'ROLE_USER', false);




-- 1. Добавление пользователей
INSERT INTO app_user (username, password, role, is_bloked) VALUES
                                                               ('user', 'user123', 'USER', false),
                                                               ('admin', 'admin123', 'ADMIN', false);

-- 2. Добавление заведений
INSERT INTO place (name, description, rating) VALUES
                                                  ('Coffee Shop', 'A cozy place to enjoy coffee and pastries', 4.5),
                                                  ('Pizza Place', 'Delicious pizzas with a variety of toppings', 4.7);

-- 3. Добавление отзывов
INSERT INTO review (content, user_id, place_id) VALUES
                                                    ('Great coffee and friendly staff!', 1, 1),
                                                    ('Best pizza in town!', 1, 2),
                                                    ('The place was too noisy.', 1, 1);

-- 4. Добавление фотографий
INSERT INTO photo (url, place_id) VALUES
                                      ('http://example.com/images/coffee_shop.jpg', 1),
                                      ('http://example.com/images/pizza_place.jpg', 2);
