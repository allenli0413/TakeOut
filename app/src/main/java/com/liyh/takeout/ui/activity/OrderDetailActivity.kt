package com.liyh.takeout.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.liyh.takeout.R
import com.liyh.takeout.utils.OrderObservable
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.json.JSONObject
import java.util.*


/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 26 日
 * @time  17 时 57 分
 * @descrip :
 */
class OrderDetailActivity : AppCompatActivity(),Observer {
    var orderId:String = ""
    override fun update(o: Observable?, arg: Any?) {
        arg?.apply {
            val jsonObject = JSONObject(arg as String)
            val pushId = jsonObject.getString("id")
            val pushType = jsonObject.getString("type")
            if (orderId.equals(pushId)){
                val index = getIndex(pushType)
                (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
                (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        processIntent()
    }

    private fun processIntent() {
        if (intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId")
            val type = intent.getStringExtra("type")
            val index = getIndex(type)
            (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
            (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)
        }
    }

    fun getIndex(type: String): Int =
            when (type) {
                OrderObservable.ORDERTYPE_UNPAYMENT -> -1
//                typeInfo = "未支付";
                OrderObservable.ORDERTYPE_SUBMIT -> 0
//                typeInfo = "已提交订单";
                OrderObservable.ORDERTYPE_RECEIVEORDER -> 1
//                typeInfo = "商家接单";
                OrderObservable.ORDERTYPE_DISTRIBUTION,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE -> 2
//                typeInfo = "配送中";
                OrderObservable.ORDERTYPE_SERVED -> 3
//                typeInfo = "已送达";
                OrderObservable.ORDERTYPE_CANCELLEDORDER -> -1
//                typeInfo = "取消的订单";
                else -> -1
            }

}