package com.liyh.takeout.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.fragment.GoodsFragment
import com.liyh.takeout.ui.listener.On2ItemClickListener
import com.liyh.takeout.ui.views.GoodsListItemView
import com.liyh.takeout.ui.views.GoodsTitleListItemView
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  21 时 42 分
 * @descrip :
 */
class GoodsListAdapter(val goodsFragment: GoodsFragment, val goodsList: List<GoodsInfo>) : BaseAdapter(), StickyListHeadersAdapter {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        var holder: GoodsListViewHolder
        if (convertView == null) {
            view = GoodsListItemView(goodsFragment.activity)
            holder = GoodsListViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as GoodsListViewHolder
        }
        val itemData = goodsList[position]
        val itemView: GoodsListItemView = view as GoodsListItemView
        itemView.bindData(itemData, position)
        itemView.setOn2ItemClickListener(object : On2ItemClickListener {
            override fun onFirstItemClick(position: Int, obj: Any?) {//减号
                itemData.selectCount--
                notifyDataSetChanged()
                updateGoodsTypeRed(itemData, false)
            }

            override fun onSecondItemClick(position: Int, obj: Any?) {//加号
                itemData.selectCount++
                notifyDataSetChanged()
                updateGoodsTypeRed(itemData, true)
            }

        })
        return view
    }

    fun updateGoodsTypeRed(itemData: GoodsInfo, isAdd: Boolean) {
        val position = goodsFragment.presenter.getTypePositionById(itemData.typeId)
        val goodsTypeInfo = goodsFragment.presenter.goodsTypeList[position]
        if (isAdd) {
            goodsTypeInfo.cartCount++
        } else {
            goodsTypeInfo.cartCount--
        }
        goodsFragment.goodsTypeListAdapter.notifyDataSetChanged()
    }

    override fun getItem(position: Int): GoodsInfo = goodsList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = goodsList.size

    override fun getHeaderId(position: Int): Long = goodsList[position].typeId.toLong()

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        var holder: GoodsTitleListViewHolder
        if (convertView == null) {
            view = GoodsTitleListItemView(goodsFragment.activity)
            holder = GoodsTitleListViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as GoodsTitleListViewHolder
        }
        val itemData = goodsList[position]
        (view as GoodsTitleListItemView).bindData(itemData)
        return view
    }

    class GoodsListViewHolder(itemView: GoodsListItemView)
    class GoodsTitleListViewHolder(itemView: GoodsTitleListItemView)
}