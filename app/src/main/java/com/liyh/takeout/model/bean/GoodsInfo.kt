package com.liyh.takeout.model.bean

import com.google.gson.annotations.SerializedName

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  15 时 03 分
 * @descrip :
 *    int id;//商品id
String name;//商品名称
String icon;//商品图片
String form;//组成
int monthSaleNum;//月销售量
boolean bargainPrice;//特价
boolean isNew;//是否是新产品
String newPrice;//新价
int oldPrice;//原价
int sellerId;
 */
data class GoodsInfo(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("icon")
        var icon: String,
        @SerializedName("form")
        var form: String,
        @SerializedName("monthSaleNum")
        var monthSaleNum: Int,
        @SerializedName("bargainPrice")
        var bargainPrice: Boolean,
        @SerializedName("isNew")
        var isNew: Boolean,
        @SerializedName("newPrice")
        var newPrice: String,
        @SerializedName("oldPrice")
        var oldPrice: Int,
        @SerializedName("sellerId")
        var sellerId: Int,
        var selectCount: Int = 0,
        var typeId: Int = 0,
        var typeName: String = ""
)