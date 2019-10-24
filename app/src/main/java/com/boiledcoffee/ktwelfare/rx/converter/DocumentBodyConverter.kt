package com.boiledcoffee.ktwelfare.rx.converter

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Converter

/**
 * @author zjh
 * 2019/10/21
 */
class DocumentBodyConverter: Converter<ResponseBody, Document>{
    override fun convert(value: ResponseBody?): Document {
        return Jsoup.parse(value?.string())
    }
}