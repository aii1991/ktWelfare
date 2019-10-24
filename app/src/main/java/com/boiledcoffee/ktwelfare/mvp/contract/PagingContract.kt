package com.boiledcoffee.ktwelfare.mvp.contract

import android.support.v4.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * @author zjh
 * 2019/10/22
 */
interface PagingContract{
    companion object {
        const val STATUS_ENABLE_LOAD_MORE:Int = 1
        const val STATUS_DISABLE_LOAD_MORE:Int = -1
    }
    interface View<in T>: IBaseView{
        fun fillData(data:List<T>)
        fun onRefreshFinish(data:List<T>)
        fun onLoadMoreFinish(data:List<T>)
        fun onLoadMoreStatus(status:Int)
    }

    interface Presenter: BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener
}