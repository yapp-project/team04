package yapp14th.co.kr.myplant.ui.main

import android.content.Intent
import android.os.Bundle
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.ui.intro.IntroColorPickActivity

class MainActivity: BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getIsUseDataBinding() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(R.color.transparent)

        var intent = Intent(this,IntroColorPickActivity::class.java)
        startActivity(intent)

    }
}