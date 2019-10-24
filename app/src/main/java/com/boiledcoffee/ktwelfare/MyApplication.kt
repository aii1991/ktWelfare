package com.boiledcoffee.ktwelfare

import com.boildcoffee.base.BFConfig
import com.boildcoffee.base.BaseApplication
import com.boildcoffee.base.BaseConfig
import com.boiledcoffee.ktwelfare.rx.converter.DocumentConverter

/**
 * @author zjh
 * 2019/10/21
 */
const val BASE_URL = "https://www.mzitu.com/"

class MyApplication: BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        BFConfig.INSTANCE.init(BaseConfig.Builder()
                .setBaseUrl(BASE_URL)
                .setPageSize(24)
                .setApiQueryCacheMode(BaseConfig.CacheMode.NO_CACHE)
                .setConverter(DocumentConverter.create())
                .setDebug(true)
                .build())
    }
}