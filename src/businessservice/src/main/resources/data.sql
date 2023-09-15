-- 테이블 생성

CREATE TABLE IF NOT EXISTS events_basic (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  title VARCHAR(100),
  image VARCHAR(1000),
  event_start_at DATETIME NULL,
  event_end_at DATETIME NULL,
  participate_num BIGINT NULL,
  location VARCHAR(50),
  category BIGINT,
  is_active BOOLEAN DEFAULT true,
  is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS event_details (
    id BIGINT PRIMARY KEY,
    content VARCHAR(2000),
    apply_start_at DATETIME NULL,
    apply_end_at DATETIME NULL,
    views BIGINT DEFAULT(0),
    delete_date DATETIME NULL,
    update_date DATETIME NULL,
    create_date DATETIME NULL
);

CREATE TABLE IF NOT EXISTS tickets (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255),
     price BIGINT,
     quantity INT,
     event_id BIGINT,
     is_deleted BOOLEAN DEFAULT false
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- -- events 테이블 더미 데이터 삽입
-- INSERT INTO events (id, host_id, title, image, event_start_at, event_end_at, participate_num, location, category)
-- VALUES
--     (1, 1, 'Event 1', 'image1.jpg', '2023-08-20 10:00:00', '2023-08-21 18:00:00', 100, 'Location A', 'CategoryEntity X'),
--     (2, 2, 'Event 2', 'image2.jpg', '2023-08-22 14:00:00', '2023-08-23 22:00:00', 150, 'Location B', 'CategoryEntity Y'),
--     (3, 3, 'Event 3', 'image3.jpg', '2023-08-25 09:00:00', '2023-08-26 17:00:00', 120, 'Location C', 'CategoryEntity Z');
--
-- -- event_details 테이블 더미 데이터 삽입
-- INSERT INTO event_details (id, content, apply_start_at, apply_end_at, views, delete_date, update_date, create_date)
-- VALUES
--     (1, 'Detail for Event 1', '2023-08-18 10:00:00', '2023-08-20 18:00:00', 500, NULL, NULL, '2023-08-17 09:00:00'),
--     (2, 'Detail for Event 2', '2023-08-20 14:00:00', '2023-08-22 22:00:00', 350, NULL, NULL, '2023-08-19 11:00:00'),
--     (3, 'Detail for Event 3', '2023-08-23 09:00:00', '2023-08-24 17:00:00', 420, NULL, NULL, '2023-08-22 08:00:00');
