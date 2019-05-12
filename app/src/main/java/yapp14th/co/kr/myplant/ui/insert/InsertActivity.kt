package yapp14th.co.kr.myplant.ui.insert

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_insert.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.MaterialColorAdapter
import yapp14th.co.kr.myplant.databinding.ActivityInsertBinding
import yapp14th.co.kr.myplant.ui.main.MainActivity
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.utils.getCurrentDate
import yapp14th.co.kr.myplant.utils.getCurrentMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear

class InsertActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_insert
    override fun getIsUseDataBinding() = true
    lateinit var binding: ActivityInsertBinding
    var currentPosition: Int = 0

    private val insertVM: InsertViewModel by lazy {
        ViewModelProviders.of(this).get(InsertViewModel::class.java)
    }

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.insertVM = insertVM
        binding.activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wv_color.adapter = MaterialColorAdapter(insertVM.emotionsColor)

        wv_color.setOnWheelItemSelectedListener { parent, itemDrawable, position ->
            //the adapter position that is closest to the selection angle and it's drawable
            img_color.setImageDrawable(itemDrawable)
            currentPosition = position + 1
            tv_emotion.text = insertVM.emotionsTitle[position]
            // insertVM.emotionsColor.get(position)
        }

        btn_completed.setOnClickListener {
            val realm = realmInstance
            realm.beginTransaction()
            var maxId = realm.where(CDay::class.java).max("id")
            var nextId = if (maxId == null) 1 else (maxId.toInt() + 1)
            realm.insert(CDay(nextId.toLong(), getCurrentYear().toShort(), getCurrentMonth().toShort(), getCurrentDate().toShort(), currentPosition.toShort(), ""))
            // realm.where(CDay::class.java).equalTo("owner", SharedPreferenceUtil.getStringData(SharedPreferenceUtil.email)).equalTo(fieldName, filePath).findAll().deleteAllFromRealm()
            realm.commitTransaction()
            startActivity(Intent(this@InsertActivity, MainActivity::class.java))
            finish()
        }
    }
}