package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.heima.takeout.model.beans.Seller
import com.liyh.takeout.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_seller.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 24 日
 * @time  10 时 24 分
 * @descrip :
 */
class HomeListSellerItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.item_seller, this)
    }

    fun setData(itemData: Seller) {
        tvCount.text = "8"
        Picasso.with(context).load(itemData.icon).into(seller_logo)
        tv_title.text = itemData.name
        ratingBar.rating = itemData.score.toFloat()
        tv_home_sale.text = itemData.sale
        tv_home_send_price.text = "¥${itemData.sendPrice}起送/配送费¥${itemData.deliveryFee}"
        tv_home_distance.text = itemData.distance
    }
}