package com.boiledcoffee.ktwelfare.mvp.presenter

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.boildcoffee.base.BFConfig
import com.boiledcoffee.ktwelfare.mvp.contract.PagingContract
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable

/**
 * @author zjh
 * 2019/10/22
 */
abstract class PagingPresenter<T>(lifecycleProvider: LifecycleProvider<*>): BasePresenter<PagingContract.View<T>>(lifecycleProvider),PagingContract.Presenter {
    private var currentPage:Int = 1
    private val pageSize: Int = BFConfig.INSTANCE.config.pageSize
    private var canLoadMore: Boolean = true

    /**
     * 开启上拉加载更多
     */
    fun openLoadMore(adapter: BaseQuickAdapter<*, *>, rv:RecyclerView){
        adapter.setOnLoadMoreListener(this,rv)
    }

    /**
     * 开启下拉刷新
     */
    fun openRefreshData(swipeRefreshLayout: SwipeRefreshLayout){
        swipeRefreshLayout.setColorSchemeColors(17170450, 17170454, 17170456, 17170452)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun loadData() {
        currentPage = 1
        mView?.showLoading()
        getData(currentPage).subscribe({
            mView?.dismissLoading()
            mView?.fillData(it)
        },{
            mView?.dismissLoading()
            handleErr(it)
        })
    }

    override fun onLoadMoreRequested(){
        if (!canLoadMore){
            return
        }
        currentPage ++
        getData(currentPage).subscribe({
            if (it.size < pageSize){
                canLoadMore = false
                mView?.onLoadMoreStatus(PagingContract.STATUS_DISABLE_LOAD_MORE)
            }
            mView?.onLoadMoreFinish(it)
        },{
            handleErr(it,false)
        })
    }

    override fun onRefresh() {
        currentPage = 1
        canLoadMore = true
        mView?.onLoadMoreStatus(PagingContract.STATUS_ENABLE_LOAD_MORE)
        getData(currentPage).subscribe({
            mView?.onRefreshFinish(it)
        },{
            handleErr(it,false)
        })
    }

    abstract fun getData(page:Int,param:Any?=null):Observable<List<T>>

}
