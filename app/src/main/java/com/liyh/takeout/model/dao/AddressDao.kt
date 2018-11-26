package com.liyh.takeout.model.dao

import android.content.Context
import com.j256.ormlite.dao.Dao
import com.liyh.takeout.model.bean.AddressInfo
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 06 日
 * @time  17 时 54 分
 * @descrip :
 */
class AddressDao(val context: Context) : AnkoLogger {

    var addressDao: Dao<AddressInfo, Int>
    var dbHelper:TakeoutDbHelper?
    init {
        dbHelper = TakeoutDbHelper(context)
        addressDao = dbHelper!!.getDao(AddressInfo::class.java)
    }

    fun addAddress(addressInfo: AddressInfo) {
        try {
            addressDao.create(addressInfo)
        } catch (e: Exception) {
            error { e.localizedMessage }
        }
    }

    fun deleteAddress(addressInfo: AddressInfo) {
        try {
            addressDao.delete(addressInfo)
        } catch (e: Exception) {
            error { e.localizedMessage }
        }
    }

    fun updateAddress(addressInfo: AddressInfo) {
        try {
            addressDao.update(addressInfo)
        } catch (e: Exception) {
            error { e.localizedMessage }
        }
    }

    fun queryAddress(): List<AddressInfo> {
        try {
            return addressDao.queryForAll()
        } catch (e: Exception) {
            error { e.localizedMessage }
            return mutableListOf()
        }
    }

    fun onDestory(){
        addressDao?.closeLastIterator()
        dbHelper?.close()
        dbHelper = null
    }
}