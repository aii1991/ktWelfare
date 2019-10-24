package com.boiledcoffee.ktwelfare.ui.activity

import android.os.Bundle
import com.boildcoffee.base.BaseActivity
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Type
import com.boiledcoffee.ktwelfare.mvp.contract.HomeContract
import com.boiledcoffee.ktwelfare.mvp.presenter.HomePresenter
import com.boiledcoffee.ktwelfare.ui.adapter.FragmentPageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),HomeContract.View {
    private val mPresenter:HomePresenter by lazy { HomePresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.loadData()
    }

    init {
        mPresenter.attachView(this)
    }

    override fun fillData(res: List<Type>) {
        res.map {
            val tab = tab_layout.newTab()
            tab.text = it.name
            tab_layout.addTab(tab)
        }
        tab_layout.setupWithViewPager(view_page)
        view_page.adapter = FragmentPageAdapter.createPageAdapter(supportFragmentManager,res)
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
