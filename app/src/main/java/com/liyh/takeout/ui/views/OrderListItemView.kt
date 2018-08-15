package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.model.bean.OrderInfo
import com.liyh.takeout.utils.OrderObservable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_order_item.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  15 时 26 分
 * @descrip :
 */
class OrderListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.item_order_item, this)
    }

    fun bindData(data: OrderInfo) {
        Picasso.with(context).load(data.seller.icon).into(iv_order_item_seller_logo)
        tv_order_item_seller_name.text = data.seller.name
        tv_order_item_type.text = getType(data.type)
        tv_order_item_time.text = data.detail.time
        tv_order_item_foods.text = "${data.goodsInfos[0].name}等${data.goodsInfos.size}件商品"
        tv_order_item_money.text = getTotalPrice(data.goodsInfos)
    }

    private fun getTotalPrice(goodsInfos: List<GoodsInfo>): CharSequence? {
        var totalPrice = 0.00f
        goodsInfos.forEach {
            totalPrice += it.newPrice.toFloat()
        }
        return "¥${totalPrice.toString()}"
    }

    fun getType(type: String):String {
        return when(type) {
            OrderObservable.ORDERTYPE_UNPAYMENT -> "未支付"
            OrderObservable.ORDERTYPE_SUBMIT -> "已提交订单"
            OrderObservable.ORDERTYPE_RECEIVEORDER -> "商家已接单"
            OrderObservable.ORDERTYPE_DISTRIBUTION -> "配送中"
            OrderObservable.ORDERTYPE_SERVED -> "已送达"
            OrderObservable.ORDERTYPE_CANCELLEDORDER -> "取消的订单"
            else -> ""
        }
    }
}