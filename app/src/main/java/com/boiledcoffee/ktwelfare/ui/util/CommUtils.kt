package com.boiledcoffee.ktwelfare.ui.util

import com.boildcoffee.base.BFConfig
import java.util.HashMap

/**
 * @author zjh
 * 2019/10/23
 */
object CommUtils{
    fun getHeader(url: String?=""): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["referer"] = BFConfig.INSTANCE.config.baseUrl + url
        headers["user-Agent"] = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36"
        return headers
    }
}