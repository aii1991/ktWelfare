package com.boiledcoffee.ktwelfare.rx.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author zjh
 * 2019/10/21
 */
class DocumentConverter: Converter.Factory(){
    companion object {
        fun create(): DocumentConverter{
            return DocumentConverter()
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return DocumentBodyConverter()
    }
}