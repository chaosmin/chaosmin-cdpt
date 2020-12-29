create table partner
(
    id          bigint auto_increment primary key,
    code        varchar(64)                        not null comment '保司代码',
    name        varchar(64)                        null comment '保司名称',
    public_key  varchar(128)                       null comment '保司接口公钥',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64)                        null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64)                        null comment '更新人',
    extra_info  varchar(128)                       null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除'
) comment '保险公司表' charset = utf8;

create table product_category
(
    id                bigint auto_increment primary key,
    category_name     varchar(64)                        not null comment '父类名称',
    category_sub_name varchar(64)                        null comment '分类名称',
    sort              int      default 0                 not null comment '排序',
    is_show           int      default 1                 not null comment '是否展示',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator           varchar(64)                        null comment '创建人',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater           varchar(64)                        null comment '更新人',
    extra_info        varchar(128)                       null comment '扩展信息',
    is_deleted        smallint default 0                 not null comment '是否删除'
) comment '产品大类表' charset = utf8;

create unique index product_category_category_name_category_sub_name_uindex
    on product_category (category_name, category_sub_name);

create table product
(
    id                 bigint auto_increment primary key,
    partner_id         bigint                             not null comment '保司ID',
    product_code       varchar(64)                        not null comment '产品代码',
    product_name       varchar(64)                        null comment '产品名称',
    product_sub_name   varchar(64)                        null comment '产品子名称',
    partner_product_no varchar(64)                        null comment '保司产品编码',
    product_desc       varchar(256)                       null comment '产品描述',
    waiting_days       int      default 1                 not null comment '起保日期(T+N)',
    status             int      default 0                 not null comment '产品状态 0-暂存/1-有效/2-下架',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '产品定义表' charset = utf8;

create table product_category_relation
(
    id                  bigint auto_increment primary key,
    product_id          bigint                             not null comment '产品ID',
    product_category_id bigint                             not null comment '产品大类ID',
    create_time         datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator             varchar(64)                        null comment '创建人',
    update_time         datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater             varchar(64)                        null comment '更新人',
    extra_info          varchar(128)                       null comment '扩展信息',
    is_deleted          smallint default 0                 not null comment '是否删除'
) comment '产品分类表' charset = utf8;

create table product_plan
(
    id                       bigint auto_increment primary key,
    product_id               bigint                             not null comment '产品ID',
    plan_code                varchar(64)                        not null comment '计划代码',
    plan_name                varchar(64)                        null comment '计划名称',
    primary_coverage         varchar(64)                        null comment '主险保额',
    currency                 varchar(64)                        null comment '保额币种',
    default_commission_ratio double                             not null comment '默认佣金比例',
    status                   int      default 0                 not null comment '计划状态 0-暂存/1-有效/2-下架',
    create_time              datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator                  varchar(64)                        null comment '创建人',
    update_time              datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater                  varchar(64)                        null comment '更新人',
    extra_info               varchar(128)                       null comment '扩展信息',
    is_deleted               smallint default 0                 not null comment '是否删除'
) comment '产品计划表' charset = utf8;

create table product_plan_liability
(
    id                 bigint auto_increment primary key,
    product_plan_id    bigint                             not null comment '产品计划ID',
    product_plan_code  varchar(64)                        not null comment '产品计划Code',
    liability_category varchar(64)                        not null comment '责任大类(文本)',
    liability_name     varchar(64)                        not null comment '责任名称',
    liability_remark   varchar(256)                       not null comment '责任备注',
    sort               int      default 0                 not null comment '排序',
    amount             varchar(64)                        not null comment '保障金额',
    amount_remark      varchar(64)                        null comment '保障金额备注',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '产品计划责任表' charset = utf8;

create table product_plan_rate_table
(
    id                bigint auto_increment primary key,
    product_plan_id   bigint                             not null comment '产品计划ID',
    product_plan_code varchar(64)                        not null comment '产品计划Code',
    sort              int      default 0                 not null comment '排序',
    type              int                                not null comment '类型 0-公式 1-日 2-月 3-年',
    factor            int                                not null comment '费率因子',
    formula           varchar(256)                       not null comment '计算公式',
    premium           double                             not null comment '保费',
    premium_currency  varchar(64)                        not null comment '保费币种',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator           varchar(64)                        null comment '创建人',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater           varchar(64)                        null comment '更新人',
    extra_info        varchar(128)                       null comment '扩展信息',
    is_deleted        smallint default 0                 not null comment '是否删除'
) comment '产品计划费率表' charset = utf8;

create table product_plan_commission
(
    id                bigint auto_increment primary key,
    product_plan_id   bigint                             not null comment '产品计划ID',
    product_plan_code varchar(64)                        not null comment '产品计划Code',
    commission_ratio  double                             not null comment '最高佣金比例',
    status            int      default 1                 not null comment '计划状态 0-无效/1-有效',
    settle_date       datetime                           not null comment '设置时间',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator           varchar(64)                        null comment '创建人',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater           varchar(64)                        null comment '更新人',
    extra_info        varchar(128)                       null comment '扩展信息',
    is_deleted        smallint default 0                 not null comment '是否删除'
) comment '产品计划佣金表' charset = utf8;

create table product_external
(
    id            bigint auto_increment primary key,
    product_id    bigint                             not null comment '产品ID',
    external_text text                               null comment '扩展文本',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator       varchar(64)                        null comment '创建人',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater       varchar(64)                        null comment '更新人',
    extra_info    varchar(128)                       null comment '扩展信息',
    is_deleted    smallint default 0                 not null comment '是否删除'
) comment '产品特约及须知表' charset = utf8;

create table knowledge
(
    id             bigint auto_increment primary key,
    type           int                                not null comment '文本类型 0-投保须知/1-投保声明/2-特别约定/3-争议处理/4-司法管辖/5-健康告知/6-保险案例',
    knowledge_code varchar(64)                        not null comment '知识编码',
    knowledge_text text                               not null comment '文本',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator        varchar(64)                        null comment '创建人',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater        varchar(64)                        null comment '更新人',
    extra_info     varchar(128)                       null comment '扩展信息',
    is_deleted     smallint default 0                 not null comment '是否删除'
) comment '产品定义知识库' charset = utf8;

create table res_data
(
    id          bigint auto_increment primary key,
    item_code   varchar(64)                        not null comment '编码',
    item_name   varchar(64)                        not null comment '名称',
    item_desc   varchar(64)                        null comment '描述',
    extend1     varchar(64)                        null comment '扩展字段1',
    extend2     varchar(64)                        null comment '扩展字段2',
    extend3     varchar(64)                        null comment '扩展字段3',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64)                        null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64)                        null comment '更新人',
    extra_info  varchar(128)                       null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除'
) comment '通用码表' charset = utf8;