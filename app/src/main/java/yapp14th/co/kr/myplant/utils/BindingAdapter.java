package yapp14th.co.kr.myplant.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth;

import static yapp14th.co.kr.myplant.utils.DefaultVariableKt.adjustAlpha;
import static yapp14th.co.kr.myplant.utils.DefaultVariableKt.getcalendarResources;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("android:visibility")
    public static void setBackground(View view, boolean isVisible) {
        if (isVisible)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

    @androidx.databinding.BindingAdapter("visibility")
    public static void setBackgroundInvisible(View view, boolean isVisible) {
        if (isVisible)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.INVISIBLE);
    }

    @androidx.databinding.BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    @androidx.databinding.BindingAdapter("calendarPictureBackground")
    public static void setCalendarPicture(ImageView view, CalendarMonth icMonth) {
        // TODO 여기에 달 계산 로직 넣음0
        List<CDayVO> monthDays = icMonth.get_dayList();
        int[] array = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < monthDays.size(); i++) {
            array[monthDays.get(i).getEmotionType()]++;
        }

        int maxIndex = 0;
        for (int emotionNum = 0; emotionNum < array.length - 1; emotionNum++) {
            maxIndex = array[maxIndex] < array[emotionNum + 1] ? emotionNum + 1 : maxIndex;
        }

        int picture = getcalendarResources()[maxIndex];
        view.setImageResource(picture);
        view.requestLayout();
    }

    @androidx.databinding.BindingAdapter("calendarFilterBackground")
    public static void setCalendarFilter(ImageView view, CalendarMonth icMonth) {
        // TODO 여기에 달 계산 로직 넣음
        List<CDayVO> monthDays = icMonth.get_dayList();
        int[][] array = new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {8, 0}};
        for (int i = 0; i < monthDays.size(); i++) {
            array[monthDays.get(i).getEmotionType()][1]++;
        }

        JavaUtil.sort(array, 0, array.length - 1);
        int maxIndex = array[0][0];
        int secondIndex = array[1][0];

        if (array[0][1] != 0) {
            if (array[1][1] == 0)
                secondIndex = maxIndex;
        }
        else
            secondIndex = 0;

        int color = adjustAlpha(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_" + secondIndex)), 0.4f);
        view.setBackgroundColor(color);
        view.requestLayout();
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


    @androidx.databinding.BindingAdapter({"commentEmotionText"})
    public static void setCommentDate(TextView textView, int emotionType) {
        String[] array = textView.getContext().getResources().getStringArray(R.array.emotions);
        array[array.length - 1] = SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last);

        Log.d("emotionList check: ", Arrays.toString(array));

        textView.setText(array[emotionType - 1]);
        textView.requestLayout();
    }
}
