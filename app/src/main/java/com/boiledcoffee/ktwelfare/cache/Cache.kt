package com.boiledcoffee.ktwelfare.cache

/**
 * @author zjh
 * 2019/10/24
 */
interface Cache<T> {
    fun put(key:String, value: T)
    fun get(key:String): T?
    fun clean()
}