package com.boiledcoffee.ktwelfare.mvp.contract

import com.boiledcoffee.ktwelfare.bean.Type

/**
 * @author zjh
 * 2019/10/21
 */
interface HomeContract {
    interface View: IBaseView{
        fun fillData(res: List<Type>)
    }

    interface Presenter:IPresenter<View>
}