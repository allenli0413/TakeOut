package com.liyh.takeout.model.bean

import com.google.gson.annotations.SerializedName

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 27 日
 * @time  18 时 15 分
 * @descrip :
 */
data class Promotion(
        @SerializedName("id")
        val id: Int,
        @SerializedName("info")
        val info: String,
        @SerializedName("pic")
        val pic: String)