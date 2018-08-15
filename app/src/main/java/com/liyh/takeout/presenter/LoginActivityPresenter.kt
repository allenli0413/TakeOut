package com.liyh.takeout.presenter

import com.j256.ormlite.android.AndroidDatabaseConnection
import com.j256.ormlite.dao.Dao
import com.liyh.takeout.model.bean.UserInfo
import com.liyh.takeout.model.dao.TakeoutDbHelper
import com.liyh.takeout.ui.activity.LoginActivity
import com.liyh.takeout.utils.TakeoutApp
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.sql.Savepoint

/**
 * @author  Yahri Lee
 * @date  2018 年 08 月 01 日
 * @time  14 时 45 分
 * @descrip :
 */
class LoginActivityPresenter(val loginActivity: LoginActivity) : BaseNetPresenter<UserInfo>() {
    override fun onFailed(msg: String) {
        uiThread {
            loginActivity.onFailed(msg)
        }
    }

    override fun onSuccess(data: UserInfo) {
        //存储到内存中
        TakeoutApp.userInfo = data
        //存储到数据库中，需要ormlite事务处理
        var dbConnection: AndroidDatabaseConnection? = null
        var startPoint: Savepoint? = null
        try {
            val dbHelper = TakeoutDbHelper(loginActivity)
            val useDao = dbHelper.getDao<Dao<UserInfo, Int>, UserInfo>(UserInfo::class.java)
//        useDao.create(data)
//        useDao.createOrUpdate(data)
            //区分新老用户
            dbConnection = AndroidDatabaseConnection(dbHelper.writableDatabase, true)
            startPoint = dbConnection.setSavePoint("start")
            dbConnection.isAutoCommit = false //取消自动提交
            val userList = useDao.queryForAll()
            var isOldUser = false
            userList.forEach {
                if (data.id == it.id) {
                    isOldUser = true
                }
            }
            if (isOldUser) {
                info { "老用户" }
                useDao.update(data)
            } else{
                info { "新用户" }
                useDao.create(data)
            }
            dbConnection.commit(startPoint)
            info { "事务正常" }
        } catch (e: Exception) {
            error { "事务异常：${e.localizedMessage}" }
            loginActivity.onFailed(e.localizedMessage)
            dbConnection?.rollback(startPoint)
        }
        uiThread {
            loginActivity.onSuccess(data)
        }
    }

    fun getUserInfo(phone: String) {
        val observable = takeOutService.getLoginInfo(phone)
        request(observable)
    }
}