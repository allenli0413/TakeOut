package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.amap.api.services.core.PoiItem
import com.liyh.takeout.R
import kotlinx.android.synthetic.main.item_around_address.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 44 分
 * @descrip :
 */
class AroundListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.item_around_address, this)
    }

    fun bindData(itemData: PoiItem) {
        tv_title.text = itemData.title
        tv_address.text = itemData.snippet
    }
}