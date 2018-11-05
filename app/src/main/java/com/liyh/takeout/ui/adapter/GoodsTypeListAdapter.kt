package com.liyh.takeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.liyh.takeout.model.bean.GoodsTypeInfo
import com.liyh.takeout.ui.fragment.GoodsFragment
import com.liyh.takeout.ui.listener.On1ItemClickListener
import com.liyh.takeout.ui.views.GoodsTypeListItemView

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  21 时 28 分
 * @descrip :
 */
class GoodsTypeListAdapter(val goodsTypeList: List<GoodsTypeInfo>, val fragment: GoodsFragment)
    : RecyclerView.Adapter<GoodsTypeListAdapter.GoodsTypeViewHolder>() {
    var context: Context

    init {
        context = fragment.activity!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsTypeViewHolder
            = GoodsTypeViewHolder(GoodsTypeListItemView(context))

    override fun getItemCount(): Int = goodsTypeList.size

    override fun onBindViewHolder(holder: GoodsTypeViewHolder, position: Int) {
        val itemData = goodsTypeList[position]
//        if (position == 0 ) itemData.isSelected = true
        val itemView = holder.itemView as GoodsTypeListItemView
        itemView.bindData(itemData, position)
        itemView.setOnItemClickListener(object : On1ItemClickListener {
            override fun onItemClick(position: Int, obj: Any?) {
                val itemModel = obj as GoodsTypeInfo
                setSelected(itemModel)
                val selectPosition = fragment.presenter.getPositionByTypeId(itemModel.id)
                fragment.goodsListView.setSelection(selectPosition)
            }

        })
    }

    fun setSelected(itemModel: GoodsTypeInfo) {
        goodsTypeList.forEach {
            it.isSelected = false
        }
        itemModel.isSelected = true
        notifyDataSetChanged()
    }

    fun getSelectPosition(): Int{
        goodsTypeList.forEachIndexed { index, goodsTypeInfo ->
            if (goodsTypeInfo.isSelected){
                return index
            }
        }
        return 0
    }

    class GoodsTypeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}