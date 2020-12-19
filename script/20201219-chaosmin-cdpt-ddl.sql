create table chaosmin_cdpt.department
(
    id          bigint auto_increment primary key,
    code        varchar(64)                        not null comment '编号',
    name        varchar(64) null comment '名称',
    status      int      default 1                 not null comment '状态',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64) null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64) null comment '更新人',
    extra_info  varchar(128) null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除',
    constraint department_code_uindex unique (code)
) comment '部门表';

create table chaosmin_cdpt.user
(
    id            bigint auto_increment primary key,
    department_id bigint null comment '部门ID',
    username      varchar(64)                        not null comment '用户名',
    login_name    varchar(64)                        not null comment '登录名称',
    password      varchar(128)                       not null comment '登录密码',
    status        int      default 1                 not null comment '用户状态',
    phone         varchar(16) null comment '手机号码',
    email         varchar(128) null comment '邮箱',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator       varchar(64) null comment '创建人',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater       varchar(64) null comment '更新人',
    extra_info    varchar(128) null comment '扩展信息',
    is_deleted    smallint default 0                 not null comment '是否删除',
    constraint user_login_name_uindex unique (login_name)
) comment '用户表';

create table chaosmin_cdpt.role
(
    id          bigint auto_increment primary key,
    code        varchar(64)                        not null comment '编号',
    name        varchar(64) null comment '名称',
    priority    int      default 0                 not null comment '优先级',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64) null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64) null comment '更新人',
    extra_info  varchar(128) null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除',
    constraint role_code_uindex unique (code)
) comment '角色表';

create table chaosmin_cdpt.authority
(
    id          bigint auto_increment primary key,
    parent_id   mediumtext null comment '父权限',
    type        int                                not null comment '权限类型 0-根节点/1-接口权限/2-按钮权限',
    code        varchar(64)                        not null comment '编号',
    name        varchar(64) null comment '名称',
    url         varchar(128) null comment '访问路径',
    http_method varchar(8) null comment '权限',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64) null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64) null comment '更新人',
    extra_info  varchar(128) null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除',
    constraint authority_code_uindex unique (code)
) comment '权限表';

create table chaosmin_cdpt.role_authority
(
    id           bigint auto_increment primary key,
    role_id      bigint                             not null comment '角色id',
    authority_id bigint                             not null comment '权限id',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator      varchar(64) null comment '创建人',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater      varchar(64) null comment '更新人',
    extra_info   varchar(128) null comment '扩展信息',
    is_deleted   smallint default 0                 not null comment '是否删除',
    constraint role_authority_role_id_authority_id_uindex unique (role_id, authority_id)
) comment '角色权限表';

create table chaosmin_cdpt.user_role
(
    id          bigint auto_increment primary key,
    user_id     bigint                             not null comment '用户id',
    role_id     bigint                             not null comment '角色id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64) null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64) null comment '更新人',
    extra_info  varchar(128) null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除',
    constraint user_role_user_id_role_id_uindex unique (user_id, role_id)
) comment '用户角色表';