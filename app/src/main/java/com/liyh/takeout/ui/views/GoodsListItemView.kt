package com.liyh.takeout.ui.views

import android.graphics.Paint
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.CacheSelectedInfo
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.activity.BusinessActivity
import com.liyh.takeout.ui.listener.On2ItemClickListener
import com.liyh.takeout.utils.Constants
import com.liyh.takeout.utils.PriceFormater
import com.liyh.takeout.utils.TakeoutApp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_goods.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  21 时 44 分
 * @descrip :
 */
class GoodsListItemView(context: FragmentActivity?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
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

        ib_minus.setOnClickListener {
            if (itemData.selectCount == 1) {
                //最后一次点击 执行动画集
                minusOpreation()
                //删除缓存
                TakeoutApp.instance.deleteCacheSelectedInfo(itemData.id)
            } else {
                //更新缓存
                TakeoutApp.instance.updateCacheSelectedInfo(itemData.id, Constants.MINUS)
            }
            listener?.onFirstItemClick(-1, itemData)
            (context as BusinessActivity).updateCartCount()
        }
        ib_add.setOnClickListener {
            if (itemData.selectCount == 0) {
                //第一次点击 执行动画集
                addOpreation()
                TakeoutApp.instance.addCacheSelectedInfo(CacheSelectedInfo(itemData.sellerId, itemData.typeId, itemData.id, 1))
            } else {
                TakeoutApp.instance.updateCacheSelectedInfo(itemData.id, Constants.ADD)
            }
            //克隆+号并添加到activity中
            val ib = ImageButton(context)
            //背景，大小，位置都一样
            ib.setBackgroundResource(R.drawable.button_add)
            val srcLocation = IntArray(2)
            ib_add.getLocationInWindow(srcLocation)
            ib.x = srcLocation[0].toFloat()
            ib.y = srcLocation[1].toFloat()
            //在activity中添加动画
            (context as BusinessActivity).addImageView(ib, ib_add.width, ib_add.height)
            //获取动画结束位置
            val destLocation: IntArray = (context as BusinessActivity).getDestLocation()
            val parabolaAnimation: AnimationSet = getParabolaAnimation(srcLocation, destLocation)
            parabolaAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    //动画结束移除动画
                    handler?.post {
                        (ib.parent as ViewGroup).removeView(ib)
                        (context as BusinessActivity).updateCartCount()
                    }
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
            ib.startAnimation(parabolaAnimation)
            listener?.onSecondItemClick(-1, itemData)
        }
    }

    /**
     * 获取抛物线动画集
     */
    private fun getParabolaAnimation(srcLocation: IntArray, destLocation: IntArray): AnimationSet {
        val animationSet = AnimationSet(false)
        animationSet.duration = ANIM_DURATION
        val translateAnimationX = TranslateAnimation(Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, destLocation[0].toFloat() - srcLocation[0].toFloat(),
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f)
        translateAnimationX.duration = ANIM_DURATION
        animationSet.addAnimation(translateAnimationX)
        val translateAnimationY = TranslateAnimation(Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, destLocation[1].toFloat() - srcLocation[1].toFloat())
        translateAnimationY.duration = ANIM_DURATION
        //添加Y方向的加速器
        translateAnimationY.interpolator = AccelerateInterpolator()
        animationSet.addAnimation(translateAnimationY)
        return animationSet
    }

    /**
     * 点击加号动画
     */
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
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        translateAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(translateAnimation)
        tv_count.startAnimation(animationSet)
        ib_minus.startAnimation(animationSet)
    }

    /**
     * 减号动画
     */
    private fun minusOpreation() {
        val animationSet = AnimationSet(false)
        animationSet.duration = ANIM_DURATION
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(alphaAnimation)
        val rotateAnimation = RotateAnimation(720.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = ANIM_DURATION
        animationSet.addAnimation(rotateAnimation)
        val translateAnimation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 2.0F,
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