package yapp14th.co.kr.myplant.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.ui.main.tab1_home.HomeFragment


class MainActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main
    override fun getIsUseDataBinding() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(R.color.transparent)

        // 이부분에 fragment 추가하면 됨
        var adapter = MainPagerAdapter(supportFragmentManager)
        adapter.init(arrayListOf<Fragment>(HomeFragment(), HomeFragment(), HomeFragment()))
        vp_container.adapter = adapter

        main_tab.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_home -> {
                        vp_container.currentItem = 0
                        return true
                    }
                    R.id.action_static -> {
                        vp_container.currentItem = 1
                        return true
                    }
                    R.id.action_mypage -> {
                        vp_container.currentItem = 2
                        return true
                    }
                }
                return false
            }
        })

        vp_container.currentItem = 0
    }
}