package com.liyh.takeout.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.liyh.takeout.R
import com.liyh.takeout.dagger2.component.DaggerGoodsFragmentComponent
import com.liyh.takeout.dagger2.module.GoodsFragmentModule
import com.liyh.takeout.presenter.GoodsFragmentPresenter
import com.liyh.takeout.ui.adapter.GoodsListAdapter
import com.liyh.takeout.ui.adapter.GoodsTypeListAdapter
import kotlinx.android.synthetic.main.fragment_goods.*
import org.jetbrains.anko.support.v4.toast
import se.emilsjolander.stickylistheaders.StickyListHeadersListView
import javax.inject.Inject

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  15 时 58 分
 * @descrip :
 */
class GoodsFragment : Fragment() {

    @Inject
    lateinit var presenter: GoodsFragmentPresenter
    lateinit var goodsListView: StickyListHeadersListView
    lateinit var goodsListAdapter: GoodsListAdapter
    lateinit var goodsTypeListAdapter: GoodsTypeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val goodsView = inflater.inflate(R.layout.fragment_goods, null)
        DaggerGoodsFragmentComponent.builder().goodsFragmentModule(GoodsFragmentModule(this)).build().inJect(this)
        return goodsView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sellerId = activity?.intent?.getStringExtra("id")
        if (!TextUtils.isEmpty(sellerId)) {
            presenter.getGoodsInfo(sellerId!!)
        }
        rv_goods_type.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            goodsTypeListAdapter = GoodsTypeListAdapter(presenter.goodsTypeList, this@GoodsFragment)
            adapter = goodsTypeListAdapter
        }
        goodsListView = slhlv
        goodsListAdapter = GoodsListAdapter(activity!!, presenter.goodsList)
        goodsListView.adapter = goodsListAdapter
    }


    fun onFailed(msg: String) {
        toast(msg)
    }

    fun onSuccess() {
        goodsTypeListAdapter.notifyDataSetChanged()
        goodsListAdapter.notifyDataSetChanged()
        goodsListView.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val oldPosition  = goodsTypeListAdapter.getSelectPosition()
                val typeId = presenter.goodsList[firstVisibleItem].typeId
                val newPostion: Int = presenter.getTypePositionById(typeId)
                if (oldPosition != newPostion) {
                    goodsTypeListAdapter.setSelected(presenter.goodsTypeList[newPostion])
                    rv_goods_type.scrollToPosition(newPostion + 3)
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            }

        })
    }
}