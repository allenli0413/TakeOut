package com.liyh.takeout.dagger2.component

import com.liyh.takeout.dagger2.module.OrderFragmentModule
import com.liyh.takeout.ui.fragment.OrderFragment
import dagger.Component

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  15 时 55 分
 * @descrip :
 */
@Component(modules = arrayOf(OrderFragmentModule::class))
interface OrderFragmentComponent {
    fun inject(orderFragment: OrderFragment)
}