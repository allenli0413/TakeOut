package com.liyh.takeout.model.bean

class ResponseInfo<T> {
    //服务器开发者定义的数据结构
    var code: String = ""
    var data: T? = null
}
