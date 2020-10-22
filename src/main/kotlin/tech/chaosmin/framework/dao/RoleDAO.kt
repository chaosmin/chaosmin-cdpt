package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tech.chaosmin.framework.dao.dataobject.Role

interface RoleDAO : BaseMapper<Role> {
    @Select(value = ["select * from role r left join user_role m on r.id = m.role_id where m.user_id = \${userId}"])
    fun findRoles(@Param("userId") userId: Long): Set<Role>

    @Insert(
        value = ["<script> insert into user_role(user_id,role_id) VALUES "
                + "<foreach collection='roleIds' item='id' separator=','>"
                + "(#{userId},#{id}) </foreach></script>"]
    )
    fun addRoles(@Param("userId") userId: Long, @Param("roleIds") roleIds: List<Long>)

    @Delete(value = ["<script> delete from user_role where user_id = \${userId} and role_id in <foreach collection='roleIds' item='id' separator=',' open='(' close=')'>#{id}</foreach></script>"])
    fun removeRoles(@Param("userId") userId: Long, @Param("roleIds") roleIds: List<Long>)

    @Delete(value = ["delete from user_role where user_id = \${userId}"])
    fun clearRoles(@Param("userId") userId: Long)
}