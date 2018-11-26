package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.liyh.takeout.R
import com.liyh.takeout.dagger2.component.DaggerLoginActivityComponent
import com.liyh.takeout.dagger2.module.LoginActivityModule
import com.liyh.takeout.extentions.isValidCode
import com.liyh.takeout.extentions.isValidPhone
import com.liyh.takeout.model.bean.UserInfo
import com.liyh.takeout.presenter.LoginActivityPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 30 日
 * @time  15 时 48 分
 * @descrip :
 */
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var presenter: LoginActivityPresenter
    val eventHandler = object : EventHandler() {
        override fun afterEvent(event: Int, result: Int, data: Any) {
            if (data is Throwable) {
                val msg = data.message ?: "error"
                runOnUiThread { toast(msg) }
            } else {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    runOnUiThread {
                        toast("验证码已发送，请查收")
                        tv_user_code.isEnabled = false
                        timer.start()
                    }

                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    login()
                }
            }
        }
    }
    val timer = MyCountDownTimer(60000, 1000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DaggerLoginActivityComponent.builder().loginActivityModule(LoginActivityModule(this)).build().inject(this)
        SMSSDK.registerEventHandler(eventHandler)

        initListener()
    }

    fun onSuccess(data: UserInfo) {
        timer.onFinish()
        finish()
        toast("登录成功")
    }

    fun onFailed(errorMsg: String) {
        toast(errorMsg)
    }

    private fun initListener() {
        iv_user_back.setOnClickListener { finish() }
        tv_user_code.setOnClickListener {
            val phoneNum = et_user_phone.text.toString().trim()
            if (phoneNum.isValidPhone()) {
                SMSSDK.getVerificationCode("86", phoneNum)
            } else {
                et_user_phone.error = "请输入正确的手机号码"
            }

        }
        login.setOnClickListener {
            val phoneNum = et_user_phone.text.toString().trim()
            val codeNum = et_user_code.text.toString().trim()
            if (phoneNum.isValidPhone() and codeNum.isValidCode()) {
                hideSoftKeyBoard()
//                SMSSDK.submitVerificationCode("86", phoneNum, codeNum)
                login()
            } else {
                toast("手机号或验证码输入错误，请检查后登录")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterEventHandler(eventHandler)
        timer.cancel()
        timer.onFinish()
//        presenter.onDestroy()
    }

    fun hideSoftKeyBoard() {
        if (inputMethodManager.isActive)
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun login() {
        val phoneNum = et_user_phone.text.toString().trim()
        presenter.getUserInfo(phoneNum)
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        // 参数依次为总时长,和计时的时间间隔

        // 计时完毕时触发
        override fun onFinish() {
            tv_user_code.isEnabled = true
            tv_user_code.text = "重新发送"
        }

        // 计时过程显示
        override fun onTick(millisUntilFinished: Long) {
            tv_user_code.text = "${millisUntilFinished / 1000}秒后重试"
        }

    }

}
