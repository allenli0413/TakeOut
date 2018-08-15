package com.liyh.takeout.utils

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 13 日
 * @time  11 时 33 分
 * @descrip :
 */
class LoggerInterceptor: Interceptor, AnkoLogger {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var response: Response? = null
        chain?.run {
            var request = chain.request()
            val newBuilder = request.newBuilder()
            request = newBuilder.build()
            val body = request.body()
            val connection = chain.connection()
            val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
            var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
            info { requestStartMessage }
        }
        response = chain?.proceed(chain?.request())
        return response
    }
}