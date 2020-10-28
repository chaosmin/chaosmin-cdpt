package tech.chaosmin.framework.handler.base

import tech.chaosmin.framework.domain.RestResult

interface Operate<P, R> {
    fun operate(arg: P): RestResult<R>
}