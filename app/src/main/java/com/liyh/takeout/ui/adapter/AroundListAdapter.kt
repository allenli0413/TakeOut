package com.liyh.takeout.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.core.PoiItem
import com.liyh.takeout.ui.activity.MapLocationActivity
import com.liyh.takeout.ui.views.AroundListItemView

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 40 分
 * @descrip : 地址列表
 */
class AroundListAdapter(val context: Context, val aroundList: List<PoiItem>)
    : RecyclerView.Adapter<AroundListAdapter.AroundListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AroundListAdapter.AroundListViewHolder =
            AroundListAdapter.AroundListViewHolder(AroundListItemView(context))

    override fun getItemCount(): Int = aroundList.size

    override fun onBindViewHolder(holder: AroundListAdapter.AroundListViewHolder, position: Int) {
        val itemData = aroundList[position]
        val itemView = holder.itemView as AroundListItemView
        itemView.bindData(itemData)
        itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra("title", itemData.title)
            intent.putExtra("address", itemData.snippet)
            val activity = (context as MapLocationActivity)
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }
    }

    class AroundListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}