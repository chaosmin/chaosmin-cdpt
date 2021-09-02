create table if not exists chaosmin_cdpt.`payment_transaction`
(
    id             bigint auto_increment primary key,
    status         smallint                           not null comment '订单状态 0-初始化 1-支付中 2-支付成功 3-支付失败',
    channel        smallint                           not null comment '支付渠道',
    trade_type     smallint                           null comment '支付类型',
    transaction_id varchar(64)                        null comment '交易流水号',
    out_trade_no   varchar(64)                        not null comment '外部订单号',
    description    varchar(64)                        null comment '描述',
    amount         bigint                             not null comment '支付金额(分)',
    refund_amount  bigint                             null comment '退款金额',
    payer          varchar(64)                        null comment '支付人',
    refund_account varchar(256)                       null comment '退款入账账户',
    pay_url        varchar(128)                       null comment '支付链接',
    expire_time    datetime                           null comment '过期时间',
    order_time     datetime                           not null comment '订单创建时间',
    pay_time       datetime                           null comment '支付时间',
    refund_time    datetime                           null comment '退款时间',
    close_time     datetime                           null comment '订单关闭时间',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator        varchar(64)                        null comment '创建人',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    updater        varchar(64)                        null comment '更新人',
    extra_info     varchar(128)                       null comment '扩展信息',
    is_deleted     smallint default 0                 not null comment '是否删除'
)
    comment '支付订单表';

create index payment_order_out_trade_no_index on payment_transaction (out_trade_no);
create index payment_order_transaction_id_index on payment_transaction (transaction_id);


