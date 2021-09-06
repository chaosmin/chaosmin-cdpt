-- 删除张殷杰的授权产品
delete
from goods_plan
where user_id = (select id from user where login_name = '张殷杰123');
delete
from goods_plan
where user_id = (select id from user where login_name = '张殷杰 123');

-- 删除张殷杰的角色配置
delete
from user_role
where user_id = (select id from user where login_name = '张殷杰123');
delete
from user_role
where user_id = (select id from user where login_name = '张殷杰 123');

-- 删除张殷杰的账号
delete
from user
where login_name = '张殷杰123';
delete
from user
where login_name = '张殷杰 123';