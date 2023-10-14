create table company
(
    id bigserial primary key
);

create table business_user_creator_of_companies
(
    business_user_id        bigint references business_user (id),
    creator_of_companies_id bigint references company (id)
)