package com.liyh.takeout.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.heima.takeout.model.beans.Seller
import com.liyh.takeout.model.bean.Categorie
import com.liyh.takeout.model.bean.HomeInfo
import com.liyh.takeout.model.bean.Promotion
import com.liyh.takeout.ui.fragment.HomeFragment
import org.jetbrains.anko.info
import org.json.JSONObject

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 25 日
 * @time  17 时 08 分
 * @descrip :
 */
class HomeFragmentPresenter(val homeFragment: HomeFragment) : BaseNetPresenter<HomeInfo>() {
    override fun onFailed(msg: String) {
        uiThread { homeFragment.onFailed(msg) }

    }

    override fun onSuccess(data: HomeInfo) {
        if (data.promotionList.isNotEmpty()) {
            imgs.clear()
            data.promotionList.forEach {
                imgs[it.info] = it.pic
                uiThread { homeFragment.onSuccess() }

            }
        } else {
            uiThread { homeFragment.onFailed("推荐数据为空") }
        }
        if (data.nearbySellerList.isNotEmpty() || data.otherSellerList.isNotEmpty()) {
            sellers.clear()
            sellers.addAll(data.nearbySellerList)
            sellers.addAll(data.otherSellerList)
            homeFragment.onSuccess()
        } else {
            homeFragment.onFailed("商家数据为空")
        }
    }

    val sellers = arrayListOf<Seller>()
    val imgs = hashMapOf<String, String>()


    fun getSellerInfo() {
        val observable = takeOutService.getHomeInfo()
        request(observable)
    }

    fun parseJson(json: String) {
        val gson = Gson()
        val jsonObject = JSONObject(json)
        val categorie = jsonObject.getString("categorieList")
        info { categorie }
        val categorieList = gson.fromJson<List<Categorie>>(categorie, object : TypeToken<List<Categorie>>() {}.type)
//        if (categorieList.isNotEmpty()) {
//            imgs.clear()
//            categorieList.forEach {
//                imgs[it.name] = it.pic
//                homeFragment.onSuccess()
//            }
//        } else {
//            homeFragment.onFailed()
//        }
        val promotion = jsonObject.getString("promotionList")
        info { promotion }
        val promotionList = gson.fromJson<List<Promotion>>(promotion, object : TypeToken<List<Promotion>>() {}.type)
        if (promotionList.isNotEmpty()) {
            imgs.clear()
            promotionList.forEach {
                imgs[it.info] = it.pic
                homeFragment.onSuccess()
            }
        } else {
//            homeFragment.onFailed()
        }
        val nearby = jsonObject.getString("nearbySellerList")
        info { nearby }
        val nearbyList = gson.fromJson<List<Seller>>(nearby, object : TypeToken<List<Seller>>() {}.type)
        val other = jsonObject.getString("otherSellerList")
        info { other }
        val otherList = gson.fromJson<List<Seller>>(other, object : TypeToken<List<Seller>>() {}.type)
        if (nearbyList.isNotEmpty() || otherList.isNotEmpty()) {
            sellers.clear()
            sellers.addAll(nearbyList)
            sellers.addAll(otherList)
            homeFragment.onSuccess()
        } else {
//            homeFragment.onFailed()
        }

    }
}