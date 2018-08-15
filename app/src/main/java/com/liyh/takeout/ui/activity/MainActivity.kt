package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.ui.fragment.HomeFragment
import com.liyh.takeout.ui.fragment.MeFragment
import com.liyh.takeout.ui.fragment.MoreFragment
import com.liyh.takeout.ui.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip


class MainActivity : AppCompatActivity() {

    val fragments = arrayListOf(HomeFragment(), OrderFragment(), MeFragment(), MoreFragment())
    var childCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isNavigationBarShow()) {
            ll_activity_main.setPadding(0, 0, 0, dip(50))
        }
        childCount = activity_home_bottom_container.childCount
        setSelectFragment(0)
        for (index in 0 until childCount) {
            activity_home_bottom_container.getChildAt(index).setOnClickListener {
                setSelectFragment(index)
            }
        }
    }

    /**
     * 设置选中的页面
     */
    private fun setSelectFragment(index: Int) {
        val beginTransaction = fragmentManager.beginTransaction()
        beginTransaction.replace(R.id.activity_home_content, fragments[index]).commit()
        for (i in 0 until childCount) {
            val child = activity_home_bottom_container.getChildAt(i)
            if (i == index) {
                setChildEnable(child, false)
            } else {
                setChildEnable(child, true)
            }
        }
    }

    /**
     * 设置子控件enable
     */
    private fun setChildEnable(child: View?, isEnabled: Boolean) {
        if (child is ViewGroup) {
            val count = child.childCount
            for (index in 0 until count) {
                child.getChildAt(index).isEnabled = isEnabled
            }
        }
    }

    /**
     * 检查是否存在NavigationBar
     */
    fun checkDeviceHasNavigationBar(): Boolean {
        var hasNavigationBar = false
        val id = resources.getIdentifier("config_showNavigationBar", "bool",
                "android")
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }
        return hasNavigationBar
    }

}
