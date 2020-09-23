CREATE TABLE user(
    `id`            SERIAL NOT NULL,
    `slack_id`      VARCHAR(255) NOT NULL,
    `name`          VARCHAR(255) NOT NULL,
    `channel`       VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE uq_user (slack_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE observed_reaction(
    `id`            SERIAL NOT NULL,
    `slack_id`      VARCHAR(255) NOT NULL,
    `name`          VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE uq_user_reaction (slack_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stacked_message(
    `id`            SERIAL NOT NULL,
    `slack_id`      VARCHAR(255) NOT NULL,
    `path`          VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE uq_user_reaction (slack_id, path)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
