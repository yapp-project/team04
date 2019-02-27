package yapp14th.co.kr.myplant

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import yapp14th.co.kr.myplant.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    var input = "abcd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding?.activity = this
    }

    fun onButtonClick(abcd: String) {
        Toast.makeText(this, "Login Button Click $abcd", Toast.LENGTH_SHORT).show()
    }
}