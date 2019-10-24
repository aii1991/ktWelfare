package com.boiledcoffee.ktwelfare.mvp.model

import com.boiledcoffee.ktwelfare.bean.Type
import com.boiledcoffee.ktwelfare.service.WelfareDataService
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable

/**
 * @author zjh
 * 2019/10/21
 */
class TypeModel(lifecycleProvider: LifecycleProvider<*>){
    private val mLifecycleProvider = lifecycleProvider

    fun getTypes(): Observable<List<Type>> {
        return WelfareDataService.getTypes().compose(mLifecycleProvider.bindToLifecycle())
    }
}