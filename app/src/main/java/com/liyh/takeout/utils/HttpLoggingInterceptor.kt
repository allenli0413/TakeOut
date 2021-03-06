package com.mayigeek.frame.http

import okhttp3.*
import okio.Buffer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * [application interceptor][OkHttpClient.interceptors] or as a [ ][OkHttpClient.networkInterceptors].
 *
 * The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
open class HttpLoggingInterceptor constructor() : Interceptor, AnkoLogger {
    private val UTF8 = Charset.forName("UTF-8")


//    var requestHook: RequestHook? = null
//    var responseHook: ResponseHook? = null
//
//    var level: LogLevel = LogLevel.NONE
//        private set

    /**
     * Change the logLevel at which this interceptor logs.
     */
//    fun setLevel(level: LogLevel?): HttpLoggingInterceptor {
//        if (level == null)
//            throw NullPointerException("logLevel == null. Use LogLevel.NONE instead.")
//        this.level = level
//        return this
//    }

    override fun intercept(chain: Interceptor.Chain): Response {
//        val level = this.level

        var request = chain.request()
        val requestBuilder = request.newBuilder()
//        requestHook?.onHook(requestBuilder)
        request = requestBuilder.build()
//        if (level === LogLevel.NONE) {
//            return chain.proceed(request)
//        }

        val logBody = true
        val logHeaders = logBody || true

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }

        info { requestStartMessage }
//        logger!!.log(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null) {
                    info { "Content-Type: " + requestBody.contentType() }
                }
                if (requestBody.contentLength() != -1L) {
                    info { "Content-Length: " + requestBody.contentLength() }
                }
            }

            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                    info { name + ": " + headers.value(i) }
                }
                i++
            }

            if (!logBody || !hasRequestBody) {
                info { "--> END " + request.method() }
            } else if (bodyEncoded(request.headers())) {
                info { "--> END " + request.method() + " (encoded body omitted)" }
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)

                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                info { "" }
                info { buffer.readString(charset) }
                info { "--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)" }

            }
        }

        val startNs = System.nanoTime()
        var response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val bodySize = if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
        info {
            "<-- " + response.code() + ' ' + response.message() + ' ' + response.request().url() + " (" + tookMs + "ms" + (if (!logHeaders)
                ", $bodySize body"
            else
                "") + ')'
        }

        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                info { headers.name(i) + ": " + headers.value(i) }
                i++
            }

            if (!logBody || responseBody == null) {
                info { "<-- END HTTP" }
            } else if (bodyEncoded(response.headers())) {
                info { "<-- END HTTP (encoded body omitted)" }
            } else {
                val source = responseBody.source()
                source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source?.buffer()

                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        info { "" }
                        info { "Couldn't decode the response body; charset is likely malformed." }
                        info { "<-- END HTTP" }
                        return response
                    }

                }

                if (contentLength != 0L) {
                    info { "" }
                    info { buffer?.clone()?.readString(charset) ?: "responseBody is empty!" }
                }
                info { "<-- END HTTP (" + buffer?.size() + "-byte body)" }
            }
        }
        val responseBuilder = response.newBuilder()
//        responseHook?.onHook(responseBuilder)
        response = responseBuilder.build()
        return response
    }


    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", true)
    }

//    fun setLogger(logger: Logger) {
//        this.logger = logger
//    }
}
