-- 修改用户表结构, 增加支付方式/投保单位信息
alter table department
    add pay_type int default 0 not null comment '支付方式 0-月结 1-微信' after name;

-- 修改保单表结构, 增加支付方式
alter table policy
    add pay_type int default 0 not null comment '支付方式 0-月结 1-微信' after e_policy_url;


