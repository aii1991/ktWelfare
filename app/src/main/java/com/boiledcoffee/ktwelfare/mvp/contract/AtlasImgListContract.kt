package com.boiledcoffee.ktwelfare.mvp.contract

import com.boiledcoffee.ktwelfare.bean.Image

/**
 * @author zjh
 * 2019/10/22
 */
interface AtlasImgListContract {
    interface View: IBaseView{
        fun fillData(imageList:List<Image>)
        fun updateImgItem(image: Image,index: Int)
    }

    interface Presenter:IPresenter<View>
}