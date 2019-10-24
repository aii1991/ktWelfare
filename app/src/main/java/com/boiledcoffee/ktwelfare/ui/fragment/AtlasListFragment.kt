package com.boiledcoffee.ktwelfare.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Atlas
import com.boiledcoffee.ktwelfare.mvp.contract.PagingContract
import com.boiledcoffee.ktwelfare.mvp.presenter.AtlasPresenter
import com.boiledcoffee.ktwelfare.ui.activity.AtlasImgListActivity
import com.boiledcoffee.ktwelfare.ui.adapter.AtlasListAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_img_list.*

/**
 * @author zjh
 * 2019/10/18
 */
class AtlasListFragment : RxFragment(),PagingContract.View<Atlas>{

    private var typeUrl:String? = ""
    private lateinit var mAtlasPresenter: AtlasPresenter
    private lateinit var mAdapter: AtlasListAdapter

    companion object {
        const val key = "typeUrl"
        fun newInstance(url: String): Fragment {
            val fragment = AtlasListFragment()
            val bundle = Bundle()
            bundle.putString(key,url)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        typeUrl = arguments?.getString(AtlasListFragment.key)
        mAtlasPresenter = AtlasPresenter(this,typeUrl!!)
        mAtlasPresenter.attachView(this)
    }

    override fun onDetach() {
        super.onDetach()
        mAtlasPresenter.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_img_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.layoutManager = GridLayoutManager(view.context,2)
        mAdapter = AtlasListAdapter()
        recycler_view.adapter = mAdapter
        recycler_view.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val atlas = mAdapter.getItem(position)
                AtlasImgListActivity.startActivity(view?.context!!, atlas?.detailUrl!!)
            }
        })
        mAtlasPresenter.openLoadMore(mAdapter as BaseQuickAdapter<*, *>,recycler_view)
        mAtlasPresenter.openRefreshData(swipe_refresh_layout)
        mAtlasPresenter.loadData()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun onErr(e: Throwable) {
        multipleStatusView.showError()
    }

    override fun fillData(data: List<Atlas>) {
        if (data.isEmpty()){
            multipleStatusView.showEmpty()
        }
        mAdapter.setNewData(data)
    }

    override fun onRefreshFinish(data: List<Atlas>) {
        mAdapter.setNewData(data)
        swipe_refresh_layout.isRefreshing = false
    }

    override fun onLoadMoreFinish(data: List<Atlas>) {
        mAdapter.addData(data)
        mAdapter.loadMoreComplete()
    }

    override fun onLoadMoreStatus(status: Int) {
        when(status){
            PagingContract.STATUS_DISABLE_LOAD_MORE -> {
                mAdapter.setEnableLoadMore(false)
                mAdapter.loadMoreEnd()
            }
            PagingContract.STATUS_ENABLE_LOAD_MORE -> mAdapter.setEnableLoadMore(true)
        }
    }

}