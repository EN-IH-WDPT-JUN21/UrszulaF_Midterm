/*CREATE USER 'tester'@'localhost' IDENTIFIED BY 'tester123';

GRANT ALL PRIVILEGES ON *.* TO 'tester'@'localhost';

FLUSH PRIVILEGES;

create database midterm;
use midterm;

show tables;

select * from role;
select * from user;
select * from admin;
select * from account_holder;
select * from account;
select * from student_checking_account;
select * from checking_account;
select * from credit_card_account;
select * from saving_account;
select * from account_secondary_owners;
*/
/*drop tables doctor, patient;*/

/*
DROP TABLE if exists role;
DROP TABLE if exists user;
DROP TABLE if exists admin;
DROP TABLE if exists account_holder;

CREATE TABLE `role` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` VARCHAR(255)
);

CREATE TABLE `user` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `username` VARCHAR(255),
    `password` VARCHAR(255),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `admin` (
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `account_holder` (
  `date_of_birth` date,
  `mailing_address` varchar(255),
  `city` varchar(255),
  `country` varchar(255),
  `postal_code` int,
  `street_address` varchar(255),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `account` (
  `id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `balance_amount` decimal(19,2) DEFAULT 0,
  `balance_currency` varchar(255) DEFAULT 'USD',
  `creation_date` datetime(6),
  `secret_key` varchar(255),
  `status` varchar(255) DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`primary_owner_id`) REFERENCES `account_holder` (`id`)
);

CREATE TABLE `student_checking_account` (
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `checking_account` (
  `minimum_balance_amount` decimal(19,2) DEFAULT 250,
  `minimum_balance_currency` varchar(255) DEFAULT 'USD',
  `monthly_maintenance_fee_amount` decimal(19,2) DEFAULT 120,
  `monthly_maintenance_fee_currency` varchar(255) DEFAULT 'USD',
  `penalty_fee_amount` decimal(19,2) DEFAULT 0,
  `penalty_fee_currency` varchar(255) DEFAULT 'USD',
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `credit_card_account` (
  `credit_limit_amount` decimal(19,2) DEFAULT 100,
  `credit_limit_currency` varchar(255) DEFAULT 'USD',
  `interest_rate` decimal(7,4) DEFAULT 0.2,
  `last_interest_applied` datetime(6),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `credit_card_account` (
  `credit_limit_amount` decimal(19,2) DEFAULT 100,
  `credit_limit_currency` varchar(255) DEFAULT 'USD',
  `interest_rate` decimal(7,4) DEFAULT 0.2,
  `last_interest_applied` datetime(6),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `saving_account` (

  `interest_rate` decimal(5,4) DEFAULT 0.0025,
  `last_interest_applied` datetime(6),
  `minimum_balance_amount` decimal(19,2) DEFAULT 1000,
  `minimum_balance_currency` varchar(255) DEFAULT 'USD',
  `penalty_fee_amount` decimal(19,2) DEFAULT 0,
  `penalty_fee_currency` varchar(255) DEFAULT 'USD',
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `secondary_owner_id` (

    FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
	FOREIGN KEY (`secondary_owner_id`) REFERENCES `account_holder` (`id`)
);
*/

