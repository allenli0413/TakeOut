package com.heima.takeout.model.beans

import android.content.pm.ActivityInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Seller(
        @SerializedName("id")
        val id: Long,
        @SerializedName("pic")
        val pic: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("score")
        val score: String,
        @SerializedName("sale")
        val sale: String,
        @SerializedName("ensure")
        val ensure: String,
        @SerializedName("invoice")
        val invoice: String,
        @SerializedName("sendPrice")
        val sendPrice: String,
        @SerializedName("deliveryFee")
        val deliveryFee: String,
        @SerializedName("recentVisit")
        val recentVisit: String,
        @SerializedName("distance")
        val distance: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("activityList")
        val activityList: ArrayList<ActivityInfo>) : Serializable{

//    var id: Long = 0
//    var pic: String? = null
//    var name: String? = null
//
//    var score: String? = null
//    var sale: String? = null
//    var ensure: String? = null
//
//    var invoice: String? = null
//    var sendPrice: String? = null
//    var deliveryFee: String? = null
//
//    var recentVisit: String? = null
//    var distance: String? = null
//    var time: String? = null
//
//    var icon: String? = null
//
//    var activityList: ArrayList<ActivityInfo>? = null

}
