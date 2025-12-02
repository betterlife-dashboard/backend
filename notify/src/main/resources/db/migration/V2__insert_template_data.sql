INSERT INTO notification_templates
(event_type, channel_type, lang, title_template, body_template, created_at, updated_at)
VALUES
    ('SCHEDULE_DEADLINE', 'WEB', 'KO',
     '{title} 마감이 임박했어요',
     '{deadline}까지 완료해야 해요.',
     NOW(), NOW()
    ),
    ('SCHEDULE_REMINDER', 'WEB', 'KO',
     '{title} 할 시간이에요.',
     '{timeLeft} 남았습니다.',
     NOW(), NOW()
    ),
    ('SCHEDULE_DEADLINE', 'MOBILE', 'KO',
     '{title} 마감 알림',
     '{timeLeft} 후 마감',
     NOW(), NOW()
    ),
    ('SCHEDULE_REMINDER', 'MOBILE', 'KO',
     '리마인더: {title}',
     '{title} 준비',
     NOW(), NOW()
    );