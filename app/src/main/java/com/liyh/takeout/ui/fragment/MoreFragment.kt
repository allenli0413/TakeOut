package com.liyh.takeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 23 日
 * @time  15 时 12 分
 * @descrip :
 */
class MoreFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_more, null)
    }
}