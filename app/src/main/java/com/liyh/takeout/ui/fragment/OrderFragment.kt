package com.liyh.takeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.R
import com.liyh.takeout.dagger2.component.DaggerOrderFragmentComponent
import com.liyh.takeout.dagger2.module.OrderFragmentModule
import com.liyh.takeout.presenter.OrderFragmentPresenter
import com.liyh.takeout.ui.activity.LoginActivity
import com.liyh.takeout.ui.adapter.OrderListAdapter
import com.liyh.takeout.utils.TakeoutApp.Companion.userInfo
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 23 日
 * @time  15 时 12 分
 * @descrip :
 */
class OrderFragment : Fragment() {
    @Inject
    lateinit var presenter: OrderFragmentPresenter
    var isFirst = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        DaggerOrderFragmentComponent.builder().orderFragmentModule(OrderFragmentModule(this)).build().inject(this)
        return inflater.inflate(R.layout.fragment_order, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()

    }

    private fun initListener() {
        srl_order?.apply {
            setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
            setOnRefreshListener {
                requestDatas()
            }
        }
    }

    private fun initView() {
        rv_order_list?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = OrderListAdapter(context, presenter.orderList)

        }
    }

    fun onFailed(msg: String) {
        srl_order.isRefreshing = false
        toast("请求数据失败：$msg")
    }

    fun onSuccess() {
        srl_order.isRefreshing = false
        rv_order_list.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        requestDatas()
    }

    fun requestDatas() {
        if (userInfo == null) {
            toast("您还没有登录，请先登录")
            if (isFirst) {
                activity.startActivity<LoginActivity>()
                isFirst = false;
            }
        } else {
            presenter.getOrderList(userInfo!!.id.toString())
        }
    }
}