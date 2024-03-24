CREATE TABLE notification
(
    id           BIGSERIAL PRIMARY KEY,
    order_id     BIGINT  NOT NULL,
    company_id   BIGINT  NOT NULL REFERENCES company(id),
    message      TEXT    NOT NULL,
    was_received BOOLEAN NOT NULL
)
