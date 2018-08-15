package com.liyh.takeout.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.liyh.takeout.utils.OrderObservable


/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 10 日
 * @time  09 时 38 分
 * @descrip :
 */
class TalkReceiver : BroadcastReceiver() {
    private val TAG = "TalkReceiver"

    private var nm: NotificationManager? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (null == nm) {
            nm = nm ?: context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        val bundle = intent?.getExtras()

        val registrationId = bundle?.getString(JPushInterface.EXTRA_REGISTRATION_ID)
        val title = bundle?.getString(JPushInterface.EXTRA_TITLE)
        val msg = bundle?.getString(JPushInterface.EXTRA_MESSAGE)
        val extraString = bundle?.getString(JPushInterface.EXTRA_EXTRA)
        Log.d(TAG, "onReceive - " + intent?.getAction())
        Log.d(TAG, "registrationId = " + registrationId + ",title = " + title + ",msg = " + msg + ",extraString = " + extraString)
        if (JPushInterface.ACTION_REGISTRATION_ID == intent?.getAction()) {
            Log.d(TAG, "JPush用户注册成功")

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent?.getAction()) {
            Log.d(TAG, "接受到推送下来的自定义消息")

            // Push Talk messages are push down by custom message format
            extraString?.apply {
                processCustomMessage(context, extraString)
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent?.getAction()) {
            Log.d(TAG, "接受到推送下来的通知")

//            receivingNotification(context, bundle)

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent?.getAction()) {
            Log.d(TAG, "用户点击打开了通知")

//            openNotification(context, bundle)

        } else {
            Log.d(TAG, "Unhandled intent - " + intent?.getAction())
        }
    }

    private fun processCustomMessage(context: Context?, extraString: String) {
        OrderObservable.instance.newMsgComing(extraString)
    }
}