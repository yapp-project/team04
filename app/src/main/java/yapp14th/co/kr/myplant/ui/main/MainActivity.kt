package yapp14th.co.kr.myplant.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.ui.main.tab1_home.HomeFragment
import yapp14th.co.kr.myplant.ui.main.tab2_statistic.StatisticFragment
import yapp14th.co.kr.myplant.ui.main.tab3_mypage.MypageFragment
import yapp14th.co.kr.myplant.utils.ActivityUtil
import yapp14th.co.kr.myplant.utils.alarm.AlarmUtil
import yapp14th.co.kr.myplant.utils.alarm.HomeWatcher
import yapp14th.co.kr.myplant.utils.alarm.OnHomePressedListener


class MainActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getIsUseDataBinding() = false
    private var mHomeWatcher: HomeWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        ActivityUtil.addFragmentToActivity(supportFragmentManager, HomeFragment.getInstance(), yapp14th.co.kr.myplant.R.id.vp_container)

        mHomeWatcher = HomeWatcher(this).apply {
            setOnHomePressedListener(object : OnHomePressedListener {
                override fun onHomePressed() {
                    AlarmUtil.instance.startEightPMAlarm(this@MainActivity)
                }

                override fun onHomeLongPressed() {

                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        mHomeWatcher?.startWatch()
        AlarmUtil.instance.clearAlarm(this)
    }

    override fun onPause() {
        super.onPause()

        mHomeWatcher?.stopWatch()
    }

    override fun onDestroy() {
        super.onDestroy()

        AlarmUtil.instance.startEightPMAlarm(this)
    }
}