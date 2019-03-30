package yapp14th.co.kr.myplant.base.example.nouse.view

import android.os.Bundle
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.utils.ActivityUtil

class NouseKotlinActivity : BaseActivity() {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.activity_nouse

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정 (사용안할 시 반드시 false 처리할 것))
    override fun getIsUseDataBinding() = false

    // TODO 필수 선언 3 (onCreate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO 필수 선언 4 (툴바 설정)
        setToolbar(R.color.transparent)

        // 선택 선언 4 (프레그먼트 사용 시)
        ActivityUtil.addFragmentToActivity(supportFragmentManager, NouseKotlinFragment.getInstance(), R.id.fl_container)
    }
}