package com.boiledcoffee.ktwelfare.ui.adapter

import android.view.View
import com.boildcoffee.base.BFConfig
import com.boiledcoffee.ktwelfare.ui.util.CommUtils
import com.chad.library.adapter.base.BaseViewHolder
import java.util.HashMap


/**
 * @author zjh
 * 2019/10/22
 */
class MyViewHolder(view:View) : BaseViewHolder(view){
    fun getHeader(url: String?=""): HashMap<String, String> {
        return CommUtils.getHeader(url)
    }
}