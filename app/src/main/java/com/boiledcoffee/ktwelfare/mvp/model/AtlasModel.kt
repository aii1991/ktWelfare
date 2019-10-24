package com.boiledcoffee.ktwelfare.mvp.model

import com.boiledcoffee.ktwelfare.bean.Atlas
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.service.WelfareDataService
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.RxFragment
import io.reactivex.Observable

/**
 * @author zjh
 * 2019/10/22
 */
class AtlasModel(lifecycleProvider: LifecycleProvider<*>) {
    private val mLifecycleProvider = lifecycleProvider

    fun getAtlasList(page:Int,typeUrl:String): Observable<List<Atlas>>{
        return WelfareDataService
                .getAtlasList(page,typeUrl)
                .compose(mLifecycleProvider.bindToLifecycle())
    }

    fun getAtlasImgPageList(detailUrl: String): Observable<List<String>>{
        return WelfareDataService
                .getAtlasImgPageList(detailUrl)
                .compose(mLifecycleProvider.bindToLifecycle())
    }

    fun getAtlasImg(pageUrl: String): Observable<Image>{
        return WelfareDataService
                .getAtlasImg(pageUrl)
                .compose(mLifecycleProvider.bindToLifecycle())
    }
}