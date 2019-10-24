package com.boiledcoffee.ktwelfare.ui.adapter

import android.text.TextUtils
import com.boildcoffee.base.imageloader.GlideApp
import com.boildcoffee.base.imageloader.ImageLoaderManager
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.bean.Image
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * @author zjh
 * 2019/10/22
 */
class AtlasImgListAdapter : BaseQuickAdapter<Image,MyViewHolder>(R.layout.item_list_atlas_img){

    override fun convert(helper: MyViewHolder?, item: Image?) {
        if (!TextUtils.isEmpty(item?.url)){
            ImageLoaderManager.getInstance().loadImg(GlideApp.with(mContext),item?.url,helper?.getHeader(),null,null).into(helper?.getView(R.id.iv))
        }else{
            helper?.setImageResource(R.id.iv,R.mipmap.load_image_200)
        }
    }
}