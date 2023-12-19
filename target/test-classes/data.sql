INSERT INTO user_roles (id, user_role)
SELECT * FROM (
    SELECT 1 as id, 'ADMIN' as user_role
    UNION ALL
    SELECT 2 as id, 'USER' as user_role
) AS new_roles
WHERE NOT EXISTS (
    SELECT 1 FROM user_roles
    WHERE user_role IN ('ADMIN', 'USER')
);

INSERT INTO users (id, username, first_name, last_name, age, email, password, is_active)
SELECT * FROM (
    SELECT
        1 as id,
        'petko' as username,
        'Admincho' as first_name,
        'Adminov' as last_name,
        18 as age,
        'admin@example.com' as email,
        'ae5fa5dd8ccde035084e4aee4474c4b192df9dedf3a7c35dfaf837c900b2abcefaee1d0c1b330a42' as password,
        1 as is_active
    UNION ALL
    SELECT
        2 as id,
        'shestko' as username,
        'Usercho' as first_name,
        'Userov' as last_name,
        18 as age,
        'user@example.com' as email,
        'ae5fa5dd8ccde035084e4aee4474c4b192df9dedf3a7c35dfaf837c900b2abcefaee1d0c1b330a42' as password,
        1 as is_active
) AS new_users
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE id IN (1, 2)
);


INSERT INTO users_user_roles (user_id, user_roles_id)
SELECT * FROM (
    SELECT
        1 as user_id,
        1 as user_roles_id
    UNION ALL
    SELECT
        2 as user_id,
        2 as user_roles_id
) AS new_user_roles
WHERE NOT EXISTS (
    SELECT 1 FROM users_user_roles
    WHERE (user_id, user_roles_id) IN ((1, 1), (2, 2))
);