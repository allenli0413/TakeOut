package com.liyh.takeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.views.CartListItemView

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 40 分
 * @descrip :
 */
class CartListAdapter(val context: Context, val cartList: List<GoodsInfo>)
    : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder
            = CartListViewHolder(CartListItemView(context))

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val itemData = cartList[position]
        val itemView = holder.itemView as CartListItemView
        itemView.bindData(itemData)
    }


    class CartListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}