package com.liyh.takeout.dagger2.component

import com.liyh.takeout.dagger2.module.GoodsFragmentModule
import com.liyh.takeout.ui.fragment.GoodsFragment
import dagger.Component

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  17 时 25 分
 * @descrip :
 */

@Component(modules = arrayOf(GoodsFragmentModule::class))
interface GoodsFragmentComponent {
    fun inJect(goodsFragment: GoodsFragment)
}