package com.liyh.takeout.model.bean

import com.google.gson.annotations.SerializedName
import com.heima.takeout.model.beans.Seller

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  14 时 44 分
 * @descrip :
 * 	public String id;
public String type;
public Seller seller;
public Rider rider;
public List<GoodsInfo> goodsInfos;
public Distribution distribution;
public OrderDetail detail;
 */
data class OrderInfo(
        @SerializedName("id")
        val id: String,
        @SerializedName("type")
        var type: String,
        @SerializedName("seller")
        val seller: Seller,
        @SerializedName("rider")
        val rider: Rider,
        @SerializedName("goodsInfos")
        val goodsInfos: List<GoodsInfo>,
        @SerializedName("distribution")
        val distribution: Distribution,
        @SerializedName("detail")
        val detail: OrderDetail)

/**
 * public int id;
public String name;
public String phone;
 */
data class Rider(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String
)

/**
 * public String username;
 * 	public String phone;
public String address;
public String pay;
public String time;
 */
data class OrderDetail(
        @SerializedName("username")
        val username: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("pay")
        val pay: String,
        @SerializedName("time")
        val time: String
)

/**
 * 	// 配送方式
public String type;
// 配送说明
public String des;
 */
data class Distribution(
        @SerializedName("type")
        val type: String,
        @SerializedName("des")
        val des: String
)