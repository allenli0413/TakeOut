package com.liyh.takeout.dagger2.module

import com.liyh.takeout.presenter.GoodsFragmentPresenter
import com.liyh.takeout.ui.fragment.GoodsFragment
import dagger.Module
import dagger.Provides

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  17 时 23 分
 * @descrip :
 */
@Module
class GoodsFragmentModule(val goodsFragment: GoodsFragment) {
    @Provides
    fun provideGoodsFragmentPresenter(): GoodsFragmentPresenter {
        return GoodsFragmentPresenter(goodsFragment)
    }
}