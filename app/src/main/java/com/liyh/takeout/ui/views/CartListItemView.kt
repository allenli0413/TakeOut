package com.liyh.takeout.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liyh.takeout.R
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.activity.BusinessActivity
import com.liyh.takeout.utils.Constants
import com.liyh.takeout.utils.PriceFormater
import com.liyh.takeout.utils.TakeoutApp
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 20 日
 * @time  16 时 44 分
 * @descrip :
 */
class CartListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    lateinit var businessActivity: BusinessActivity
    init {
        View.inflate(context, R.layout.item_cart, this)
        businessActivity = context as BusinessActivity
    }

    fun bindData(itemData: GoodsInfo) {
        tv_name.text = itemData.name
        tv_type_all_price.text = PriceFormater.formate(itemData.newPrice.toFloat() * itemData.selectCount)
        tv_count.text = itemData.selectCount.toString()
        val cartList = businessActivity.goodsfragment.presenter.getCartList()
        ib_minus.setOnClickListener {
            if (itemData.selectCount == 1){
                cartList.remove(itemData)
                //删除缓存
                TakeoutApp.instance.deleteCacheSelectedInfo(itemData.id)
            } else{
                //更新缓存
                TakeoutApp.instance.updateCacheSelectedInfo(itemData.id, Constants.MINUS)
            }
            if (cartList.size == 0) {
                businessActivity.showOrHideCartList()
            }
            itemData.selectCount --
            //购物车
            businessActivity.cartAdapter?.notifyDataSetChanged()
            //左侧列表
            businessActivity.goodsfragment.goodsListAdapter.updateGoodsTypeRed(itemData, false)
            //右侧列表
            businessActivity.goodsfragment.goodsListAdapter.notifyDataSetChanged()
            //底部菜单
            businessActivity.updateCartCount()
        }
        ib_add.setOnClickListener {
            itemData.selectCount ++
            //购物车
            businessActivity.cartAdapter?.notifyDataSetChanged()
            //左侧列表
            businessActivity.goodsfragment.goodsListAdapter.updateGoodsTypeRed(itemData, true)
            //右侧列表
            businessActivity.goodsfragment.goodsListAdapter.notifyDataSetChanged()
            //底部菜单
            businessActivity.updateCartCount()
            TakeoutApp.instance.updateCacheSelectedInfo(itemData.id, Constants.ADD)

        }
    }
}