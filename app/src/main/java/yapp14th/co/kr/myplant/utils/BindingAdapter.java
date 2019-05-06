package yapp14th.co.kr.myplant.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;
import yapp14th.co.kr.myplant.R;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    @androidx.databinding.BindingAdapter("setFlipAnimation")
    public static void setFlipAnimation(View view, boolean fliped) {
        ConstraintLayout ll_front = view.findViewById(R.id.ll_front);
        ConstraintLayout ll_back = view.findViewById(R.id.ll_back);
        // 앞 -> 뒤
        if (fliped) {
            final ObjectAnimator oa1 = ObjectAnimator.ofFloat(ll_front, "scaleX", 1f, 0f);
            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(ll_back, "scaleX", 0f, 1f);

            oa1.setDuration(1000);
            oa2.setDuration(1000);

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

            oa1.setDuration(1000);
            oa2.setDuration(1000);

            oa1.setInterpolator(new DecelerateInterpolator());
            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
            oa1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ll_front.setVisibility(View.VISIBLE);
                    ll_back.setVisibility(View.INVISIBLE);
                    oa2.start();
                }
            });

            oa1.start();
        }
    }
}
