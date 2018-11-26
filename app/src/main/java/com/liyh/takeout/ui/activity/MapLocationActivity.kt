package com.liyh.takeout.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.liyh.takeout.R
import com.liyh.takeout.ui.adapter.AroundListAdapter
import kotlinx.android.synthetic.main.activity_map_location.*
import org.jetbrains.anko.toast

/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 23 日
 * @time  11 时 30 分
 * @descrip :
 */
class MapLocationActivity : AppCompatActivity(), AMapLocationListener, PoiSearch.OnPoiSearchListener {

    val list: ArrayList<PoiItem> = arrayListOf()
    override fun onPoiItemSearched(poiItem: PoiItem?, resultCode: Int) {
    }

    override fun onPoiSearched(poiResult: PoiResult?, resultCode: Int) {
        if (resultCode == 1000 && poiResult != null) {
            val poiItems = poiResult.pois
            toast("一共找到${poiItems.size}个兴趣点")
            list.clear()
            list.addAll(poiItems)
            rv_around.adapter.notifyDataSetChanged()
        }
    }

    override fun onLocationChanged(location: AMapLocation?) {
        location?.let {
            if (it.address.isEmpty()) {
                return
            }
            toast(it.address)
            map.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(location.latitude, location.longitude)))
            map.moveCamera(CameraUpdateFactory.zoomTo(16f))
            doSearchQuery(location)
            locationClient.stopLocation()
        }
    }

    private fun doSearchQuery(location: AMapLocation) {
        val query = PoiSearch.Query("", "", location.city)
        query.pageSize = 30
        query.pageNum = 1
        val poiSearch = PoiSearch(this, query)
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(location.latitude, location.longitude), 500)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()
    }

    lateinit var locationClient: AMapLocationClient
    lateinit var map: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_location)
        mMapView.onCreate(savedInstanceState)
        map = mMapView.map
        var myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000)
        myLocationStyle.strokeWidth(200f)
        map.setMyLocationStyle(myLocationStyle)
        map.isMyLocationEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
        rv_around.layoutManager = LinearLayoutManager(this)
        rv_around.adapter = AroundListAdapter(this, list)

    }

    private fun initLocation() {
        locationClient = AMapLocationClient(this)
        locationClient.setLocationListener(this)
        val aMapLocationClientOption = AMapLocationClientOption()
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        locationClient.setLocationOption(aMapLocationClientOption)
        locationClient.startLocation()
    }

    val REQUESTCODE = 10

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUESTCODE)
        } else {
            initLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initLocation()
        } else {
            finish()
            toast("权限被拒绝，无法使用定位服务")
        }
    }

}