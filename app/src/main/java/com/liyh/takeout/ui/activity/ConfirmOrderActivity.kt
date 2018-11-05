package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import kotlinx.android.synthetic.main.activity_confirm_order.*
import org.jetbrains.anko.dip

/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 05 日
 * @time  18 时 04 分
 * @descrip :
 */
class ConfirmOrderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        if (isNavigationBarShow()) {
            ll_confirm_container.setPadding(0, 0, 0, dip(46))
        }
    }
}