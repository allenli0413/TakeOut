package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.ui.fragment.CommentFragment
import com.liyh.takeout.ui.fragment.GoodsFragment
import com.liyh.takeout.ui.fragment.SellerFragment
import kotlinx.android.synthetic.main.activity_business.*
import org.jetbrains.anko.dip

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  15 时 15 分
 * @descrip :
 */
class BusinessActivity : AppCompatActivity() {
    val fragments = listOf(GoodsFragment(), SellerFragment(), CommentFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        if (isNavigationBarShow()) {
            fl_Container.setPadding(0, 0, 0, dip(46))
        }

        vp.adapter = BusinessFragmentAdapter()
        tabs.setupWithViewPager(vp)
    }


    val titles = arrayOf("商品", "商家", "评论")

    inner class BusinessFragmentAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment? = fragments[position]

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]
    }
}