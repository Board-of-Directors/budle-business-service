CREATE TABLE user_session
(
    id                 BIGSERIAL PRIMARY KEY,
    refresh_token_uuid VARCHAR   NOT NULL UNIQUE,
    business_user_id   BIGINT    NOT NULL,
    expire_at          TIMESTAMP NOT NULL,
    create_at          TIMESTAMP NOT NULL,
    update_at          TIMESTAMP NOT NULL
);

COMMENT ON TABLE user_session IS 'Таблица сессии пользователя';

COMMENT ON COLUMN user_session.refresh_token_uuid IS 'Refresh токен';
COMMENT ON COLUMN user_session.business_user_id IS 'Id пользователя';
COMMENT ON COLUMN user_session.expire_at IS 'Время жизни токена';
COMMENT ON COLUMN user_session.create_at IS 'Время создания токена';
COMMENT ON COLUMN user_session.update_at IS 'Время обновления токена';

CREATE INDEX user_session_refresh_toke_uuid_index ON user_session (refresh_token_uuid);
CREATE INDEX business_user_user_session_index ON user_session (business_user_id);
