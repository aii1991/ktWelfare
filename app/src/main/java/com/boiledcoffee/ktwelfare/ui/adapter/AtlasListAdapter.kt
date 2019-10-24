package com.boiledcoffee.ktwelfare.ui.adapter

import android.widget.ImageView
import com.boildcoffee.base.BFConfig
import com.boildcoffee.base.imageloader.GlideApp
import com.boildcoffee.base.imageloader.ImageLoaderManager
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Atlas
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.util.HashMap

/**
 * @author zjh
 * 2019/10/21
 */
class AtlasListAdapter(): BaseQuickAdapter<Atlas,MyViewHolder>(R.layout.item_list_atlas) {
    override fun convert(helper: MyViewHolder?, item: Atlas?) {
        helper?.setText(R.id.tv,item?.title)
        val iv = helper?.getView<ImageView>(R.id.iv)
        ImageLoaderManager.getInstance().loadImg(GlideApp.with(iv?.context),item?.cover,helper?.getHeader(),null,null).into(iv)
    }


}