package com.liyh.takeout.dagger2.component

import com.liyh.takeout.dagger2.module.LoginActivityModule
import com.liyh.takeout.ui.activity.LoginActivity
import dagger.Component
import javax.inject.Inject

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  15 时 34 分
 * @descrip :
 */
@Component(modules = arrayOf(LoginActivityModule::class))
interface LoginActivityComponent {
    fun inject(loginActivity: LoginActivity)
}