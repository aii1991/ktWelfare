package com.boiledcoffee.ktwelfare.service

import com.boildcoffee.base.network.RetrofitManager
import com.boildcoffee.base.network.rx.TransformerHelper
import com.boiledcoffee.ktwelfare.BASE_URL
import com.boiledcoffee.ktwelfare.api.WelfareApi
import com.boiledcoffee.ktwelfare.bean.Atlas
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.bean.Type
import io.reactivex.Observable
import org.jsoup.helper.StringUtil
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * @author zjh
 * 2019/10/21
 */
object WelfareDataService{
    /**
     * 获取分类 性感 热门 等
     */
    fun getTypes(): Observable<List<Type>>{
        return createReq(WelfareApi::class.java)
                .getData("")
                .map{
                    val typeList = ArrayList<Type>()
                    val subElements = it.select(".main > .main-content > .subnav > a")
                    if (subElements != null && !subElements.isEmpty()){
                        addTypeToList(subElements,typeList)
                    }
                    val elements = it.select(".mainnav > ul > li > a")
                    addTypeToList(elements,typeList)
                    typeList
                }
                .compose(TransformerHelper.observableToMainThreadTransformer<List<Type>>())
    }

    /**
     * 获取相册
     */
    fun getAtlasList(page:Int,typeUrl:String):Observable<List<Atlas>>{
        val reqUrl = if (page <= 1) typeUrl else typeUrl + "page/" + page
        return createReq(WelfareApi::class.java)
                .getData(reqUrl)
                .map {
                    val atlasList = ArrayList<Atlas>()
                    if (!havePage(it,page)){
                        return@map atlasList
                    }
                    val elements = it.select(".postlist > ul > li")
                    for (element in elements) {
                        if (element.children().size < 1) {
                            continue
                        }
                        val aElement = element.child(0)
                        val spanElement = element.child(1)

                        val imgElement = aElement.child(0)
                        val atlas = Atlas(imgElement.attr("alt")
                                ,imgElement.attr("data-original")
                        ,parseUrl(spanElement.child(0).attr("href")) + "/",null)
                        atlasList.add(atlas)
                    }
                    atlasList
                }
                .compose(TransformerHelper.observableToMainThreadTransformer<List<Atlas>>())
    }

    /**
     * 获取图片所在页面地址
     */
    fun getAtlasImgPageList(detailUrl:String): Observable<List<String>>{
        return createReq(WelfareApi::class.java)
                .getData(detailUrl)
                .map {
                    val list = ArrayList<String>()
                    var maxPage = 0
                    val elements = it.select(".pagenavi > a > span")
                    for (element in elements) {
                        val strPage = element.text()
                        if (strPage.matches("^[0-9]*$".toRegex())) {
                            val page = Integer.valueOf(strPage)!!
                            if (page > maxPage) {
                                maxPage = page
                            }
                        }
                    }
                    for (i in 1..maxPage) {
                        list.add(detailUrl + i)
                    }
                    list
                }
                .compose(TransformerHelper.observableToMainThreadTransformer<List<String>>())
    }

    fun getAtlasImg(pageUrl: String): Observable<Image>{
        return createReq(WelfareApi::class.java)
                .getData(pageUrl)
                .map {
                    val image = Image("","")
                    val elements = it.select(".main-image > p > a > img")
                    if (elements.size > 0) {
                        val element = elements[0]
                        image.url = element.attr("src")
                        image.desc = element.attr("alt")
                    }
                    image
                }
                .compose(TransformerHelper.observableToMainThreadTransformer<Image>())
                .onErrorReturn {
                    Image("","")
                }
    }

    private fun <T> createReq(clazz: Class<T>): T{
        return RetrofitManager.getInstance().createReq(clazz)
    }

    private fun havePage(document:Document,currentPage:Int): Boolean{
        val elements = document.select("nav .nav-links .page-numbers")
        var maxPage = -1
        for (element in elements) {
            if (element.hasClass("dots")) {
                continue
            }
            val text = element.text()
            if (StringUtil.isNumeric(text)) {
                val page = Integer.parseInt(text)
                if (page > maxPage) {
                    maxPage = page
                }
            }
        }
        return currentPage <= maxPage
    }

    private fun parseUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    private fun addTypeToList(elements: Elements, typeList: ArrayList<Type>){
        for (element in elements){
            val text = element.text()
            val value = element.attr("href").replace(BASE_URL.toRegex(), "")
            if ("首页" == text || "zipai/" == value || "all/" == value || "best/" == value || "zhuanti/" == value) {
                continue
            }
            typeList.add(Type(text,value))
        }
    }
}