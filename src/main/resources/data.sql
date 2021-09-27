/*
use midterm;
select * from role;
select * from user;
select * from admin;
select * from account_holder;
select * from account;
select * from student_checking_account;
select * from checking_account;
select * from credit_card_account;
select * from saving_account;
select * from account_secondary_owners;*/

INSERT INTO role (name) VALUES
("ACCOUNT_HOLDER"),
("ADMIN");
INSERT INTO user (`username`, `password`, `role_id`) VALUES
('Ula', '$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 1),
('Adam', '$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 1),
('Maryla', '$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 1),
('Janusz', '$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 1),
('Zofia', '$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 1),
('Boss','$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e', 2);
INSERT INTO admin (`id`) VALUES
(6);
INSERT INTO account_holder (`date_of_birth`, `mailing_address`, `city`,`country`,`postal_code`,`street_address`,`id`) VALUES
('1990-08-17', 'ula@gmail.pl','Warsaw','Poland',1000,'Szembeka 1',1),
('1976-06-16', 'adam@gmail.pl','Poznan','Poland',20000,'Targ 12',2),
('1951-07-27', 'maryla@gmail.pl','London','UK',23000,'Main 1',3),
('1951-03-23', 'janusz@gmail.pl','Heaven','Poland',34500,'Cloud',4),
('2010-03-17', 'zofia@gmail.pl','Lublin','Poland',43000,'Wola 1',5);
INSERT INTO account (`balance_amount`, `balance_currency`, `creation_date`,`secret_key`,`status`,`primary_owner_id`) VALUES
(1000, 'USD','1984-03-02','12345','ACTIVE',1),
(1000, 'USD','1999-03-02','55555','ACTIVE',2),
(1000, 'USD','2010-03-02','00000','ACTIVE',3),
(1000, 'USD','2020-03-02','23232','ACTIVE',5);
INSERT INTO student_checking_account (`id`) VALUES
(4);
INSERT INTO checking_account (`minimum_balance_amount`, `minimum_balance_currency`, `monthly_maintenance_fee_amount`,`monthly_maintenance_fee_currency`,
`penalty_fee_amount`,`penalty_fee_currency`,`id`) VALUES
(250, 'USD',12,'USD',0,'USD',1);
INSERT INTO credit_card_account (`credit_limit_amount`, `credit_limit_currency`, `interest_rate`,`last_interest_applied`,`id`) VALUES
(100, 'USD',0.2,'2020-03-02',2);
INSERT INTO saving_account (`interest_rate`, `last_interest_applied`, `minimum_balance_amount`,`minimum_balance_currency`,`penalty_fee_amount`,`penalty_fee_currency`,`id`) VALUES
(0.0025,'2020-03-02', 1000, 'USD',0,'USD',3);
INSERT INTO account_secondary_owners (`account_id`,`secondary_owner_id`) VALUES
(1,2),
(2,1),
(3,5),
(3,1);

