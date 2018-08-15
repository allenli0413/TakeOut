package com.liyh.takeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.heima.takeout.model.beans.Seller
import com.liyh.takeout.ui.activity.BusinessActivity
import com.liyh.takeout.ui.views.HomeListSellerItemView
import com.liyh.takeout.ui.views.HomeListTitleItemView
import org.jetbrains.anko.startActivity

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 24 日
 * @time  10 时 18 分
 * @descrip :
 */
class HomeListAdapter(val content: Context, val list: ArrayList<Seller>, val imgMap: HashMap<String, String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val ITEM_TYPE_TITLE = 1
        val ITEM_TYPE_SELLER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_TYPE_TITLE else ITEM_TYPE_SELLER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == ITEM_TYPE_SELLER) HomeSellerViewHolder(HomeListSellerItemView(content))
            else HomeTitleViewHolder(HomeListTitleItemView(content))

    override fun getItemCount(): Int = if (list.size > 0) list.size + 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == ITEM_TYPE_SELLER) {
            val itemView = holder.itemView as HomeListSellerItemView
            val itemData = list[position]
            itemView.setData(itemData)
            itemView.setOnClickListener {
                content.startActivity<BusinessActivity>("id" to itemData.id.toString())
            }
        } else {
            val itemView = holder.itemView as HomeListTitleItemView
            itemView.setData(imgMap)
        }
    }

    class HomeTitleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
    class HomeSellerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}