package com.liyh.takeout.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.model.bean.AddressInfo
import com.liyh.takeout.ui.activity.RecepitAddressActivity
import com.liyh.takeout.ui.views.AddressListItemView

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 40 分
 * @descrip : 地址列表
 */
class AddressListAdapter(val context: Context, val addressList: List<AddressInfo>)
    : RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder =
            AddressListViewHolder(AddressListItemView(context))

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        val itemData = addressList[position]
        val itemView = holder.itemView as AddressListItemView
        itemView.bindData(itemData)
        itemView.setOnClickListener {
            val activity = context as RecepitAddressActivity
            val intent = Intent()
            intent.putExtra("addressInfo", itemData)
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }
    }

    class AddressListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}