package com.boiledcoffee.ktwelfare.api

import io.reactivex.Observable
import org.jsoup.nodes.Document
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author zjh
 * 2019/10/21
 */
interface WelfareApi{
    @GET("/{url}")
    fun getData(@Path("url") value: String): Observable<Document>
}