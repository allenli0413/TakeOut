package com.liyh.takeout.presenter

import com.liyh.takeout.model.bean.OrderInfo
import com.liyh.takeout.ui.fragment.OrderFragment

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  15 时 40 分
 * @descrip :
 */
class OrderFragmentPresenter(val orderFragment: OrderFragment): BaseNetPresenter<List<OrderInfo>>() {
    val orderList = mutableListOf<OrderInfo>()

    override fun onFailed(msg: String) {
            orderFragment.onFailed(msg)
    }

    override fun onSuccess(data: List<OrderInfo>) {
        orderList.clear()
        orderList.addAll(data)
        orderFragment.onSuccess()
    }

    fun getOrderList(id: String){
        val observable = takeOutService.getOrderInfo(id)
        request(observable)
    }

}