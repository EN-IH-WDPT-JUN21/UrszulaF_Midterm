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
select * from transaction;
select * from third_party_transaction_send;
select * from third_party;
select * from third_party_transaction_receive;
*/
/*drop tables third_party, third_party_transaction_send;*/

/*
DROP TABLE if exists role;
DROP TABLE if exists user;
DROP TABLE if exists admin;
DROP TABLE if exists account_holder;
DROP TABLE if exists third_party;
DROP TABLE if exists account;
DROP TABLE if exists student_checking_account;
DROP TABLE if exists checking_account;
DROP TABLE if exists credit_card_account;
DROP TABLE if exists saving_account;
DROP TABLE if exists account_secondary_owners;
DROP TABLE if exists transaction;
DROP TABLE if exists third_party_transaction_send;
DROP TABLE if exists third_party_transaction_receive;

CREATE TABLE `role` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255),
      PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `username` VARCHAR(255),
    `password` VARCHAR(255),
	`role_id` bigint,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `admin` (
	`id` bigint,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `user` (`id`)
);

CREATE TABLE `account_holder` (
`id` bigint,
  `date_of_birth` date,
  `mailing_address` varchar(255),
  `city` varchar(255),
  `country` varchar(255),
  `postal_code` int,
  `street_address` varchar(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `user` (`id`)
);

CREATE TABLE `third_party` (
`id` bigint AUTO_INCREMENT NOT NULL,
  `hashed_key` varchar(255),
  `name` varchar(255),
  PRIMARY KEY (`id`)
);


CREATE TABLE `account` (
  `id` bigint AUTO_INCREMENT NOT NULL,
  `balance_amount` decimal(19,2) DEFAULT 0,
  `balance_currency` varchar(255) DEFAULT 'USD',
  `creation_date` datetime(6),
  `secret_key` varchar(255),
  `status` varchar(255) DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`primary_owner_id`) REFERENCES `account_holder` (`id`)
);

CREATE TABLE `student_checking_account` (
	`id` bigint,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `checking_account` (
	`id` bigint,
  `minimum_balance_amount` decimal(19,2) DEFAULT 250,
  `minimum_balance_currency` varchar(255) DEFAULT 'USD',
  `monthly_maintenance_fee_amount` decimal(19,2) DEFAULT 120,
  `monthly_maintenance_fee_currency` varchar(255) DEFAULT 'USD',
  `penalty_fee_amount` decimal(19,2) DEFAULT 0,
  `penalty_fee_currency` varchar(255) DEFAULT 'USD',
  	PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `credit_card_account` (
	`id` bigint,
  `credit_limit_amount` decimal(19,2) DEFAULT 100,
  `credit_limit_currency` varchar(255) DEFAULT 'USD',
  `interest_rate` decimal(7,4) DEFAULT 0.2,
  `last_interest_applied` datetime(6),
  	PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `saving_account` (
	`id` bigint,
  `interest_rate` decimal(5,4) DEFAULT 0.0025,
  `last_interest_applied` datetime(6),
  `minimum_balance_amount` decimal(19,2) DEFAULT 1000,
  `minimum_balance_currency` varchar(255) DEFAULT 'USD',
  `penalty_fee_amount` decimal(19,2) DEFAULT 0,
  `penalty_fee_currency` varchar(255) DEFAULT 'USD',
	PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `account` (`id`)
);

CREATE TABLE `account_secondary_owners` (
	`account_id` bigint,
	`secondary_owner_id` bigint,
  	PRIMARY KEY (`account_id`),
    FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
	FOREIGN KEY (`secondary_owner_id`) REFERENCES `account_holder` (`id`)
);

CREATE TABLE `transaction` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
  `amount` decimal(19,2),
  `currency` varchar(255) DEFAULT 'USD',
   `time_stamp` datetime(6),
  `transaction_type` VARCHAR(255),
    	PRIMARY KEY (`id`),
    FOREIGN KEY (`recipient_account_id`) REFERENCES `account` (`id`),
	FOREIGN KEY (`sender_account_id`) REFERENCES `account` (`id`)
);

CREATE TABLE `third_party_transaction_send` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
  `amount` decimal(19,2),
  `currency` varchar(255) DEFAULT 'USD',
   `time_stamp` datetime(6),
  `transaction_type` VARCHAR(255),
  `recipient_account_id` bigint,
  `sender_third_party_id` bigint,
    	PRIMARY KEY (`id`),
    FOREIGN KEY (`recipient_account_id`) REFERENCES `account` (`id`),
	FOREIGN KEY (`sender_third_party_id`) REFERENCES `third_party` (`id`)
);

CREATE TABLE `third_party_transaction_receive` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
  `amount` decimal(19,2),
  `currency` varchar(255) DEFAULT 'USD',
   `time_stamp` datetime(6),
  `transaction_type` VARCHAR(255),
    `recipient_third_party_id` bigint,
  `sender_account_id` bigint,
    	PRIMARY KEY (`id`),
    FOREIGN KEY (`sender_account_id`) REFERENCES `account` (`id`),
	FOREIGN KEY (`recipient_third_party_id`) REFERENCES `third_party` (`id`)
);

*/

