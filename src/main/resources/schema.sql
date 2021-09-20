/*CREATE USER 'tester'@'localhost' IDENTIFIED BY 'tester123';

GRANT ALL PRIVILEGES ON *.* TO 'tester'@'localhost';

FLUSH PRIVILEGES;

create database midterm;
use midterm;

show tables;

select * from credit_card_account;
select * from patient;

*/
/*drop tables doctor, patient;*/

/*
DROP TABLE if exists patient;
DROP TABLE if exists doctor;

CREATE TABLE `doctor` (
  `employee_id` bigint NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
);

CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `admitted_by` bigint DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  FOREIGN KEY (`admitted_by`) REFERENCES `doctor` (`employee_id`)
);*/

