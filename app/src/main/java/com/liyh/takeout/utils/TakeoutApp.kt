package com.liyh.takeout.utils

import cn.jpush.android.api.JPushInterface
import com.liyh.takeout.model.bean.UserInfo
import com.mob.MobApplication

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 30 日
 * @time  16 时 53 分
 * @descrip :
 */
class TakeoutApp: MobApplication() {
    companion object {
        var userInfo: UserInfo? = null
    }
    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }
}