package com.liyh.takeout.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.AMapUtils
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.*
import com.liyh.takeout.R
import com.liyh.takeout.utils.OrderObservable
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 26 日
 * @time  17 时 57 分
 * @descrip :
 */
class OrderDetailActivity : AppCompatActivity(), Observer {
    var orderId: String = ""
    lateinit var map: AMap
    lateinit var rideMarker: Marker
    val positions = ArrayList<LatLng>()
    override fun update(o: Observable?, arg: Any?) {
        arg?.apply {
            val jsonObject = JSONObject(arg as String)
            val pushId = jsonObject.getString("orderId")
            val pushType = jsonObject.getString("type")
            if (orderId.equals(pushId)) {
                val index = getIndex(pushType)
                (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
                (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)
                when (pushType) {
                    OrderObservable.ORDERTYPE_RECEIVEORDER -> {
                        mMapView.visibility = View.VISIBLE
//                        买家坐标 39.912543,116.47291
//                        卖家坐标 39.91817930378354,116.4773297310096
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.912543, 116.47291), 16f))
                        val sellerMarker = map.addMarker(MarkerOptions()
                                .position(LatLng(39.91817930378354, 116.4773297310096))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.order_seller_icon))
                                .title("世贸天阶").snippet("我是卖家"))
                        val imageView = ImageView(this@OrderDetailActivity)
                        imageView.setImageResource(R.drawable.order_buyer_icon)
                        val buyerMarker = map.addMarker(MarkerOptions()
                                .position(LatLng(39.912543, 116.47291))
                                .icon(BitmapDescriptorFactory.fromView(imageView))
                                .title("东方梅地亚").snippet("我是买家"))
                    }
                    OrderObservable.ORDERTYPE_DISTRIBUTION -> {
//                          骑手坐标 39.9219149887,116.4581680298
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.9219149887, 116.4581680298), 16f))
                        val imageView = ImageView(this@OrderDetailActivity)
                        imageView.setImageResource(R.drawable.order_rider_icon)
                        rideMarker = map.addMarker(MarkerOptions()
                                .position(LatLng(39.9219149887, 116.4581680298))
                                .icon(BitmapDescriptorFactory.fromView(imageView))
                                .title("美团外卖").snippet("我是骑手"))
                        rideMarker.showInfoWindow()
                        positions.add(LatLng(39.9219149887, 116.4581680298))
                    }
                    OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL,
                    OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL -> {
                        if (jsonObject.has("lat")) {
                            val lat = jsonObject.getString("lat")
                            val lng = jsonObject.getString("lng")
                            val latLng = LatLng(lat.toDouble(), lng.toDouble())
                            rideMarker.hideInfoWindow()
                            rideMarker.position = latLng
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                            val distance = AMapUtils.calculateLineDistance(LatLng(39.912543, 116.47291),latLng)
                            rideMarker.title = "据您还有${distance}米!"
                            rideMarker.showInfoWindow()
                            positions.add(latLng)
                            val size = positions.size
                            val polyLine = map.addPolyline(PolylineOptions().color(Color.RED).width(3.0f)
                                    .add(positions[size - 1]).add(positions[size - 2]))
                        }

                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OrderObservable.instance.addObserver(this)
        setContentView(R.layout.activity_order_detail)
        processIntent()
        mMapView.onCreate(savedInstanceState)
        map = mMapView.map
    }

    private fun processIntent() {
        if (intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId")
            val type = intent.getStringExtra("type")
            val index = getIndex(type)
            (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
            (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)
        }
    }

    fun getIndex(type: String): Int =
            when (type) {
                OrderObservable.ORDERTYPE_UNPAYMENT -> -1
//                typeInfo = "未支付";
                OrderObservable.ORDERTYPE_SUBMIT -> 0
//                typeInfo = "已提交订单";
                OrderObservable.ORDERTYPE_RECEIVEORDER -> 1
//                typeInfo = "商家接单";
                OrderObservable.ORDERTYPE_DISTRIBUTION,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL,
                OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE -> 2
//                typeInfo = "配送中";
                OrderObservable.ORDERTYPE_SERVED -> 3
//                typeInfo = "已送达";
                OrderObservable.ORDERTYPE_CANCELLEDORDER -> -1
//                typeInfo = "取消的订单";
                else -> -1
            }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }
}