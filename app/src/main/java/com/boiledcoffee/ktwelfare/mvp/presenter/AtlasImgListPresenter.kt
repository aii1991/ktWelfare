package com.boiledcoffee.ktwelfare.mvp.presenter

import android.text.TextUtils
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.cache.ImgCache
import com.boiledcoffee.ktwelfare.mvp.contract.AtlasImgListContract
import com.boiledcoffee.ktwelfare.mvp.model.AtlasModel
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @author zjh
 * 2019/10/22
 */
class AtlasImgListPresenter(lifecycleProvider: LifecycleProvider<*>,detailUrl: String): BasePresenter<AtlasImgListContract.View>(lifecycleProvider),AtlasImgListContract.Presenter{
    private val mDetailUrl = detailUrl
    private val mAtlasMode by lazy { AtlasModel(lifecycleProvider) }

    override fun loadData() {
        mView?.showLoading()
        mAtlasMode.getAtlasImgPageList(mDetailUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView?.dismissLoading()
                    mView?.fillData(getDefaultImgList(it))
                    getImgList(it)
                },{
                    handleErr(it)
                })
    }

    private fun getDefaultImgList(pageUrlList: List<String>):List<Image>{
        val imageList = ArrayList<Image>()
        for (pageUrl in pageUrlList){
            imageList.add(Image("","",pageUrl))
        }
        return imageList
    }

    private fun getImgList(pageUrlList: List<String>){
        var index = 0
        Observable.fromIterable(pageUrlList)
                .concatMap {
                    val cacheImg = ImgCache.instance.get(it.hashCode())
                    if (cacheImg != null){
                        return@concatMap Observable.just(cacheImg)
                    }
                    Observable.concat(Observable.timer(500,TimeUnit.MILLISECONDS),mAtlasMode.getAtlasImg(it)) //妹子图限制,如果同时并发访问妹子图后台会返回403
                }
                .filter {
                    it is Image
                }
                .cast(Image::class.java)
                .filter {
                    if(TextUtils.isEmpty(it.url)){
                        index ++
                        return@filter false
                    }
                    return@filter true
                } .subscribe({
                    ImgCache.instance.put(pageUrlList[index].hashCode(),it)
                    mView?.updateImgItem(it,index)
                    index ++
                },{
                    showShortToast(it.message)
                })
    }
}