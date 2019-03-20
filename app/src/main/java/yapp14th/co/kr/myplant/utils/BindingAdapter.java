package yapp14th.co.kr.myplant.utils;

import android.view.View;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }
}
