-- 修改保单表, 增加几个时间记录
alter table policy
    modify status smallint default 0 not null comment '保单状态';
alter table policy
    modify pay_type smallint default 0 not null comment '支付方式';
alter table policy
    add pay_status smallint default 0 not null comment '支付状态' after e_policy_url;
alter table policy
    add order_time datetime null comment '出单时间' after goods_plan_id;
alter table policy
    add issue_time datetime null comment '承保时间' after order_time;
alter table policy
    add cancel_time datetime null comment '退保时间' after pay_type;
alter table policy
    add refund_time datetime null comment '退费时间' after cancel_time;

-- 修改第三方接口请求记录表, 增加请求耗时记录
alter table channel_http_request
    add cost_time bigint default 0 not null comment '请求耗时' after http_status;

-- 修改订单表, 去除不需要的字段, 调整增加所属用户id
alter table `order`
    drop column proposal_no;
alter table `order`
    change goods_plan_id user_id bigint null comment '所属用户id' after status;