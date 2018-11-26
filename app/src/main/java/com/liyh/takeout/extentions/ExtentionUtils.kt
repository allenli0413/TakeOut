package com.liyh.takeout.extentions

import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.widget.TextView

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  11 时 27 分
 * @descrip :
 */

fun String.isValidPhone(): Boolean = this.matches(Regex("^[1][35678]\\d{9}"))

fun String.isValidCode(): Boolean = !isNullOrEmpty() and (this.length >= 4)
fun Activity.isNavigationBarShow(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = getWindowManager().getDefaultDisplay()
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(this).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            !(menu || back)
        }
fun TextView.getContent(): String = text.toString().trim()