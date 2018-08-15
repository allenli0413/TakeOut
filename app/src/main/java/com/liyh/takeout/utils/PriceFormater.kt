package com.liyh.takeout.utils

import java.text.NumberFormat

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 15 日
 * @time  11 时 00 分
 * @descrip :
 */
object PriceFormater {
    fun formate(price: Float):String{
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        return format.format(price.toDouble())
    }
}