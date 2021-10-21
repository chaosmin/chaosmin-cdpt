INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator,
                                     update_time, updater, extra_info, is_deleted)
VALUES (75, null, 0, 'LETTER_HEAD', '投保抬头管理', '/v1/api/letter-head', null, '2021-10-21 13:58:05', 'system',
        '2021-10-21 13:58:05', 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator,
                                     update_time, updater, extra_info, is_deleted)
VALUES (76, '75', 1, 'LETTER_HEAD_READ', '投保抬头查询权限', '/v1/api/letter-head/**', 'GET', '2021-10-21 13:58:05', 'system',
        '2021-10-21 13:58:05', 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator,
                                     update_time, updater, extra_info, is_deleted)
VALUES (77, '75', 1, 'LETTER_HEAD_SAVE', '投保抬头创建权限', '/v1/api/letter-head/**', 'POST', '2021-10-21 13:58:05', 'system',
        '2021-10-21 13:58:05', 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator,
                                     update_time, updater, extra_info, is_deleted)
VALUES (78, '75', 1, 'LETTER_HEAD_UPDATE', '投保抬头更新权限', '/v1/api/letter-head/**', 'PUT', '2021-10-21 13:58:05', 'system',
        '2021-10-21 13:58:05', 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator,
                                     update_time, updater, extra_info, is_deleted)
VALUES (79, '75', 1, 'LETTER_HEAD_REMOVE', '投保抬头删除权限', '/v1/api/letter-head/**', 'DELETE', '2021-10-21 13:58:05',
        'system', '2021-10-21 13:58:05', 'system', null, 0);