create table goods
(
    id              bigint auto_increment primary key,
    department_id   bigint                             not null comment '机构ID',
    product_id      bigint                             not null comment '产品ID',
    product_plan_id varchar(64)                        not null comment '可用计划ID集合',
    status          int      default 1                 not null comment '商品状态',
    show_start_time datetime                           not null comment '展示开始时间',
    show_end_time   datetime                           not null comment '展示结束时间',
    coms_ratio      double                             not null comment '佣金比例',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator         varchar(64)                        null comment '创建人',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater         varchar(64)                        null comment '更新人',
    extra_info      varchar(128)                       null comment '扩展信息',
    is_deleted      smallint default 0                 not null comment '是否删除'
) comment '保险商品表' charset = utf8;

create table goods_plan
(
    id                bigint auto_increment primary key,
    goods_id          bigint                             not null comment '商品ID',
    user_id           bigint                             not null comment '用户ID',
    product_plan_id   bigint                             not null comment '产品计划ID',
    partner_code      varchar(64)                        not null comment '保司编码',
    partner_name      varchar(64)                        not null comment '保司名称',
    category_name     varchar(64)                        not null comment '一级大类',
    category_sub_name varchar(64)                        not null comment '二级大类',
    goods_code        varchar(64)                        null comment '商品编码',
    goods_name        varchar(64)                        not null comment '商品名称',
    status            int      default 1                 not null comment '商品状态',
    is_for_sale       int      default 1                 not null comment '是否可售',
    sale_start_time   datetime                           not null comment '售卖开始时间',
    sale_end_time     datetime                           not null comment '售卖结束时间',
    authorizer        varchar(64)                        not null comment '授权人',
    authorize_time    datetime                           not null comment '授权时间',
    coms_ratio        double                             not null comment '佣金比例',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator           varchar(64)                        null comment '创建人',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater           varchar(64)                        null comment '更新人',
    extra_info        varchar(128)                       null comment '扩展信息',
    is_deleted        smallint default 0                 not null comment '是否删除'
) comment '出单计划表' charset = utf8;

