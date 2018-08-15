package com.liyh.takeout.dagger2.module

import com.liyh.takeout.presenter.LoginActivityPresenter
import com.liyh.takeout.ui.activity.LoginActivity
import dagger.Module
import dagger.Provides

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  15 时 20 分
 * @descrip :
 */
@Module
class LoginActivityModule(val loginActivity: LoginActivity) {
    @Provides
    fun provideLoginActivityPresenter(): LoginActivityPresenter {
        return LoginActivityPresenter(loginActivity)
    }
}