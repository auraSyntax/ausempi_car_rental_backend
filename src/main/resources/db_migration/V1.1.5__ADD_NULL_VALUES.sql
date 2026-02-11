ALTER TABLE `ausempi_car_rental`.`questions`
DROP FOREIGN KEY `questions_ibfk_1`;
ALTER TABLE `ausempi_car_rental`.`questions`
CHANGE COLUMN `video_id` `video_id` BIGINT NULL ;
ALTER TABLE `ausempi_car_rental`.`questions`
ADD CONSTRAINT `questions_ibfk_1`
  FOREIGN KEY (`video_id`)
  REFERENCES `ausempi_car_rental`.`videos` (`id`)
  ON DELETE CASCADE;

ALTER TABLE `ausempi_car_rental`.`options`
DROP FOREIGN KEY `options_ibfk_1`;
ALTER TABLE `ausempi_car_rental`.`options`
CHANGE COLUMN `question_id` `question_id` BIGINT NULL ;
ALTER TABLE `ausempi_car_rental`.`options`
ADD CONSTRAINT `options_ibfk_1`
  FOREIGN KEY (`question_id`)
  REFERENCES `ausempi_car_rental`.`questions` (`id`)
  ON DELETE CASCADE;

