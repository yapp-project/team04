package yapp14th.co.kr.myplant.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

import yapp14th.co.kr.myplant.components.ColorPickerDialog;

public class SwipeableViewPager extends ViewPager {
    public SwipeableViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && (v instanceof SeekBar || v instanceof ColorPickerDialog.ColorPickerView)) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
