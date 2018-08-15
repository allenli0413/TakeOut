package com.liyh.takeout.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.GoodsTypeInfo
import com.liyh.takeout.ui.listener.On1ItemClickListener
import kotlinx.android.synthetic.main.item_type.view.*
import org.jetbrains.anko.textColor

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  21 时 31 分
 * @descrip :
 */
class GoodsTypeListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    var listener: On1ItemClickListener? = null

    fun bindData(itemData: GoodsTypeInfo, position: Int) {
        val size = itemData.cartCount
        if (size > 0) {
            tvRedDotCount.visibility = View.VISIBLE
            tvRedDotCount.text = size.toString()
        } else {
            tvRedDotCount.visibility = View.INVISIBLE
        }
        type.text = itemData.name
        if (itemData.isSelected) {
            ll_type_container.setBackgroundColor(Color.WHITE)
            type.textColor = Color.BLACK
            type.typeface = Typeface.DEFAULT_BOLD
        } else {
            ll_type_container.setBackgroundColor(Color.parseColor("#b9dedcdc"))
            type.textColor = Color.GRAY
            type.typeface = Typeface.DEFAULT
        }
        ll_type_container.setOnClickListener {
            listener?.onItemClick(position, itemData)
        }
    }

    init {
        View.inflate(context, R.layout.item_type, this)
    }

    fun setOnItemClickListener(on1ItemClickListener: On1ItemClickListener) {
        listener = on1ItemClickListener
    }
}