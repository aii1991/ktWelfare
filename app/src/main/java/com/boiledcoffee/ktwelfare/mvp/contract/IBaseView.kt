package com.boiledcoffee.ktwelfare.mvp.contract

/**
 * @author zjh
 * 2019/10/21
 */
interface IBaseView {
    fun showLoading()
    fun dismissLoading()
    fun onErr(e:Throwable)
}