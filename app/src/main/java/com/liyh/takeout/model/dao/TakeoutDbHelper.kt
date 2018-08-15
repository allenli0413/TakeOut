package com.liyh.takeout.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.liyh.takeout.model.bean.UserInfo

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 06 日
 * @time  17 时 52 分
 * @descrip :
 */
class TakeoutDbHelper(context: Context?) : OrmLiteSqliteOpenHelper(context, "takeout.db", null, 1) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, UserInfo::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
    }
}