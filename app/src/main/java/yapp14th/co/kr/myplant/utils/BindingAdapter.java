package yapp14th.co.kr.myplant.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.constraintlayout.widget.ConstraintLayout;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("android:visibility")
    public static void setBackground(View view, boolean isVisible) {
        if (isVisible)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    @androidx.databinding.BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    @androidx.databinding.BindingAdapter({"setFlipAnimation", "init"})
    public static void setFlipAnimation(View view, boolean fliped, boolean init) {
        ConstraintLayout ll_front = view.findViewById(R.id.ll_front);
        ConstraintLayout ll_back = view.findViewById(R.id.ll_back);

        // 이 화면이 current가 된지 처음일 경우
        if (!init) {
            // 뒤집어지지 않았다면 (앞 -> 뒤)
            if (fliped) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(ll_front, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(ll_back, "scaleX", 0f, 1f);

                oa1.setDuration(500);
                oa2.setDuration(500);

                ll_front.setVisibility(View.VISIBLE);
                ll_back.setVisibility(View.INVISIBLE);

                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ll_front.setVisibility(View.INVISIBLE);
                        ll_back.setVisibility(View.VISIBLE);
                        oa2.start();
                    }
                });

                oa1.start();
            }
            // 뒤 -> 앞
            else {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(ll_back, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(ll_front, "scaleX", 0f, 1f);

                oa1.setDuration(500);
                oa2.setDuration(500);

                ll_back.setVisibility(View.VISIBLE);
                ll_front.setVisibility(View.INVISIBLE);

                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ll_back.setVisibility(View.INVISIBLE);
                        ll_front.setVisibility(View.VISIBLE);
                        oa2.start();
                    }
                });

                oa1.start();
            }
        } else {
            if (fliped) {
                ll_back.setVisibility(View.VISIBLE);
                ll_front.setVisibility(View.INVISIBLE);

            } else {
                ll_front.setVisibility(View.VISIBLE);
                ll_back.setVisibility(View.INVISIBLE);
            }
        }
    }

    @androidx.databinding.BindingAdapter({"commentDateText"})
    public static void setCommentDate(TextView textView, CDayVO comment) {
        Log.d("comment : ", comment.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, comment.getYear());
        calendar.set(Calendar.MONTH, comment.getMonth() - 1);
        calendar.set(Calendar.DATE, comment.getDay());

        textView.setText(sdf.format(calendar.getTime()));
        textView.requestLayout();
    }
}
