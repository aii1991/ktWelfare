package com.boiledcoffee.ktwelfare.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.bean.Type
import com.boiledcoffee.ktwelfare.ui.fragment.AtlasListFragment
import com.boiledcoffee.ktwelfare.ui.fragment.PreImgFragment
import java.util.ArrayList

/**
 * @author zjh
 * 2019/10/18
 */
class FragmentPageAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    lateinit var mDatas: List<Type>
    lateinit var  mPreImgDatas: List<Image>

    private var isPreImg: Boolean = false
    private val fragments = ArrayList<Fragment>()


    companion object {
        fun createPreImgPageAdapter(fm:FragmentManager, preImgDatas: List<Image>): FragmentPageAdapter{
            val imagePagerAdapter = FragmentPageAdapter(fm)
            imagePagerAdapter.mPreImgDatas = preImgDatas
            imagePagerAdapter.isPreImg = true
            imagePagerAdapter.init()
            return imagePagerAdapter
        }

        fun createPageAdapter(fm:FragmentManager,datas: List<Type>): FragmentPageAdapter{
            val imagePagerAdapter = FragmentPageAdapter(fm)
            imagePagerAdapter.mDatas = datas
            imagePagerAdapter.isPreImg = false
            imagePagerAdapter.init()
            return imagePagerAdapter
        }
    }

    private fun init() {
        if (isPreImg) {
            mPreImgDatas.mapTo(fragments) { PreImgFragment.newInstance(it) }
        } else {
            mDatas.mapTo(fragments) { AtlasListFragment.newInstance(it.url) }
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (!isPreImg) {
            mDatas[position].name
        } else {
            position.toString()
        }
    }

}