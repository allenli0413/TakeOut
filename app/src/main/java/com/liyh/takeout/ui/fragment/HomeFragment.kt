package com.liyh.takeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R
import com.liyh.takeout.dagger2.component.DaggerHomeFragmentComponent
import com.liyh.takeout.dagger2.module.HomeFragmentModule
import com.liyh.takeout.presenter.HomeFragmentPresenter
import com.liyh.takeout.ui.adapter.HomeListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 23 日
 * @time  15 时 12 分
 * @descrip :
 */
class HomeFragment : Fragment() {
    @Inject
    lateinit var presenter: HomeFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        DaggerHomeFragmentComponent.builder().homeFragmentModule(HomeFragmentModule(this)).build().inject(this)
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_home.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = HomeListAdapter(context, presenter.sellers, presenter.imgs)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
    }

    private fun initListener() {
        val sumH = dip(120)
        var sumY = 0
        rv_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sumY += dy
                var alphaDiff = sumY * 200 / sumH
                if (alphaDiff >= 200) {
                    alphaDiff = 200
                }
                val alpha = 55 + alphaDiff
                ll_title_container.setBackgroundColor(Color.argb(alpha, 0x31, 0x90, 0xE8))
            }
        })
    }


    private fun initData() {
        presenter.getSellerInfo()
//        list.clear()
//        for (i in 0..100) {
//            list.add("$i")
//        }
//        url_maps.clear()
//        url_maps["Hannibal"] = "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg"
//        url_maps["Big Bang Theory"] = "http://tvfiles.alphacoders.com/100/hdclearart-10.png"
//        url_maps["House of Cards"] = "http://cdn3.nflximg.net/images/3093/2043093.jpg"
//        url_maps["Game of Thrones"] = "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"
    }

    fun onSuccess() {
        toast("请求数据成功")
        rv_home.adapter.notifyDataSetChanged()
    }

    fun onFailed(errorMsg: String) {
        toast(errorMsg)
    }

}