package com.liyh.takeout.presenter

import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.model.bean.GoodsTypeInfo
import com.liyh.takeout.ui.activity.BusinessActivity
import com.liyh.takeout.ui.fragment.GoodsFragment
import com.liyh.takeout.utils.TakeoutApp
import org.jetbrains.anko.info

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  16 时 58 分
 * @descrip :
 */
class GoodsFragmentPresenter(val goodsFragment: GoodsFragment) : BaseNetPresenter<List<GoodsTypeInfo>>() {

    val goodsList = mutableListOf<GoodsInfo>()
    val goodsTypeList = mutableListOf<GoodsTypeInfo>()
    override fun onFailed(msg: String) {
        goodsFragment.onFailed(msg)
    }

    override fun onSuccess(data: List<GoodsTypeInfo>) {
        goodsTypeList.clear()
        data[0].isSelected = true
        goodsTypeList.addAll(data)
        info { goodsList.toString() }
        goodsList.clear()
        val businessActivity = goodsFragment.activity as BusinessActivity
        goodsTypeList.forEach { goodsTypeInfo ->
            if (businessActivity.hasSelectedInfo) {
                goodsTypeInfo.cartCount = TakeoutApp.instance.queryCacheSelectedInfoByTypeId(goodsTypeInfo.id)
            }
            goodsTypeInfo.list.forEach { goodsInfo ->
                if (goodsTypeInfo.cartCount > 0) {
                    goodsInfo.selectCount = TakeoutApp.instance.queryCacheSelectedInfoByGoodsId(goodsInfo.id)
                }
                goodsInfo.typeId = goodsTypeInfo.id
                goodsInfo.typeName = goodsTypeInfo.name
            }
            goodsList.addAll(goodsTypeInfo.list)
        }
        goodsFragment.onSuccess()
    }

    fun getGoodsInfo(sellerId: String) {
        request(takeOutService.getGoodsInfo(sellerId))
    }

    /**
     * 通过类型id获取右侧商品列表的位置
     */
    fun getPositionByTypeId(id: Int): Int {
        goodsList.forEachIndexed { index, goodsInfo ->
            if (goodsInfo.typeId == id) {
                return index
            }
        }
        return -1
    }

    /**
     * 通过类型Id获取左侧类型列表的位置
     */
    fun getTypePositionById(id: Int): Int {
        goodsTypeList.forEachIndexed { index, goodsTypeInfo ->
            if (goodsTypeInfo.id == id) {
                return index
            }
        }
        return -1
    }

    //购物车集合
    private val cartList = arrayListOf<GoodsInfo>()

    /**
     * 获取购物车商品集合
     */
    fun getCartList(): ArrayList<GoodsInfo> {
        cartList.clear()
        goodsList.forEach {
            if (it.selectCount > 0) {
                cartList.add(it)
            }
        }
        return cartList
    }

    /**
     * 清空购物车数据
     */
    fun clearCartList() {
        if (cartList.isNotEmpty()) {
            cartList.forEach {
                it.selectCount = 0
            }
        }
        cartList.clear()
    }
}