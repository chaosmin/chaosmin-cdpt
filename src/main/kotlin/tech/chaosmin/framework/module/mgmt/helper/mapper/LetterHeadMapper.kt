package tech.chaosmin.framework.module.mgmt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead
import tech.chaosmin.framework.module.mgmt.entity.LetterHeadEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface LetterHeadMapper {
    companion object {
        val INSTANCE: LetterHeadMapper = Mappers.getMapper(LetterHeadMapper::class.java)
    }

    fun convert2DO(source: LetterHeadEntity?): LetterHead?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: LetterHead?): LetterHeadEntity?

    fun convert2Entity(source: List<LetterHead>?): List<LetterHeadEntity>?
}