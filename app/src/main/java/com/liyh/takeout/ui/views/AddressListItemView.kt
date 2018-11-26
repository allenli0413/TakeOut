package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.AddressInfo
import com.liyh.takeout.ui.activity.AddOrEditAddressActivity
import com.liyh.takeout.ui.activity.RecepitAddressActivity
import kotlinx.android.synthetic.main.item_receipt_address.view.*
import org.jetbrains.anko.startActivity

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 44 分
 * @descrip :
 */
class AddressListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    lateinit var activity: RecepitAddressActivity

    init {
        View.inflate(context, R.layout.item_receipt_address, this)
        activity = context as RecepitAddressActivity
    }

    fun bindData(itemData: AddressInfo) {
        tv_name.text = itemData.userName
        tv_sex.text = itemData.sex
        tv_phone.text = "${itemData.phone},${itemData.phoneOther}"
        tv_label.text = itemData.tag
        tv_address.text = "${itemData.address} ${itemData.addressDetail}"
        iv_edit.setOnClickListener {
            activity.startActivity<AddOrEditAddressActivity>("address" to itemData)
        }
    }
}