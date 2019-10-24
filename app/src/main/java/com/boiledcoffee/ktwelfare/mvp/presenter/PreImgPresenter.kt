package com.boiledcoffee.ktwelfare.mvp.presenter

import android.text.TextUtils
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.mvp.contract.PreImgContract
import com.boiledcoffee.ktwelfare.mvp.model.AtlasModel
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * @author zjh
 * 2019/10/24
 */
class PreImgPresenter(lifecycleProvider: LifecycleProvider<*>): BasePresenter<PreImgContract.View>(lifecycleProvider),PreImgContract.Presenter{
    private val mAtlasModel: AtlasModel by lazy { AtlasModel(lifecycleProvider) }

    override fun loadData(image: Image) {
        mView?.showLoading()
        if (!TextUtils.isEmpty(image.url)){
            handleData(image)
        }else if (!TextUtils.isEmpty(image.pageUrl)){
            mAtlasModel.getAtlasImg(image.pageUrl!!)
                    .subscribe({
                        handleData(it)
                    },{
                        handleErr(it)
                    })
        }else{
            mView?.onErr(Exception("data error"))
        }
    }

    private fun handleData(image: Image){
        mView?.dismissLoading()
        mView?.fillData(image.url!!)
    }

    override fun loadData() {}
}