package com.liyh.takeout.model.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  16 时 26 分
 * @descrip :
 */
@DatabaseTable(tableName = "t_user")
class UserInfo {

    //使用制定id
    @DatabaseField(id = true)
//        @SerializedName("id")
    val id: Int = 0
    @DatabaseField(columnName = "name")
//        @SerializedName("name")
    val name: String = ""
    @DatabaseField(columnName = "balance")
//        @SerializedName("balance")
    val balance: Float = 0f
    @DatabaseField(columnName = "discount")
//        @SerializedName("discount")
    val discount: Int = 0
    @DatabaseField(columnName = "integral")
//        @SerializedName("integral")
    val integral: Int = 0
    @DatabaseField(columnName = "phone")
//        @SerializedName("phone")
    val phone: String = ""
}
