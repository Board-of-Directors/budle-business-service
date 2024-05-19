CREATE TABLE available_option
(
    id               BIGSERIAL PRIMARY KEY,
    company_id       BIGINT  NOT NULL REFERENCES company (id),
    business_user_id BIGINT  NOT NULL REFERENCES business_user (id),
    option           VARCHAR NOT NULL
);


CREATE INDEX available_option_company_id_idx ON available_option (company_id);
CREATE INDEX available_options_business_user_id_idx ON available_option (business_user_id);
CREATE INDEX available_options_option_idx ON available_option (option);
