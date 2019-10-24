package com.boiledcoffee.ktwelfare.mvp.presenter

import com.boiledcoffee.ktwelfare.bean.Atlas
import com.boiledcoffee.ktwelfare.mvp.model.AtlasModel
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable

/**
 * @author zjh
 * 2019/10/22
 */
class AtlasPresenter(lifecycleProvider: LifecycleProvider<*>,typeUrl:String): PagingPresenter<Atlas>(lifecycleProvider){
    private val mTypeUrl:String = typeUrl
    private val mAtlasModel: AtlasModel by lazy { AtlasModel(lifecycleProvider) }

    override fun getData(page: Int, param: Any?): Observable<List<Atlas>> {
        return mAtlasModel.getAtlasList(page,mTypeUrl)
    }
}