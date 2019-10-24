package com.boiledcoffee.ktwelfare.ui.fragment

import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.mvp.contract.AtlasImgListContract
import com.boiledcoffee.ktwelfare.mvp.presenter.AtlasImgListPresenter
import com.boiledcoffee.ktwelfare.ui.activity.PreImgActivity
import com.boiledcoffee.ktwelfare.ui.adapter.AtlasImgListAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_img_list.*

/**
 * @author zjh
 * 2019/10/18
 */
class AtlasImgListFragment: RxFragment(),AtlasImgListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private var detailUrl:String? = ""
    private lateinit var mPresenter: AtlasImgListPresenter
    private lateinit var mAdapter: AtlasImgListAdapter

    companion object {
        private const val key = "detailUrl"
        fun newInstance(url: String): Fragment{
            val fragment = AtlasImgListFragment()
            val bundle = Bundle()
            bundle.putString(key,url)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUrl = arguments?.getString(AtlasImgListFragment.key)
        mPresenter = AtlasImgListPresenter(this,detailUrl!!)
        mPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_img_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.layoutManager = GridLayoutManager(view.context,2)
        mAdapter = AtlasImgListAdapter()
        recycler_view.adapter = mAdapter
        recycler_view.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                PreImgActivity.startActivity(context,ArrayList(mAdapter.data),position)
            }
        })

        swipe_refresh_layout.setColorSchemeColors(17170450, 17170454, 17170456, 17170452)
        swipe_refresh_layout.setOnRefreshListener(this)

        mPresenter.loadData()
    }

    override fun updateImgItem(image: Image, index: Int) {
        mAdapter.setData(index,image)
    }

    override fun fillData(imageList: List<Image>) {
        if (imageList.isEmpty()){
            multipleStatusView.showEmpty()
        }
        mAdapter.setNewData(imageList)
    }

    override fun onRefresh() {
        mPresenter.loadData()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
        if (swipe_refresh_layout.isRefreshing)
            swipe_refresh_layout.isRefreshing = false
    }

    override fun onErr(e: Throwable) {
        multipleStatusView.showError()
    }
}