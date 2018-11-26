package com.liyh.takeout.model.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  16 时 26 分
 * @descrip :
 */
@DatabaseTable(tableName = "t_address")
class AddressInfo() : Serializable {

    //使用制定id
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField(columnName = "userName")
    var userName: String = ""
    @DatabaseField(columnName = "sex")
    var sex: String = "女士"
    @DatabaseField(columnName = "phone")
    var phone: String = ""
    @DatabaseField(columnName = "phoneOther")
    var phoneOther: String = ""
    @DatabaseField(columnName = "address")
    var address: String = ""
    @DatabaseField(columnName = "addressDetail")
    var addressDetail: String = ""
    @DatabaseField(columnName = "tag")
    var tag: String = ""
    @DatabaseField(columnName = "userId")
    var userId: String = "38"

    constructor(userName: String, sex: String, phone: String, phoneOther: String, address: String, addressDetail: String, tag: String, userId: String) : this() {
        this.userName = userName
        this.sex = sex
        this.phone = phone
        this.phoneOther = phoneOther
        this.address = address
        this.addressDetail = addressDetail
        this.tag = tag
        this.userId = userId
    }

}
