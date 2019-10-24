package com.boiledcoffee.ktwelfare.mvp.contract

/**
 * @author zjh
 * 2019/10/21
 */
interface IPresenter<in V: IBaseView>  {
    fun attachView(view: V)

    fun detachView()

    fun loadData()
}