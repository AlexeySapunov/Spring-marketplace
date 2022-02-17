INSERT INTO `users` (`username`, `password`)
    VALUE ('admin', 'v9Y|tKcoHHDbXY~o~3NHDNn~Nwu3WG@Lua9n82Y5|Y~0he$id1'),
          ('guest', 'VbYNA5DwIFgUmvMo~cw4w6vlLvYkBS4az$UmVsYsn{*ApAIZmS');
GO

INSERT INTO `roles` (`name`)
    VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles` (`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `username` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `username` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');
GO