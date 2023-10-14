create table business_user
(
    id           bigserial primary key,
    first_name   varchar,
    middle_name  varchar,
    last_name    varchar,
    phone_number varchar,
    password     varchar,
    email        varchar,
    login        varchar
)