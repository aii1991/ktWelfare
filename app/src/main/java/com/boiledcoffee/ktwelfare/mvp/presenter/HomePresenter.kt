package com.boiledcoffee.ktwelfare.mvp.presenter

import com.boiledcoffee.ktwelfare.mvp.contract.HomeContract
import com.boiledcoffee.ktwelfare.mvp.model.TypeModel
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * @author zjh
 * 2019/10/21
 */
class HomePresenter(lifecycleProvider: LifecycleProvider<*>): BasePresenter<HomeContract.View>(lifecycleProvider),HomeContract.Presenter{
    private val mTypeModel: TypeModel by lazy { TypeModel(lifecycleProvider) }

    override fun loadData() {
        mView?.let {
            it.showLoading()
            mTypeModel.getTypes().subscribe({
                res->
                    it.dismissLoading()
                    it.fillData(res)
            },{
                err->
                    it.dismissLoading()
                    handleErr(err)
            })
        }

    }

}