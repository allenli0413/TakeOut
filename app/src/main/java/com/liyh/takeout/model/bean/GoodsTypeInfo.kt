package com.liyh.takeout.model.bean

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  17 时 00 分
 * @descrip :
 */
class GoodsTypeInfo {
    var id: Int = 0 //商品类型id
    var name: String = "" //商品类型名称
    var info: String = ""  //特价信息
    var list: List<GoodsInfo> = listOf() //商品列表
    var cartCount: Int = 1
    var isSelected: Boolean = false
    override fun toString(): String {
        return "GoodsTypeInfo(id=$id, name='$name', info='$info', list=$list)"
    }


}