package com.liyh.takeout.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.heima.takeout.model.beans.Seller
import com.liyh.takeout.R
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.model.bean.GoodsInfo
import com.liyh.takeout.ui.adapter.CartListAdapter
import com.liyh.takeout.ui.fragment.CommentFragment
import com.liyh.takeout.ui.fragment.GoodsFragment
import com.liyh.takeout.ui.fragment.SellerFragment
import com.liyh.takeout.utils.PriceFormater
import com.liyh.takeout.utils.TakeoutApp
import kotlinx.android.synthetic.main.activity_business.*
import org.jetbrains.anko.*

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  15 时 15 分
 * @descrip :
 */
class BusinessActivity : AppCompatActivity() {

    val fragments = listOf(GoodsFragment(), SellerFragment(), CommentFragment())
    var bottomSheetView: View? = null
    val goodsfragment: GoodsFragment by lazy { fragments[0] as GoodsFragment }
    var cartAdapter: CartListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        if (isNavigationBarShow()) {
            fl_Container.setPadding(0, 0, 0, dip(46))
        }
        parseIntentParams()
        vp.adapter = BusinessFragmentAdapter()
        tabs.setupWithViewPager(vp)
        bottom.setOnClickListener {
            showOrHideCartList()
        }
        tvSubmit.setOnClickListener {
            startActivity<ConfirmOrderActivity>()
        }
    }

    var hasSelectedInfo = false
    lateinit var mSeller: Seller
    private fun parseIntentParams() {
        if (intent.hasExtra("hasSelectedInfo")) {
            hasSelectedInfo = intent.getBooleanExtra("hasSelectedInfo", false)
        }
        mSeller = intent.getSerializableExtra("seller") as Seller
        tvSendPrice.text = "另需配送费${PriceFormater.formate(mSeller.deliveryFee.toFloat())}"
        tvDeliveryFee.text = "${PriceFormater.formate(mSeller.sendPrice.toFloat())}元起送"
    }

    fun showOrHideCartList() {
        bottomSheetView = bottomSheetView ?: LayoutInflater.from(this)
                .inflate(R.layout.cart_list, window.decorView as ViewGroup, false)
        bottomSheetView?.run {
            val tvClear = this.find<TextView>(R.id.tvClear)
            val rvCart = this.find<RecyclerView>(R.id.rvCart)
            rvCart.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@BusinessActivity)
                cartAdapter = CartListAdapter(this@BusinessActivity, goodsfragment.presenter.getCartList())
                adapter = cartAdapter
            }
            tvClear.setOnClickListener {
                val dialog = alert("您确定放弃这些美食吗？", "清空购物车") {
                    yesButton { clearCart() }
                    noButton { dialog -> dialog.dismiss() }
                }.show()
            }
            if (bottomSheetLayout.isSheetShowing) {
                bottomSheetLayout.dismissSheet()
            } else {
                if (goodsfragment.presenter.getCartList().size > 0)
                    bottomSheetLayout.showWithSheetView(bottomSheetView)
                rvCart.adapter.notifyDataSetChanged()
            }

        }
    }

    /**
     * 清空购物车
     */
    private fun clearCart() {
        //购物车列表
        goodsfragment.presenter.clearCartList()
        cartAdapter?.notifyDataSetChanged()
        //关闭购物车
        showOrHideCartList()
        //右侧列表
        goodsfragment.goodsListAdapter.notifyDataSetChanged()
        //左侧列表
        clearLeftRedDot()
        //清空缓存
        TakeoutApp.instance.clearCacheSelectedInfo(mSeller.id.toInt())
    }

    /**
     * 清空左侧红点
     */
    private fun clearLeftRedDot() {
        val goodsTypeList = goodsfragment.presenter.goodsTypeList
        goodsTypeList.forEach {
            it.cartCount = 0
        }
        goodsfragment.goodsTypeListAdapter.notifyDataSetChanged()
        updateCartCount()
    }

    /**
     * 获取加号目的坐标
     */
    fun getDestLocation(): IntArray {
        val destLocation = IntArray(2)
        imgCart.getLocationInWindow(destLocation)
        return destLocation
    }

    /**
     * 添加加号
     */
    fun addImageView(ib: ImageButton, width: Int, height: Int) {
        fl_Container.addView(ib, width, height)
    }

    fun updateCartCount() {
        var count = 0
        var price = 0.0f
        val cartList: List<GoodsInfo> = goodsfragment.presenter.getCartList()
        cartList.forEach {
            count += it.selectCount
            price += it.newPrice.toFloat() * it.selectCount
        }

        tvSelectNum.text = count.toString()
        tvSelectNum.visibility = if (count > 0) View.VISIBLE else View.GONE
        tvCountPrice.text = PriceFormater.formate(price)
        if (price >= mSeller.sendPrice.toFloat()){
            tvSubmit.visibility = View.VISIBLE
            tvSendPrice.visibility = View.GONE
        } else{
            tvSubmit.visibility = View.GONE
            tvSendPrice.visibility = View.VISIBLE
        }
    }


    val titles = arrayOf("商品", "商家", "评论")

    inner class BusinessFragmentAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment? = fragments[position]

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]
    }


}