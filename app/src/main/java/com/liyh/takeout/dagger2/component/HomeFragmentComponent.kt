package com.liyh.takeout.dagger2.component

import com.liyh.takeout.dagger2.module.HomeFragmentModule
import com.liyh.takeout.ui.fragment.HomeFragment
import dagger.Component

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 26 日
 * @time  17 时 08 分
 * @descrip :
 */
@Component(modules = arrayOf(HomeFragmentModule::class))
interface HomeFragmentComponent {
    fun inject(homeFragment: HomeFragment)
}