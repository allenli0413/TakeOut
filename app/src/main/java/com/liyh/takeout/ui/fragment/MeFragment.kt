package com.liyh.takeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R
import com.liyh.takeout.ui.activity.LoginActivity
import com.liyh.takeout.utils.TakeoutApp
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.startActivity

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 23 日
 * @time  15 时 12 分
 * @descrip :
 */
class MeFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_user, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        login.setOnClickListener {
            activity.startActivity<LoginActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        val user = TakeoutApp.userInfo
        if (user != null) {
            login.visibility = View.GONE
            ll_userinfo.visibility = View.VISIBLE
            username.text = "欢迎您，${user.name}"
            phone.text = user.phone
        } else {
            ll_userinfo.visibility = View.INVISIBLE
            login.visibility = View.VISIBLE
        }
    }
}