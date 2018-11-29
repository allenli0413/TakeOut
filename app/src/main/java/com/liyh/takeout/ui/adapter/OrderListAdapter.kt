package com.liyh.takeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.model.bean.OrderInfo
import com.liyh.takeout.ui.activity.MainActivity
import com.liyh.takeout.ui.activity.OrderDetailActivity
import com.liyh.takeout.ui.views.OrderListItemView
import com.liyh.takeout.utils.OrderObservable
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import java.util.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  14 时 41 分
 * @descrip :
 */
class OrderListAdapter(val context: Context, val orderList: List<OrderInfo>) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>(), Observer {
    override fun update(o: Observable?, arg: Any?) {
        arg?.apply {
            val jsonObject = JSONObject(arg as String)
            val pushId = jsonObject.getString("orderId")
            val pushType = jsonObject.getString("type")
            orderList.forEachIndexed { index, orderInfo ->
                if (orderInfo.id == pushId) {
                    orderInfo.type = pushType
                }
                notifyItemChanged(index)
            }
        }
    }

    init {
        OrderObservable.instance.addObserver(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder = OrderListViewHolder(OrderListItemView(context))

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val itemView = holder.itemView as OrderListItemView
        val data = orderList[position]
        itemView.bindData(data)
        itemView.setOnClickListener {
            (context as MainActivity).startActivity<OrderDetailActivity>("orderId" to data.id,
                    "type" to data.type)

        }
    }


    class OrderListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}