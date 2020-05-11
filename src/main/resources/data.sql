INSERT INTO role (name) VALUES
  ('ROLE_STUDENT'),
  ('ROLE_TEACHER'),
  ('ROLE_ADMIN');

INSERT INTO user (username, email, password, full_name, user_type, role_id) VALUES
  ('test0', 'test0@test0.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Kuba Testowy', 'Student',1),
  ('test00', 'test0@test00.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Jakub Testowy', 'Student',1),
  ('test01', 'test0@test01.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Marcin Testowy', 'Student',1),
  ('test1', 'test1@test1.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Arkadiusz Testownik','Teacher',2),
  ('test2', 'test2@test2.pl', '$2a$10$R9l9mX8e0LOZv55Sr2/qLuBGPi.29f7i6Wo/VlbFNnphTnbw/Kbue', 'Arkadiusz Testownik2','Teacher',2),
  ('admin', 'admin@admin.pl', '$2a$10$cGQqzLJ13kJLqoTwkhbXgeRNH0Km3uPwq0zSVYSjrnFG/6KErXixy', 'Adminowy Admin','Admin',3);


INSERT INTO `question` (`id`, `text`, `teacher_id`) VALUES
(1, 'Użytkownik wpisując w wierszu poleceń polecenie ping www.onet.pl otrzymuje następujący komunikat: Żądanie polecenia ping nie może znaleźć hosta www.onet.pl Sprawdź nazwe i ponów próbę. Natomiast wpisując w wierszu poleceń polecenie ping 213.180.141.140 (adres IP serwera www.onet.pl), użytkownik dostaje odpowiedź z serwera. Co może być przyczyną takiego stanu?', 4),
(2, 'Programu CHKDSK używa się w celu', 4),
(3, 'Wskaż program DTP', 4),
(4, 'W systemie operacyjnym Ubuntu konto użytkownika student można usunąć za pomocą polecenia', 4),
(5, 'Wskaż technologię wykorzystywaną do udostępniania Internetu wraz z usługą telewizji kablowej, w której jako medium transmisyjne jest wykorzystywany światłowód oraz kabel koncentryczny', 4),
(6, 'Które zdanie opisuje protokół SSH (Secure Shell)?', 4),
(7, 'W systemie Linux do monitorowania w czasie rzeczywistym uruchomionych procesów służy polecenie:', 4),
(8, 'W sieci Ethernet 100Base-TX do transmisji danych wykorzystywane są żyły kabla UTP dołączone do pinów', 4),
(9, 'Jaka topologia sieci komputerowej nie istnieje?', 4),
(10, 'Komputer utracił połączenie z siecią komputerową. Jakie działanie należy wykonać w pierwszej kolejności, aby rozwiązać problem?', 4);


INSERT INTO `answer` (`id`, `text`, `question_id`) VALUES
(1, 'niepoprawnie skonfigurowana maska podsieci', 1),
(2, 'niepoprawnie skonfigurowana brama domyślna', 1),
(3, 'niepoprawny adres IP hosta', 1),
(4, 'niepoprawny adres IP serwera DNS', 1),
(5, 'naprawy logicznej struktury dysku', 2),
(6, 'defragmentacji dysku', 2),
(7, 'zmiany systemu plików', 2),
(8, 'naprawy fizycznej struktury dysku', 2),
(9, 'MS Word', 3),
(10, 'MS Excel', 3),
(11, 'MS Publisher', 3),
(12, 'MS Visio', 3),
(13, 'del user student', 4),
(14, 'net user student /del', 4),
(15, 'user net student /del', 4),
(16, 'userdel student', 4),
(17, 'xDSL', 5),
(18, 'GPRS', 5),
(19, 'HFC', 5),
(20, 'PLC', 5),
(21, 'Protokół do zdalnej pracy na odległym komputerze nie zapewnia kodowania transmisji', 6),
(22, 'Sesje SSH powodują wysyłanie zwykłego tekstu, niezaszyfrowanych danych', 6),
(23, 'Sesje SSH nie pozwalają określić, czy punkty końcowe są autentyczne', 6),
(24, 'Bezpieczny protokół terminalu sieciowego udostępniający usługi szyfrowania połączenia', 6),
(25, 'sysinfo', 7),
(26, 'ps', 7),
(27, 'proc', 7),
(28, 'sed', 7),
(29, '1,2,3,6', 8),
(30, '1,2,3,4', 8),
(31, '1,2,5,6', 8),
(32, '4,5,6,7', 8),
(33, 'Gwiazdy', 9),
(34, 'Siatki', 9),
(35, 'Trójkątu', 9),
(36, 'Podwójnego pierścienia', 9),
(37, 'Zaktualizować sterownik karty sieciowej', 10),
(38, 'Przelogować się na innego użytkownika', 10),
(39, 'Sprawdzić adres IP przypisany do karty sieciowej', 10),
(40, 'Zaktualizować system operacyjny', 10);


UPDATE `question`
  SET `correct_answer_id` = CASE `id`
    WHEN 1 THEN 4
    WHEN 2 THEN 5
    WHEN 3 THEN 11
    WHEN 4 THEN 16
    WHEN 5 THEN 19
    WHEN 6 THEN 24
    WHEN 7 THEN 26
    WHEN 8 THEN 29
    WHEN 9 THEN 35
    WHEN 10 THEN 39
  END
WHERE id IN (1,2,3,4,5,6,7,8,9,10);