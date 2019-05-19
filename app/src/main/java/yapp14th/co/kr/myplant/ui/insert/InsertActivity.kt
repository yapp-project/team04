package yapp14th.co.kr.myplant.ui.insert

import yapp14th.co.kr.myplant.R
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_insert.*
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.MaterialColorAdapter
import yapp14th.co.kr.myplant.databinding.ActivityInsertBinding
import yapp14th.co.kr.myplant.ui.main.MainActivity
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.utils.*

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

        insertVM.year = intent.getIntExtra("year", getCurrentYear())
        insertVM.month = intent.getIntExtra("month", getCurrentRefinedMonth())
        insertVM.day = intent.getIntExtra("day", getCurrentDate())

        insertVM.date = getRealmInstance.where(CDay::class.java)
                .equalTo("year", insertVM.year)
                .equalTo("month", insertVM.month)
                .equalTo("day", insertVM.day).findAll()

        if (insertVM.date?.size != 0) {
            startActivity(Intent(this@InsertActivity, MainActivity::class.java))
            finish()
        } else {
            wv_color.adapter = MaterialColorAdapter(insertVM.emotionsColor)

            wv_color.setOnWheelItemSelectedListener { parent, itemDrawable, position ->
                //the adapter position that is closest to the selection angle and it's drawable

                val color = Color.parseColor(insertVM.emotionsColor[position])
//                val alpha = Color.alpha(color)
//                val blue = Color.blue(color)
//                val green = Color.green(color)
//                val red = Color.red(color)
//                Log.d("color$position : ", "$alpha $red $green $blue")
//
//                img_color.setColorFilter(Color.argb(alpha, red, green, blue))

                img_color.setColorFilter(color, PorterDuff.Mode.SRC)

                currentPosition = position + 1
                tv_emotion.text = insertVM.emotionsTitle[position]
                // insertVM.emotionsColor.get(position)
            }

            btn_completed.setOnClickListener {
                val realm = getRealmInstance
                realm.beginTransaction()

                var maxId = realm.where(CDay::class.java).max("id")
                var nextId = if (maxId == null) 1 else (maxId.toInt() + 1)

                realm.insert(CDay(
                        nextId.toLong(),
                        insertVM.year.toShort(),
                        insertVM.month.toShort(),
                        insertVM.day.toShort(),
                        currentPosition.toShort(),
                        et_input.text.toString()))

                realm.commitTransaction()
                // realm.where(CDay::class.java).equalTo("owner", SharedPreferenceUtil.getStringData(SharedPreferenceUtil.email)).equalTo(fieldName, filePath).findAll().deleteAllFromRealm()

                startActivity(Intent(this@InsertActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}