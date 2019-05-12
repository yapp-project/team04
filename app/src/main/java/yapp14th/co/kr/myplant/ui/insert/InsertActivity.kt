package yapp14th.co.kr.myplant.ui.insert

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lukedeighton.wheelview.WheelView
import com.lukedeighton.wheelview.adapter.WheelAdapter
import kotlinx.android.synthetic.main.activity_insert.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.MaterialColorAdapter
import yapp14th.co.kr.myplant.databinding.ActivityInsertBinding
import yapp14th.co.kr.myplant.ui.main.MainActivity
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil

class InsertActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_insert
    override fun getIsUseDataBinding() = true
    lateinit var binding: ActivityInsertBinding

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

        wv_color.adapter = MaterialColorAdapter(insertVM.emotions)

        wv_color.setOnWheelItemSelectedListener { parent, itemDrawable, position ->
            //the adapter position that is closest to the selection angle and it's drawable
            img_color.setImageDrawable(itemDrawable)
            // insertVM.emotions.get(position)
        }

        btn_completed.setOnClickListener {
            startActivity(Intent(this@InsertActivity, MainActivity::class.java))
            finish()
        }
    }
}