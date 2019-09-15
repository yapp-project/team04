package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import buv.co.kr.base.BaseDialog;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.example.template.view.TemplateActivity;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class Main3Activity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset;
    private SnapHelper snapHelper;

    int width;
    int height;
    int radius;
    private int[] color_circle_set;
    private boolean mypage;
    private BaseDialog dialog;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        width = (int) MyApplication.convertDpToPixel(90F, this);
        height = (int) MyApplication.convertDpToPixel(90F, this);
        radius = (int) MyApplication.convertDpToPixel(25F, this);

        mypage = getIntent().getBooleanExtra("mypage", false);
        if (mypage) {
            init(true);
            Toast.makeText(this, "색상원의 색깔을 먼저 선택해주세요", Toast.LENGTH_LONG).show();
        } else {
            init(false);
            openDialog(1, "색상원의 색깔을 먼저 선택해주세요.", "", R.layout.base_dialog, view -> dialog.dismiss(), view -> dialog.dismiss());
        }
    }

    private void init(boolean mypage) {
        position = 0;

        snapHelper = new LinearSnapHelper();

        recyclerView = findViewById(R.id.recyclerView);

        snapHelper.attachToRecyclerView(recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        //myDataset = new String[]{"기쁨", "행복", "신남", "평화", "슬픔", "불안", "분노", "짜증"};
        myDataset = getResources().getStringArray(R.array.emotions);
        myDataset[7] = "짜증";

        color_circle_set = new int[]{width, height, radius};

        // specify an adapter (see also next example)

        if (mypage) {
            String prevLast = SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last);
            String[] prevColorSet = new String[]{
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_1),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_2),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_3),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_4),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_5),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_6),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_7),
                    SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_8)
            };

            float[] prevPinXSet = new float[]{
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_1_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_2_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_3_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_4_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_5_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_6_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_7_PIN_X),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_8_PIN_X)
            };

            float[] prevPinYSet = new float[]{
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_1_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_2_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_3_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_4_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_5_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_6_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_7_PIN_Y),
                    SharedPreferenceUtil.getFloatData(SharedPreferenceUtil.EMOTION_8_PIN_Y)
            };


            adapter = new MyAdapter(mypage, myDataset, color_circle_set, prevLast, prevColorSet, prevPinXSet, prevPinYSet);
        } else
            adapter = new MyAdapter(mypage, myDataset, color_circle_set);
        recyclerView.setAdapter(adapter);


    }

    public void pre(View view) {
//        Log.d("position", "pre: " + MyAdapter.getPosition());
//        MyAdapter.setPosition(MyAdapter.getPosition()-1);
//        Log.d("position", "pre: " + MyAdapter.getPosition());
        recyclerView.scrollToPosition(--position);
        Log.d("position", "pre: " + position);
        if (position == 0) {
            ImageButton button = findViewById(R.id.pre);
            button.setVisibility(View.INVISIBLE);
        }
        if (position < 7) {
            ImageButton button = findViewById(R.id.succ);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void succ(View view) {
//        Log.d("position", "succ: " + MyAdapter.getPosition());
//        MyAdapter.setPosition(MyAdapter.getPosition()+1);
//        Log.d("position", "succ: " + MyAdapter.getPosition());
        recyclerView.scrollToPosition(++position);
        Log.d("position", "succ: " + position);
        if (position > 0) {
            ImageButton button = findViewById(R.id.pre);
            button.setVisibility(View.VISIBLE);
        }
        if (position == 7) {
            ImageButton button = findViewById(R.id.succ);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void openDialog(int type, String title, String subTitle, int layoutRes, View.OnClickListener okListener, View.OnClickListener cancelListener) {
        // 초기 설정
        dialog = new BaseDialog(Main3Activity.this);

        dialog.setInit(layoutRes, type);
        dialog.setTitle(title, subTitle);

        dialog.setOkButtonListener(okListener);
        dialog.setCancelButtonListener(cancelListener);
        dialog.callFunction();
    }
}
