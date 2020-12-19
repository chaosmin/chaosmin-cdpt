# 创建基础ROOT权限
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (1, null, 0, 'ROOT', '最高权限', null, null, NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (2, 1, 1, 'ROOT_READ', '最高读取权限', '/**', 'GET', NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (3, 1, 1, 'ROOT_SAVE', '最高创建权限', '/**', 'POST', NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (4, 1, 1, 'ROOT_UPDATE', '最高更新权限', '/**', 'PUT', NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.authority (id, parent_id, type, code, name, url, http_method, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (5, 1, 1, 'ROOT_REMOVE', '最高删除权限', '/**', 'DELETE', NOW(), 'system', NOW(), 'system', null, 0);

# 创建基础角色
INSERT INTO chaosmin_cdpt.role (id, code, name, priority, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (1, 'administrator', '系统管理员', 0,  NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.role (id, code, name, priority, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (2, 'manager', '主管', 1,  NOW(), 'system',  NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.role (id, code, name, priority, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (3, 'officer', '出单员', 2,  NOW(), 'system',  NOW(), 'system', null, 0);

# 添加基础角色权限关联关系
INSERT INTO chaosmin_cdpt.role_authority (id, role_id, authority_id, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (1, 1, 2, NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.role_authority (id, role_id, authority_id, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (2, 1, 3, NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.role_authority (id, role_id, authority_id, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (3, 1, 4, NOW(), 'system', NOW(), 'system', null, 0);
INSERT INTO chaosmin_cdpt.role_authority (id, role_id, authority_id, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (4, 1, 5, NOW(), 'system', NOW(), 'system', null, 0);

# 创建最高管理员
INSERT INTO chaosmin_cdpt.user (id, department_id, username, login_name, password, status, phone, email, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (1, null, 'ADMIN', 'ADMIN', '$2a$10$U6.GuUoRufnKCp/53rdiIuEMoJXJJ8VXXp6LRYgdLvM/R/vnL2VfK', 1, null, null, NOW(), 'system', NOW(), 'system', null, 0);

# 创建管理员角色
INSERT INTO chaosmin_cdpt.user_role (id, user_id, role_id, create_time, creator, update_time, updater, extra_info, is_deleted)
VALUES (1, 1, 1, NOW(), 'system', NOW(), 'system', null, 0);