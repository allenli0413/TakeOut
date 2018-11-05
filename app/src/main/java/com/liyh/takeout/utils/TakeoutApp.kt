package com.liyh.takeout.utils

import cn.jpush.android.api.JPushInterface
import com.liyh.takeout.model.bean.CacheSelectedInfo
import com.liyh.takeout.model.bean.UserInfo
import com.mob.MobApplication
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 30 日
 * @time  16 时 53 分
 * @descrip :
 */
class TakeoutApp : MobApplication() {
    //点餐缓存信息，CopyOnWriteArrayList线程安全的集合
    val infos: CopyOnWriteArrayList<CacheSelectedInfo> = CopyOnWriteArrayList()

    companion object {
        var userInfo: UserInfo? = null
        lateinit var instance: TakeoutApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }

    fun queryCacheSelectedInfoByGoodsId(goodsId: Int): Int {
        var count = 0
        infos.forEach { info ->
            if (info.goodsId == goodsId) {
                count = info.count
            }
        }
        return count
    }

    fun queryCacheSelectedInfoByTypeId(typeId: Int): Int {
        var count = 0
        infos.forEach {
            if (it.typeId == typeId) {
                count += it.count
            }
        }
        return count
    }

    fun queryCacheSelectedInfoBySellerId(sellerId: Int): Int {
        var count = 0
        infos.forEach {
            if (it.sellerId == sellerId) {
                count += it.count
            }
        }
        return count;
    }

    fun addCacheSelectedInfo(info: CacheSelectedInfo) {
        infos.add(info)
    }

    fun clearCacheSelectedInfo(sellerId: Int) {
        infos.forEachIndexed { index, it ->
            if (it.sellerId == sellerId) {
                infos.remove(it)
            }
        }

    }

    fun deleteCacheSelectedInfo(goodsId: Int) {
        infos.forEachIndexed { index, it ->

            if (it.goodsId == goodsId) {
                infos.remove(it)
            }
        }
    }

    fun updateCacheSelectedInfo(goodsId: Int, operation: Int) {
        infos.forEach {
            if (it.goodsId == goodsId) {
                when (operation) {
                    Constants.ADD -> it.count++
                    Constants.MINUS -> it.count--
                }
            }
        }
    }
}