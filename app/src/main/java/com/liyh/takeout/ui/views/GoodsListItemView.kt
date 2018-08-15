package com.liyh.takeout.ui.views

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.listener.On2ItemClickListener
import com.liyh.takeout.utils.PriceFormater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_goods.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  21 时 44 分
 * @descrip :
 */
class GoodsListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    companion object {
        val ANIM_DURATION = 1000L
    }

    var listener: On2ItemClickListener? = null

    fun bindData(itemData: GoodsInfo, position: Int) {
        Picasso.with(context).load(itemData.icon).into(iv_icon)
        tv_name.text = itemData.name
        tv_form.text = itemData.form
        tv_month_sale.text = "月售${itemData.monthSaleNum}份"
        tv_newprice.text = PriceFormater.formate(itemData.newPrice.toFloat())
        tv_oldprice.text = PriceFormater.formate(itemData.oldPrice.toFloat())
        tv_oldprice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tv_oldprice.visibility = if (itemData.oldPrice > 0) View.VISIBLE else View.INVISIBLE
        tv_count.visibility = if (itemData.selectCount > 0) View.VISIBLE else View.INVISIBLE
        ib_minus.visibility = if (itemData.selectCount > 0) View.VISIBLE else View.INVISIBLE
        tv_count.text = itemData.selectCount.toString()

        ib_minus.setOnClickListener { listener?.onFirstItemClick(-1, itemData) }
        ib_add.setOnClickListener {
            if (itemData.selectCount == 0) {
                addOpreation()
            }
            listener?.onSecondItemClick(-1, itemData)
        }
    }

    private fun addOpreation() {
        val animationSet = AnimationSet(false)
        animationSet.duration = ANIM_DURATION
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(alphaAnimation)
        val rotateAnimation = RotateAnimation(0.0f, 720.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(rotateAnimation)
        val translateAnimation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 2.0f,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        translateAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(translateAnimation)
        tv_count.startAnimation(animationSet)
        ib_minus.startAnimation(animationSet)
    }

    init {
        View.inflate(context, R.layout.item_goods, this)
    }

    fun setOn2ItemClickListener(on2ItemClickListener: On2ItemClickListener) {
        listener = on2ItemClickListener
    }
}