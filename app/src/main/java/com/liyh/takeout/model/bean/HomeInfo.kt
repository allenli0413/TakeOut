package com.liyh.takeout.model.bean

import com.google.gson.annotations.SerializedName
import com.heima.takeout.model.beans.Seller

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  15 时 46 分
 * @descrip :
 */
data class HomeInfo(
        @SerializedName("categorieList")
        var categorieList: ArrayList<Categorie>,
        @SerializedName("nearbySellerList")
        var nearbySellerList: ArrayList<Seller>,
        @SerializedName("otherSellerList")
        var otherSellerList: ArrayList<Seller>,
        @SerializedName("promotionList")
        var promotionList: ArrayList<Promotion>)