create table company_workers
(
    id         bigserial primary key,
    company_id bigint references company (id),
    worker_id  bigint references business_user (id)
)