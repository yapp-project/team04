package yapp14th.co.kr.myplant.base.example.nouse.view;

import android.os.Bundle;

import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseActivity;

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreate
// TODO 필수 선언 4. 툴바 설정

public class NouseActivity extends BaseActivity {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_nouse;
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정 (사용안할 시 반드시 false 처리할 것))
    @Override
    protected boolean getIsUseDataBinding() {
        return false;
    }

    // TODO 필수 선언 3 (onCreate)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 필수 선언 4 (툴바 설정)
        setToolbar(R.color.transparent);
    }
}
