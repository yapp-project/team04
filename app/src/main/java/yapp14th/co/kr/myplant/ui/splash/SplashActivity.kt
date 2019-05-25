package yapp14th.co.kr.myplant.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.databinding.ActivitySplashBinding
import yapp14th.co.kr.myplant.ui.main.MainActivity

class SplashActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_splash
    override fun getIsUseDataBinding() = true
    private var binding: ActivitySplashBinding? = null

    private val splashVM : SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding?.let { binding ->
            binding.splashVM = splashVM
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}