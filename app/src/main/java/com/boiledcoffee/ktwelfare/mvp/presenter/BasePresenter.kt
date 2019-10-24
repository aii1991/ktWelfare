package com.boiledcoffee.ktwelfare.mvp.presenter

import android.content.Context
import android.widget.Toast
import com.boildcoffee.base.BaseApplication
import com.boiledcoffee.ktwelfare.mvp.contract.IBaseView
import com.boiledcoffee.ktwelfare.mvp.contract.IPresenter
import com.classic.common.MultipleStatusView
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * @author zjh
 * 2019/10/21
 */
abstract class BasePresenter<T: IBaseView>(lifecycleProvider: LifecycleProvider<*>): IPresenter<T> {
    val mLifecycleProvider = lifecycleProvider

    var mView: T? = null
        private set

    private var multipleStatusView: MultipleStatusView? = null

    override fun attachView(view: T) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    fun showShortToast(text: String?, context:Context = BaseApplication.mInstance){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(text: String?, context:Context = BaseApplication.mInstance){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun enableMultipleStatusView(multipleStatusView: MultipleStatusView){
        this.multipleStatusView = multipleStatusView
        this.multipleStatusView?.setOnRetryClickListener({
            loadData()
        })
    }

    fun handleErr(e:Throwable,notifyView:Boolean = true){
        if (notifyView){
            mView?.onErr(e)
        }else{
            showShortToast(e.message)
        }
    }
}