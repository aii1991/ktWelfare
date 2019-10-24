package com.boiledcoffee.ktwelfare.cache

import com.boiledcoffee.ktwelfare.bean.Image
import com.google.gson.Gson

/**
 * @author zjh
 * 2019/10/24
 * 简单实现内存缓存(应加入对缓存管理实现LRU算法)
 */
class ImgCache private constructor() : Cache<Image>{

    private val mGson = Gson()
    private val mCacheMap = HashMap<String,String>()

    companion object {
        val instance:ImgCache by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            ImgCache()
        }
    }

    fun put(key: Int, value: Image) {
        mCacheMap[key.toString()] = mGson.toJson(value)
    }

    override fun put(key: String, value: Image) {
        mCacheMap[key] = mGson.toJson(value)
    }

    fun get(key: Int): Image? {
        return mCacheMap[key.toString()].let { mGson.fromJson(it,Image::class.java) }
    }

    override fun get(key: String): Image? {
        return mCacheMap[key].let { mGson.fromJson(it,Image::class.java) }
    }

    override fun clean() {
        mCacheMap.clear()
    }
}