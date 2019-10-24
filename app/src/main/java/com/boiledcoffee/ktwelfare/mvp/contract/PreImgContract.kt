package com.boiledcoffee.ktwelfare.mvp.contract

import com.boiledcoffee.ktwelfare.bean.Image

/**
 * @author zjh
 * 2019/10/24
 */
interface PreImgContract {
    interface View: IBaseView{
        fun fillData(imgUrl: String)
    }

    interface Presenter: IPresenter<View>{
        fun loadData(image: Image)
    }
}