ALTER TABLE `ausempi_car_rental`.`users`
CHANGE COLUMN `last_name` `last_name` VARCHAR(100) NULL ,
CHANGE COLUMN `employee_id` `employee_id` VARCHAR(50) NOT NULL ;

CREATE TABLE videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NULL,
    description TEXT,
    video_url TEXT NOT NULL,
    duration_seconds INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id BIGINT NOT NULL,
    question_text TEXT NOT NULL,
    question_order INT,

    FOREIGN KEY (video_id) REFERENCES videos(id)
        ON DELETE CASCADE,

    INDEX idx_video (video_id)
) ENGINE=InnoDB;

CREATE TABLE options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    option_text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (question_id) REFERENCES questions(id)
        ON DELETE CASCADE,

    INDEX idx_question (question_id)
) ENGINE=InnoDB;
