package com.liyh.takeout.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.model.bean.AddressInfo
import kotlinx.android.synthetic.main.activity_confirm_order.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 05 日
 * @time  18 时 04 分
 * @descrip :
 */
class ConfirmOrderActivity: AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        when(v.id){
            R.id.ib_back -> finish()
            R.id.tvSubmit -> toast("提交订单结算")
            R.id.rl_location ->startActivityForResult<RecepitAddressActivity>(12)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        if (isNavigationBarShow()) {
            ll_confirm_container.setPadding(0, 0, 0, dip(46))
        }
        ib_back.setOnClickListener(this)
        tvSubmit.setOnClickListener(this)
        rl_location.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            data?.let {
                val addressInfo = data.getSerializableExtra("addressInfo") as AddressInfo
                tv_name.text = addressInfo.userName
                tv_sex.text = addressInfo.sex
                tv_phone.text = "${addressInfo.phone},${addressInfo.phoneOther}"
                tv_address.text = addressInfo.address
            }
        }
    }
}