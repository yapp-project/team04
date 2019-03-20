package yapp14th.co.kr.myplant

import android.widget.Toast
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_login
    override fun getIsUseDataBinding() = false

    fun onButtonClick(abcd: String) {
        Toast.makeText(this, "Login Button Click $abcd", Toast.LENGTH_SHORT).show()
    }
}