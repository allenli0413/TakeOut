package com.liyh.takeout.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  15 时 58 分
 * @descrip :
 */
class SellerFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_business,null)
        return view
    }
}