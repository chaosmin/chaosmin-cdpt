create table partner
(
    id          bigint auto_increment primary key,
    code        varchar(64)                        not null comment '保司代码',
    name        varchar(64)                        null comment '保司名称',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64)                        null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64)                        null comment '更新人',
    extra_info  varchar(128)                       null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除'
) comment '保险公司表' charset = utf8;

create table product_category
(
    id            bigint auto_increment primary key,
    parent_id     bigint                             null comment '父级ID',
    category_code varchar(64)                        not null comment '分类代码',
    category_name varchar(64)                        null comment '分类名称',
    sort          int      default 0                 not null comment '排序',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator       varchar(64)                        null comment '创建人',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater       varchar(64)                        null comment '更新人',
    extra_info    varchar(128)                       null comment '扩展信息',
    is_deleted    smallint default 0                 not null comment '是否删除'
) comment '产品大类表' charset = utf8;

create table product
(
    id               bigint auto_increment primary key,
    partner_id       bigint                             not null comment '保司ID',
    product_code     varchar(64)                        not null comment '产品代码',
    product_name     varchar(64)                        null comment '产品名称',
    product_sub_name varchar(64)                        null comment '产品子名称',
    product_desc     varchar(256)                       null comment '产品描述',
    status           int      default 0                 not null comment '产品状态 0-暂存/1-有效/2-下架',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator          varchar(64)                        null comment '创建人',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater          varchar(64)                        null comment '更新人',
    extra_info       varchar(128)                       null comment '扩展信息',
    is_deleted       smallint default 0                 not null comment '是否删除'
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
    id               bigint auto_increment primary key,
    product_id       bigint                             not null comment '产品ID',
    plan_code        varchar(64)                        not null comment '计划代码',
    plan_name        varchar(64)                        null comment '计划名称',
    primary_coverage varchar(64)                        null comment '主险保额',
    status           int      default 0                 not null comment '计划状态 0-暂存/1-有效/2-下架',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator          varchar(64)                        null comment '创建人',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater          varchar(64)                        null comment '更新人',
    extra_info       varchar(128)                       null comment '扩展信息',
    is_deleted       smallint default 0                 not null comment '是否删除'
) comment '产品计划表' charset = utf8;

create table liability_category
(
    id            bigint auto_increment primary key,
    parent_id     bigint                             not null comment '父类ID',
    category_code varchar(64)                        not null comment '分类代码',
    category_name varchar(64)                        null comment '分类名称',
    sort          int      default 0                 not null comment '排序',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator       varchar(64)                        null comment '创建人',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater       varchar(64)                        null comment '更新人',
    extra_info    varchar(128)                       null comment '扩展信息',
    is_deleted    smallint default 0                 not null comment '是否删除'
) comment '责任大类表' charset = utf8;

create table liability
(
    id                    bigint auto_increment primary key,
    liability_category_id bigint                             not null comment '分类ID',
    liability_code        varchar(64)                        not null comment '责任代码',
    liability_name        varchar(64)                        null comment '责任名称',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator               varchar(64)                        null comment '创建人',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater               varchar(64)                        null comment '更新人',
    extra_info            varchar(128)                       null comment '扩展信息',
    is_deleted            smallint default 0                 not null comment '是否删除'
) comment '责任表' charset = utf8;

create table product_plan_liability
(
    id                 bigint auto_increment primary key,
    product_plan_id    bigint                             not null comment '产品责任ID',
    liability_id       bigint                             not null comment '责任ID',
    liability_code     varchar(64)                        not null comment '责任代码',
    liability_name     varchar(64)                        not null comment '责任名称',
    liability_category varchar(64)                        not null comment '责任大类(文本)',
    liability_remark   varchar(256)                       not null comment '责任备注',
    sort               int      default 0                 not null comment '排序',
    is_optional        int      default 1                 not null comment '是否唯一 0-不唯一/1-唯一',
    amount             varchar(64)                        not null comment '保障金额',
    amount_remark      varchar(64)                        null comment '保障金额备注',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '产品计划责任表' charset = utf8;

create table product_agreement
(
    id                 bigint auto_increment primary key,
    product_id         bigint                             not null comment '产品ID',
    notice             text                               null comment '投保须知',
    announcement       text                               null comment '投被保人声明与授权',
    special_agreement  text                               null comment '特别约定',
    dispute_settlement text                               null comment '争议处理',
    jurisdiction       text                               null comment '司法管辖',
    health_declaration text                               null comment '健康告知',
    insurance_case     text                               null comment '保险案例',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator            varchar(64)                        null comment '创建人',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater            varchar(64)                        null comment '更新人',
    extra_info         varchar(128)                       null comment '扩展信息',
    is_deleted         smallint default 0                 not null comment '是否删除'
) comment '产品特约须知表' charset = utf8;

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

create table rate_table
(
    id          bigint auto_increment primary key,
    code        varchar(64)                        not null comment '费率表代码',
    name        varchar(64)                        null comment '费率表名称',
    formula     varchar(1024)                      not null comment '费率计算公式',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator     varchar(64)                        null comment '创建人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater     varchar(64)                        null comment '更新人',
    extra_info  varchar(128)                       null comment '扩展信息',
    is_deleted  smallint default 0                 not null comment '是否删除'
) comment '费率表' charset = utf8;

create table rate_table_factors
(
    id              bigint auto_increment primary key,
    rate_table_id   bigint                             not null comment '费率表ID',
    rate_table_code varchar(64)                        not null comment '费率表代码',
    factor_code     varchar(64)                        not null comment '费率因子',
    is_optional     int                                not null comment '是否必须 0-非必须/1-必须',
    default_value   varchar(64)                        null comment '默认值',
    test_value      varchar(64)                        not null comment '测试用值',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator         varchar(64)                        null comment '创建人',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater         varchar(64)                        null comment '更新人',
    extra_info      varchar(128)                       null comment '扩展信息',
    is_deleted      smallint default 0                 not null comment '是否删除'
) comment '费率因子表' charset = utf8;

create table product_plan_rate_table
(
    id              bigint auto_increment primary key,
    product_plan_id bigint                             not null comment '产品ID',
    rate_table_id   bigint                             not null comment '费率表ID',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator         varchar(64)                        null comment '创建人',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater         varchar(64)                        null comment '更新人',
    extra_info      varchar(128)                       null comment '扩展信息',
    is_deleted      smallint default 0                 not null comment '是否删除'
) comment '产品计划费率表' charset = utf8;
