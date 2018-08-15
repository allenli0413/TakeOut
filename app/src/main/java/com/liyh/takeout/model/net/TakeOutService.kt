package com.liyh.takeout.model.net

import com.liyh.takeout.model.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author  Yahri Lee
 * @date  2018 年 07 月 25 日
 * @time  17 时 19 分
 * @descrip :
 */
interface TakeOutService {

    @GET("home")
    fun getHomeInfo(): Observable<ResponseInfo<HomeInfo>>

    @GET("login")
    fun getLoginInfo(@Query("phone") phone: String): Observable<ResponseInfo<UserInfo>>

    @GET("order")
    fun getOrderInfo(@Query("id") userId: String): Observable<ResponseInfo<List<OrderInfo>>>

    @GET("business")
    fun getGoodsInfo(@Query("sellerId") userId: String): Observable<ResponseInfo<List<GoodsTypeInfo>>>
}