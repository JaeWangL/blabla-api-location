DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id`            varchar(255)     NOT NULL,
    `device_type`   smallint(6)      NOT NULL,
    `device_id`     varchar(1024)    NOT NULL,
    `latitude`      decimal(11, 7)   NOT NULL,
    `longitude`     decimal(11, 7)   NOT NULL,
    `created_at`    timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
