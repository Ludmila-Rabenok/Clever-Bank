drop table checks;
drop table transactions;
drop table accounts;
drop table banks;
drop table users;


create table banks
(id bigserial not null,
name varchar(50) not null,
constraint pk_banks primary key(id),
constraint name_unique unique ("name"));

create table users
(id bigserial not null,
full_name varchar(50) not null,
constraint pk_users primary key(id));

create table accounts
(id bigserial not null,
number bigserial not null,
user_id int not null,
bank_id int not null,
currency varchar(5) not null check(currency = 'BYN' or currency = 'RUB' or currency = 'EUR' or currency = 'USD') default 'BYN',
date_open_account timestamp default now(),
balance numeric,
monthly_percentage numeric,
constraint pk_accounts primary key(id),
foreign key (bank_id) references banks(id) on delete cascade on update restrict,
foreign key (user_id) references users(id) on delete cascade on update restrict);

create table transactions
(id bigserial not null,
date_time timestamp default now(),
type_transaction varchar(20) not null check(type_transaction = 'WITHDRAWALS' or type_transaction = 'REPLENISHMENT' or type_transaction = 'TRANSFER_TO_CARD' or type_transaction = 'REPLENISHMENT_FROM'),
sum numeric,
account1_id int not null,
account2_id int,
constraint pk_transactions primary key(id),
foreign key (account1_id) references accounts(id) on delete cascade on update restrict,
foreign key (account2_id) references accounts(id) on delete cascade on update restrict);

create table checks
(id bigserial not null,
transaction_id int not null,
constraint pk_checks primary key(id),
foreign key (transaction_id) references transactions(id)on delete cascade on update restrict);

insert into banks(name)
values('Clever-Bank'),
      ('Belarusbank'),
      ('Alfa-Bank'),
      ('Belagroprom-Bank'),
      ('Belinvest-Bank'),
      ('Paritet-bank');
     
insert into users(full_name)
values('Рабенок Людмила Сергеевна'),
     ('Иванов Иван Иванович'),
     ('Пушкин Александр Сергеевич'),
     ('Завгороднев Дмитрий Анатольевич'),
     ('Бараян Генадий Сергеевич'),
     ('Карамзин Юрий Павлович'),
     ('Кравцова Инна Андреевна'),
     ('Василькова Кира Игоревна'),
     ('Александров Владимир Владимирович'),
     ('Ахматова Инна Алексеевна'),
     ('Пулуян Инга Евгеньевна'),
     ('Алексеенко Алексей Иванович'),
     ('Михалкова Ирина Генадьевна'),
     ('Кравченко Николай Степанович'),
     ('Любимов Евгений Сергеевич'),
     ('Паршута Дмитрий Олегович'),
     ('Королева Наталья Ивановна'),
     ('Сапоненко Тамара Игоревна'),
     ('Толстой Иван Иванович'),
     ('Зацепин Николай Николаевич'),
     ('Флягин Степан Степанович');
      
insert into accounts(number, user_id, bank_id, currency, balance)
values(123456789, 1, 1,'BYN', 1000.2),
      (123456790, 2, 1,'BYN', 5000.55),
      (123456791, 3, 1,'BYN', 100.47),
      (123456792, 4, 1,'BYN', 600.0),
      (123456793, 5, 1,'BYN', 40.2),
      (123456794, 6, 1,'BYN', 55000.5),
      (123456795, 7, 1,'BYN', 11000.27),
      (123456796, 8, 1,'BYN', 50500.5),
      (123456797, 9, 1,'BYN', 1400.38),
      (123456798, 10, 1,'BYN', 960.9),
      (123456799, 11, 1,'BYN', 100.2),
      (123456800, 12, 1,'BYN', 50070.55),
      (123456802, 13, 1,'BYN', 105.47),
      (123456803, 14, 1,'BYN', 680.0),
      (123456804, 15, 1,'BYN', 409.2),
      (123456805, 16, 1,'BYN', 5505.5),
      (123456806, 17, 1,'BYN', 11070.27),
      (123456807, 18, 1,'BYN', 5100.5),
      (123456808, 19, 1,'BYN', 14010.38),
      (123456809, 20, 1,'BYN', 9650.9),
      (123456810, 1, 1,'BYN', 1090.2),
      (123456811, 2, 2,'BYN', 5040.55),
      (123456812, 3, 3,'BYN', 1400.47),
      (123456813, 4, 4,'BYN', 6033.0),
      (123456814, 5, 5,'BYN', 4046.2),
      (123456815, 6, 6,'BYN', 52400.5),
      (123456816, 7, 1,'BYN', 190.27),
      (123456817, 8, 2,'BYN', 55600.5),
      (123456818, 9, 3,'BYN', 140.38),
      (123456819, 10, 4,'BYN', 965.9),
      (123456820, 11, 5,'BYN', 1030.2),
      (123456821, 12, 6,'BYN', 5030.55),
      (123456822, 13, 1,'BYN', 130.47),
      (123456823, 14, 2,'BYN', 630.0),
      (123456824, 15, 3,'BYN', 405.2),
      (123456825, 16, 4,'BYN', 53000.5),
      (123456826, 17, 5,'BYN', 11000.27),
      (123456827, 18, 6,'BYN', 55800.5),
      (123456828, 20, 1,'BYN', 1460.38),
      (123456829, 21, 1,'BYN', 130.9);
     
     
     select full_name from users ;
     
      