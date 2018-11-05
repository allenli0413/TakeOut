package com.liyh.takeout.presenter

import android.os.Handler
import android.os.Looper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.liyh.takeout.model.bean.ResponseInfo
import com.liyh.takeout.model.net.TakeOutService
import com.mayigeek.frame.http.HttpLoggingInterceptor
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  14 时 46 分
 * @descrip :
 */
abstract class BaseNetPresenter<T> : AnkoLogger {

    val observer = object : Observer<ResponseInfo<T>> {
        override fun onNext(t: ResponseInfo<T>) {
            if (t == null) {
                onFailed("数据解析错误")
            } else {
                if (t.code == "0") {
                    val t = t.data
                    if (t != null) {
                        onSuccess(t)
                    } else {
                        onFailed("data数据为空")
                    }
                } else if (t.code == "-1") {

                }

            }
        }

        override fun onError(e: Throwable) {
            onFailed("请求失败，错误信息：${e.localizedMessage}")

        }

        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
        }

    }

    val takeOutService: TakeOutService
//    val callback = object : Callback<ResponseInfo<T>> {
//        override fun onFailure(call: Call<ResponseInfo<T>>?, t: Throwable?) {
////            error { "没有连接到服务器" }
//            onFailed("没有连接到服务器(${t?.message})")
//        }
//
//        override fun onResponse(call: Call<ResponseInfo<T>>?, response: Response<ResponseInfo<T>>?) {
//            if (response == null) {
////                error { "服务器没有成功返回数据" }
//                onFailed("服务器没有成功返回数据")
//            } else {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        if (it.code == "0") {
//                            val t = it.data
//                            if (t != null) {
//                                onSuccess(t)
//                            } else {
//                                onFailed("data数据为空")
//                            }
//                        } else if (it.code == "-1") {
//
//                        }
//                    }
//
//                } else {
//                    onFailed("服务器代码错误")
//                }
//            }
//        }
//    }

    abstract fun onFailed(msg: String)

    abstract fun onSuccess(data: T)
    val okHttpClient = OkHttpClient.Builder().build()
    val loggerInterceptor by lazy { HttpLoggingInterceptor() }

    init {
        val newBuilder = okHttpClient.newBuilder()
        newBuilder.addInterceptor(loggerInterceptor)
        val retrofit = Retrofit.Builder()
                .client(newBuilder.build())
                .baseUrl("http://10.200.0.205:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        takeOutService = retrofit.create(TakeOutService::class.java)
    }

    //    abstract fun parseJson(json: String)
    companion object {
        val handler by lazy { Handler(Looper.getMainLooper()) }
    }

    fun uiThread(f: () -> Unit) {
        handler.post { f() }
    }

    fun request(observable: Observable<ResponseInfo<T>>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
}