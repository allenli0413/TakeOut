package com.liyh.takeout.receiver

import android.content.Context
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 10 日
 * @time  12 时 01 分
 * @descrip :
 */
class MyJPushReceiver: JPushMessageReceiver() {
    override fun onAliasOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onAliasOperatorResult(p0, p1)
    }
}