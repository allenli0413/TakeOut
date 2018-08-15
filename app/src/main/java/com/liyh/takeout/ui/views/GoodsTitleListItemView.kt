package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.GoodsInfo
import kotlinx.android.synthetic.main.item_type_header.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  22 时 11 分
 * @descrip :
 */
class GoodsTitleListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    fun bindData(itemData: GoodsInfo) {
        tv_header_title.text = itemData.typeName
    }

    init {
        View.inflate(context, R.layout.item_type_header, this)
    }
}