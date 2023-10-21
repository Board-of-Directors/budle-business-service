create table company
(
    id bigserial primary key,
    business_user_id bigint references business_user(id)
);