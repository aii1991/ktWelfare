package com.boiledcoffee.ktwelfare.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boildcoffee.base.imageloader.GlideApp
import com.boildcoffee.base.imageloader.ImageLoaderManager
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Image
import com.boiledcoffee.ktwelfare.mvp.contract.PreImgContract
import com.boiledcoffee.ktwelfare.mvp.presenter.PreImgPresenter
import com.boiledcoffee.ktwelfare.ui.activity.PreImgActivity
import com.boiledcoffee.ktwelfare.ui.util.CommUtils
import com.trello.rxlifecycle2.components.support.RxFragment
import com.wingsofts.dragphotoview.DragPhotoView
import kotlinx.android.synthetic.main.item_pre_img.*


/**
 * @author zjh
 * 2019/10/23
 */
class PreImgFragment: RxFragment(), DragPhotoView.OnExitListener, DragPhotoView.OnTapListener,PreImgContract.View {

    private lateinit var mPreImg: Image
    private lateinit var mPreImgPresenter: PreImgPresenter

    companion object {
        const val KEY_IMG:String = "img"
        fun newInstance(img:Image): Fragment{
            val fragment = PreImgFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_IMG,img)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPreImg = arguments?.getSerializable(PreImgFragment.KEY_IMG) as Image
        mPreImgPresenter = PreImgPresenter(this)
        mPreImgPresenter.attachView(this)
    }

    override fun onDetach() {
        super.onDetach()
        mPreImgPresenter.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_pre_img,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        drag_photo_view.setOnExitListener(this)
        drag_photo_view.setOnTapListener(this)
        mPreImgPresenter.loadData(mPreImg)
    }

    override fun onExit(view: DragPhotoView?, translateX: Float, translateY: Float, w: Float, h: Float) {
        exit()
    }

    override fun onTap(view: DragPhotoView?) {
        exit()
    }

    override fun fillData(imgUrl: String) {
        ImageLoaderManager.getInstance()
                .loadImg(GlideApp.with(this),imgUrl, CommUtils.getHeader(),null,null)
                .into(drag_photo_view)
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun onErr(e: Throwable) {
        progress_bar.visibility = View.GONE
        mPreImgPresenter.showShortToast(e.message)
    }

    private fun exit() {
        (activity as PreImgActivity).exit()
    }

}