alter table user
    add pay_type int null comment '支付方式' after address;
alter table letter_head
    change department_id user_id bigint not null comment '账户ID';

drop index department_code_uindex on letter_head;