package com.liyh.takeout.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.liyh.takeout.R
import com.liyh.takeout.extentions.getContent
import com.liyh.takeout.extentions.isNavigationBarShow
import com.liyh.takeout.model.bean.AddressInfo
import com.liyh.takeout.model.dao.AddressDao
import kotlinx.android.synthetic.main.activity_add_edit_receipt_address.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


/**
 * @author  Yahri Lee
 * @date  2018 年 11 月 06 日
 * @time  15 时 49 分
 * @descrip :
 */
class AddOrEditAddressActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var addressInfo: AddressInfo
    var isEdit = false
    var isShowOtherPhone = false
    lateinit var addressDao: AddressDao
    val REQUEST_CODE = 1002

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_back -> finish()
            R.id.ib_add_phone_other -> {
                rl_phone_other.visibility = if (isShowOtherPhone) View.GONE else View.VISIBLE
                isShowOtherPhone = !isShowOtherPhone
            }
            R.id.ib_select_label -> showTagDialog()
            R.id.btn_ok -> if (checkReceiptAddressInfo()) submit()
            R.id.ib_delete_phone -> et_phone.setText("")
            R.id.ib_delete_phone_other -> et_phone_other.setText("")
            R.id.ib_delete -> addressDao.deleteAddress(addressInfo)
            R.id.btn_location_address -> {
//                val intent = Intent(this, MapLocationActivity::class.java)
//                startActivityForResult(intent, REQUEST_CODE)
                startActivityForResult<MapLocationActivity>(REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val title = data.getStringExtra("title")
            val address = data.getStringExtra("address")
            et_receipt_address.setText(title)
            et_detail_address.setText(address)
        }
    }

    private fun submit() {
        var userName = et_name.getContent()
        var sex = if (rb_man.isChecked) "先生" else "女士"
        var phone = et_phone.getContent()
        var phoneOther = et_phone_other.getContent()
        var address = et_receipt_address.getContent()
        var addressDetail = et_detail_address.getContent()
        var tag = tv_label.getContent()

        addressInfo = AddressInfo(userName, sex, phone, phoneOther, address, addressDetail, tag, "38")
        if (isEdit) {
            addressDao.updateAddress(addressInfo)
            toast("更新成功")
        } else {
            addressDao.addAddress(addressInfo)
            toast("新增成功")
        }
        finish()
    }

    val tags = arrayOf("无", "家", "公司", "其他")
    //    val checkItems = booleanArrayOf(true, false, false, false)
    val tagColors = arrayOf("#FF3399", "#FF9933", "#99FF33", "#33FF99")

    private fun showTagDialog() {
        AlertDialog.Builder(this)
                .setTitle("选择标签")
                .setItems(tags, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        tv_label.text = tags[which]
                        tv_label.setBackgroundColor(Color.parseColor(tagColors[which]))
                    }

                }).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_receipt_address)
        if (isNavigationBarShow()) {
            ll_container.setPadding(0, 0, 0, dip(46))
        }
        addressDao = AddressDao(this)
        initView()
    }

    private fun initView() {
        ib_back.setOnClickListener(this)
        ib_add_phone_other.setOnClickListener(this)
        ib_select_label.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        ib_delete.setOnClickListener(this)
        ib_delete_phone.setOnClickListener(this)
        ib_delete_phone_other.setOnClickListener(this)
        btn_location_address.setOnClickListener(this)
        et_phone.addTextChangedListener(MyTextWatch(ib_delete_phone))
        et_phone_other.addTextChangedListener(MyTextWatch(ib_delete_phone_other))

        if (intent.hasExtra("address")) {
            isEdit = true
            addressInfo = intent.getSerializableExtra("address") as AddressInfo
            et_name.setText(addressInfo.userName)
            if ("先生".equals(addressInfo.sex)) {
                rb_man.isChecked = true
            } else {
                rb_women.isChecked = true
            }
            et_phone.setText(addressInfo.phone)
            et_phone_other.setText(addressInfo.phoneOther)
            isShowOtherPhone = addressInfo.phoneOther.isNotEmpty()
            rl_phone_other.visibility = if (isShowOtherPhone) View.VISIBLE else View.GONE
            et_receipt_address.setText(addressInfo.address)
            et_detail_address.setText(addressInfo.addressDetail)
            tv_label.setText(addressInfo.tag)
            tags.forEachIndexed { index, s ->
                if (s.equals(addressInfo.tag)) {
                    tv_label.setBackgroundColor(Color.parseColor(tagColors[index]))
                }
            }
        } else {
            isEdit = false
        }
        ib_delete.visibility = if (isEdit) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
//        addressDao.onDestory()
    }

    fun checkReceiptAddressInfo(): Boolean {
        var name = et_name.getContent()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        var phone = et_phone.getContent()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        var receiptAddress = et_receipt_address.getContent()
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        var address = et_detail_address.getContent()
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    fun isMobileNO(phone: String): Boolean {
        val telRegex = Regex("[1][358]\\d{9}") //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0〜9的数字，有9位。
        return phone.matches(telRegex);
    }

    inner class MyTextWatch(val ibDelete: ImageButton) : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            ibDelete.visibility = if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }
}