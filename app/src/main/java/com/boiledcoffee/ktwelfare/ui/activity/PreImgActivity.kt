package com.boiledcoffee.ktwelfare.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.view.View
import com.boildcoffee.base.BaseActivity
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.ui.adapter.FragmentPageAdapter
import kotlinx.android.synthetic.main.activity_pre_img.*

class PreImgActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    companion object {
        const val PARAM_IMG_LIST = "img_list"
        const val PARAM_INDEX = "img_index"
        fun startActivity(context: Context?, imgList:ArrayList<Image>?, cIndex:Int,vararg sharedElements: Pair<View, String>){
            val intent = Intent(context,PreImgActivity::class.java)
            intent.putExtra(PARAM_IMG_LIST,imgList)
            intent.putExtra(PARAM_INDEX,cIndex)
            context?.startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation(context as AppCompatActivity,*sharedElements).toBundle())
        }
    }


    private var mPreImgTotalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_img)
        setupWindowAnimation()

        val preImgList = intent.getSerializableExtra(PARAM_IMG_LIST) as ArrayList<Image>
        val cIndex = intent.getIntExtra(PARAM_INDEX,0)
        mPreImgTotalCount = preImgList.size

        setupViewPage(preImgList,cIndex)
        setPageIndicator(cIndex)
        iv_back.setOnClickListener { finish() }
    }

    private fun setupViewPage(preImgList: ArrayList<Image>,cIndex: Int){
        view_page.adapter = FragmentPageAdapter.createPreImgPageAdapter(supportFragmentManager,preImgList)
        view_page.currentItem = cIndex
        view_page.offscreenPageLimit = 3
        view_page.addOnPageChangeListener(this)
    }

    private fun setupWindowAnimation(){
        val fade = Fade()
        fade.duration = 500
        window.enterTransition = fade
        window.exitTransition = fade
    }

    private fun setPageIndicator(index: Int){
        tv_pagination.text = String.format(resources.getString(R.string.text_page_indicator),index + 1,mPreImgTotalCount)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * 设置指示器页数 格式: 1/10
     */
    override fun onPageSelected(position: Int) {
        setPageIndicator(position)
    }

    /**
     * 退出
     */
    fun exit(){
        finishAfterTransition()
    }
}
