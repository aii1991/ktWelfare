package com.boiledcoffee.ktwelfare.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.boildcoffee.base.BaseActivity
import com.boiledcoffee.ktwelfare.R
import com.boiledcoffee.ktwelfare.ui.fragment.AtlasImgListFragment
import kotlinx.android.synthetic.main.activity_atlas_img_list.*

class AtlasImgListActivity : BaseActivity() {
    companion object {
        const val PARAM_KEY = "p"
        fun startActivity(context: Context,url: String){
            val intent = Intent(context,AtlasImgListActivity::class.java)
            intent.putExtra(PARAM_KEY,url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_img_list)

        initActionBar()

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_container,AtlasImgListFragment.newInstance(intent.getStringExtra(PARAM_KEY)))
        ft.commit()
    }

    private fun initActionBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }
}
