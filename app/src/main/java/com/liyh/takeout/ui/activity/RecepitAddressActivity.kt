package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.model.bean.AddressInfo
import com.liyh.takeout.model.dao.AddressDao
import com.liyh.takeout.ui.adapter.AddressListAdapter
import com.liyh.takeout.ui.adapter.RecycleViewDivider
import kotlinx.android.synthetic.main.activity_address_list.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 06 日
 * @time  14 时 42 分
 * @descrip :
 */
class RecepitAddressActivity : AppCompatActivity(), View.OnClickListener {

    val list = mutableListOf<AddressInfo>()
    lateinit var addressDao: AddressDao
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_bottom_btn -> startActivity<AddOrEditAddressActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        if (isNavigationBarShow()) {
            ll_layout_container.setPadding(0, 0, 0, dip(46))
        }
        addressDao = AddressDao(this)
        ll_bottom_btn.setOnClickListener(this)
        rv_receipt_address.run {
            layoutManager = LinearLayoutManager(context)
            adapter = AddressListAdapter(this@RecepitAddressActivity, list)
            addItemDecoration(RecycleViewDivider(this@RecepitAddressActivity, LinearLayout.VERTICAL))
        }
        ib_back.setOnClickListener{
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val addressList = addressDao.queryAddress()
        if (addressList.isNotEmpty()) {
            list.clear()
            list.addAll(addressList)
            rv_receipt_address.adapter.notifyDataSetChanged()
        } else {
            toast("您还没有添加任何地址，快去点下方添加按钮添加吧")
        }
    }
}