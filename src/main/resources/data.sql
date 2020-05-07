INSERT INTO role (name) VALUES
  ('ROLE_STUDENT'),
  ('ROLE_TEACHER'),
  ('ROLE_ADMIN');

INSERT INTO user (username, email, password, full_name, user_type, role_id) VALUES
  ('test0', 'test0@test0.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Kuba Testowy', 'Student',1),
  ('test1', 'test1@test1.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Arkadiusz Testownik','Teacher',2),
  ('admin', 'admin@admin.pl', '$2a$10$cGQqzLJ13kJLqoTwkhbXgeRNH0Km3uPwq0zSVYSjrnFG/6KErXixy', 'Adminowy Admin','Admin',3);