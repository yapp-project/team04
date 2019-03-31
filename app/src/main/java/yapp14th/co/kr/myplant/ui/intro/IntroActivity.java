package yapp14th.co.kr.myplant.ui.intro;

import android.os.Bundle;
import android.util.Log;

import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseActivity;
import yapp14th.co.kr.myplant.components.ColorPickerView;

public class IntroActivity extends BaseActivity implements ColorPickerView.OnColorChangedListener {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_intro;
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

        int width = (int) (MyApplication.convertDpToPixel(90F, getApplicationContext()));
        int height = (int) (MyApplication.convertDpToPixel(90F, getApplicationContext()));
        int radius = (int) (MyApplication.convertDpToPixel(32F, getApplicationContext()));

        ColorPickerView colorPickerView = findViewById(R.id.colorPickerView);

        colorPickerView.init(this, width, height, radius);

        // ...


    }

//    @Override
//    public void colorChanged(int color) {
//        System.out.println("color : $color");
//    }

    @Override
    public void colorChanged(int color, int red, int green, int blue) {
        Log.d("IntroActivity : ", "not implemented"); //To change body of created functions use File | Settings | File Templates.
    }
}