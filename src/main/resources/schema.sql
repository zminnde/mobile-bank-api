create table if not exists customer
(
    id   int primary key auto_increment,
    user_name varchar,
    password varchar
);

create table if not exists account
(
    id             int primary key auto_increment,
    account_number varchar,
    balance        decimal(100, 2),
    customer_id    int,
    account_type   varchar check (account_type in ('REGULAR', 'SAVINGS')),
    constraint account_number_unique unique (account_number),
    constraint fk_account_customer foreign key (customer_id) references customer (id)
);

create index acc_number_idx on account (account_number)
