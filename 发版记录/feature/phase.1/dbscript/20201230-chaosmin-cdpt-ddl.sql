create table `order`
(
    id                 bigint auto_increment primary key,
    order_no           varchar(64)                        not null comment '订单号',
    product_plan_id    bigint                             not null comment '产品计划ID',
    start_time         datetime                           not null comment '起保时间',
    end_time           datetime                           not null comment '终止时间',
    travel_destination varchar(256)                       null comment '旅行目的地',
    status             smallint(6)                        not null comment '订单状态 0-暂存 1-已出单 2-废弃',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '订单表' charset = utf8;

create table policy
(
    id                 bigint auto_increment primary key,
    order_no           varchar(64)                        not null comment '订单号',
    policy_no          varchar(64)                        not null comment '保单号',
    product_plan_id    bigint                             not null comment '产品计划ID',
    effective_time     datetime                           not null comment '起保时间',
    expiry_time        datetime                           not null comment '终止时间',
    travel_destination varchar(256)                       null comment '旅行目的地',
    status             smallint(6)                        not null comment '0-待出单 1-已出单 2-出单失败',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '保单表' charset = utf8;

create table policy_holder
(
    id                    bigint auto_increment primary key,
    order_id              bigint                             not null comment '订单ID',
    policy_id             bigint                             not null comment '保单ID',
    main_insured_relation smallint(6)                        not null comment '与主被保人关系',
    party_type            smallint(6)                        not null comment '实体类型 1-个人客户 2-公司客户',
    name                  varchar(64)                        not null comment '姓名',
    certi_type            smallint(6)                        null comment '证件类型',
    certi_no              varchar(64)                        null comment '证件号码',
    birthday              datetime                           null comment '生日',
    gender                smallint(6)                        null comment '性别',
    phone_no              varchar(16)                        null comment '电话号码',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator               varchar(64)                        null comment '创建人',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater               varchar(64)                        null comment '更新人',
    extra_info            varchar(128)                       null comment '扩展信息',
    is_deleted            smallint default 0                 not null comment '是否删除'
) comment '投保人表' charset = utf8;

create table policy_insurant
(
    id          bigint auto_increment primary key,
    order_id    bigint                             not null comment '订单ID',
    policy_id   bigint                             not null comment '保单ID',
    party_type  smallint(6)                        not null comment '实体类型 1-个人客户 2-公司客户',
    name        varchar(64)                        not null comment '姓名',
    certi_type  smallint(6)                        null comment '证件类型',
    certi_no    varchar(64)                        null comment '证件号码',
    birthday    datetime                           null comment '生日',
    gender      smallint(6)                        null comment '性别',
    phone_no    varchar(16)                        null comment '电话号码',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64)                        null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64)                        null comment '更新人',
    extra_info  varchar(128)                       null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除'
) comment '被保人表' charset = utf8;

