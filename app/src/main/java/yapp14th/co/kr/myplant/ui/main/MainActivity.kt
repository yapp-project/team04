package yapp14th.co.kr.myplant.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.ui.main.tab1_home.HomeFragment
import yapp14th.co.kr.myplant.ui.main.tab2_statistic.StatisticFragment
import yapp14th.co.kr.myplant.ui.main.tab3_mypage.MypageFragment
import yapp14th.co.kr.myplant.utils.ActivityUtil
import yapp14th.co.kr.myplant.ui.intro.IntroColorPickActivity
import android.view.View

class MainActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getIsUseDataBinding() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(R.color.transparent)

        toolbar.visibility = View.GONE

        main_tab.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_home -> {
                        ActivityUtil.addFragmentToActivity(supportFragmentManager, HomeFragment.getInstance(), R.id.vp_container)
                        return true
                    }
                    R.id.action_static -> {
                        ActivityUtil.addFragmentToActivity(supportFragmentManager, StatisticFragment(), R.id.vp_container)
                        return true
                    }
                    R.id.action_mypage -> {
                        ActivityUtil.addFragmentToActivity(supportFragmentManager, MypageFragment.getInstance(), R.id.vp_container)
                        return true
                    }
                }
                return false
            }
        })

        ActivityUtil.addFragmentToActivity(supportFragmentManager, HomeFragment.getInstance(), R.id.vp_container)
    }
}