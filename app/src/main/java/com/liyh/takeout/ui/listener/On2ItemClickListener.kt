package com.liyh.takeout.ui.listener

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 14 日
 * @time  15 时 43 分
 * @descrip :
 */
interface On2ItemClickListener {
    fun onFirstItemClick(position: Int, obj: Any?)
    fun onSecondItemClick(position: Int, obj: Any?)
}