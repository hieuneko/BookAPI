INSERT INTO users (id, username, password, first_name, last_name, enabled, avatar, role_id)
VALUES ('ba1ae405-9a1d-412c-8f80-bb39f70b6921', 'CRON_JOB',
        '$2a$12$3GjmcPUu7PpwwBreqaD4quYa.ZzHOjHtI7ZFbWV.i9MVwfIUTuusS',
        'CRON', 'JOB', true, 'avatar4.jpg',
        (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));