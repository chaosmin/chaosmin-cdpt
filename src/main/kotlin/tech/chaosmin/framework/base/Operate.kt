package tech.chaosmin.framework.base

interface Operate<P, R> {
    fun operate(arg: P): RestResult<R>
}