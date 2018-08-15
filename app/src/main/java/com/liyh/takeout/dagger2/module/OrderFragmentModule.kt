package com.liyh.takeout.dagger2.module

import com.liyh.takeout.presenter.OrderFragmentPresenter
import com.liyh.takeout.ui.fragment.OrderFragment
import dagger.Module
import dagger.Provides

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 09 日
 * @time  15 时 49 分
 * @descrip :
 */
@Module
class OrderFragmentModule(val orderFragment: OrderFragment) {
    @Provides
    fun provideOrderFragmentPresenter(): OrderFragmentPresenter {
        return OrderFragmentPresenter(orderFragment)
    }
}