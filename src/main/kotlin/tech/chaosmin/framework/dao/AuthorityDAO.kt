package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tech.chaosmin.framework.dao.dataobject.Authority

interface AuthorityDAO : BaseMapper<Authority> {
    @Select(
        value = ["<script> select * from authority u left join role_authority m on u.id = m.authority_id where m.role_id in "
                + "<foreach collection='roleIds' item='id' separator=',' open=\"(\" close=\")\">#{id}</foreach></script>"]
    )
    fun findAuthorities(@Param("roleIds") roleIds: Set<Long>): Set<Authority>

    @Insert(
        value = ["<script> insert into role_authority(role_id,authority_id) VALUES "
                + "<foreach collection='authorityIds' item='id' separator=','>"
                + "(#{roleId},#{id}) </foreach></script>"]
    )
    fun addAuthorities(@Param("roleId") roleId: Long, @Param("authorityIds") authorityIds: List<Long>)
}