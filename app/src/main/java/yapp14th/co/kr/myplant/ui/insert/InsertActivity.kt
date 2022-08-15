package yapp14th.co.kr.myplant.ui.insert

import android.app.Activity
import yapp14th.co.kr.myplant.R
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_insert.*
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.MaterialColorAdapter
import yapp14th.co.kr.myplant.databinding.ActivityInsertBinding
import yapp14th.co.kr.myplant.ui.main.MainActivity
import yapp14th.co.kr.myplant.utils.*
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.Toast

class InsertActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_insert
    override fun getIsUseDataBinding() = true
    lateinit var binding: ActivityInsertBinding
    private var comingFromAppStart = false

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

        val year = intent.getIntExtra("year", getCurrentYear())
        val month = intent.getIntExtra("month", getCurrentRefinedMonth())
        val day = intent.getIntExtra("day", getCurrentDate())

        // -1은 나올수 없는 감정 (ViewModel에서는 해당 값을 0으로 치환 진행)
        val emotionType = intent.getIntExtra("emotionType", -1)
        val comment = intent.getStringExtra("comment") ?: ""
        comingFromAppStart = intent.getIntExtra("year", -1) < 0

        insertVM.setDate(year, month, day, emotionType, comment)
        var dataSize = insertVM.setTargetDate(year, month, day)

        // emotionType = -1 : 처음 앱 켜서 옴 | emotionType = 0 : 추가시키기 위해 옴 | emotionType = 1~8 : 수정하기 위해 옴
        if (emotionType == -1 && dataSize != 0) {
            startActivity(Intent(this@InsertActivity, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "원을 클릭하거나 휠을 돌려 색을 선택할 수 있습니다.", Toast.LENGTH_LONG).show()

            wv_color.adapter = MaterialColorAdapter(insertVM.emotionsColor)

            // 초기 wheel listener 설정
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

                setCenterColor(img_color, color)

                insertVM.currentEmotion = position + 1
                tv_emotion.text = insertVM.emotionsTitle[position]
            }

            wv_color.setOnWheelItemClickListener { parent, position, isSelected ->
                val color = Color.parseColor(insertVM.emotionsColor[position])
//                val alpha = Color.alpha(color)
//                val blue = Color.blue(color)
//                val green = Color.green(color)
//                val red = Color.red(color)
//                Log.d("color$position : ", "$alpha $red $green $blue")
//
//                img_color.setColorFilter(Color.argb(alpha, red, green, blue))

                setCenterColor(img_color, color)

                insertVM.currentEmotion = position + 1
                tv_emotion.text = insertVM.emotionsTitle[position]
                wv_color.setSelected(insertVM.currentEmotion - 1)
            }

            // 초기 wheel init 값 설정
            wv_color.setSelected(insertVM.currentEmotion - 1)
            setCenterColor(img_color, Color.parseColor(insertVM.emotionsColor[insertVM.currentEmotion - 1]))

            btn_completed.setOnClickListener {
                if (dataSize == 0 || emotionType == 0) {
                    insertDate(
                            year = year.toShort(),
                            month = month.toShort(),
                            day = day.toShort(),
                            emotionType = insertVM.currentEmotion.toShort(),
                            comment = et_input.text.toString())

                    if (comingFromAppStart)
                        startActivity(Intent(this@InsertActivity, MainActivity::class.java))
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    updateDate(
                            date = insertVM.date,
                            year = year.toShort(),
                            month = month.toShort(),
                            day = day.toShort(),
                            emotionType = insertVM.currentEmotion.toShort(),
                            comment = et_input.text.toString())
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }

            btn_close.setOnClickListener {
                if (comingFromAppStart)
                    startActivity(Intent(this@InsertActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setCenterColor(img_color: CircleImageView, color: Int) {
        // val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(color, Color.parseColor("#ffffff")))
        // gd.cornerRadius = 0f

        // img_color.setImageDrawable(gd)
        img_color.setColorFilter(color, PorterDuff.Mode.SRC)
    }
}