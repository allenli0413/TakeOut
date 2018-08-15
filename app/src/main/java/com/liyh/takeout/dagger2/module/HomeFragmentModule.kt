package com.liyh.takeout.dagger2.module

import com.liyh.takeout.presenter.HomeFragmentPresenter
import com.liyh.takeout.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 26 日
 * @time  17 时 00 分
 * @descrip :
 */
@Module
class HomeFragmentModule(val homeFragment: HomeFragment) {

    @Provides
    fun provideHomeFragmentPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenter(homeFragment)
    }
}