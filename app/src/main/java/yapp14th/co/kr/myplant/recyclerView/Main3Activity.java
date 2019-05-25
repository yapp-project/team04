package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


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

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        width = (int) MyApplication.convertDpToPixel(90F, this);
        height = (int) MyApplication.convertDpToPixel(90F, this);
        radius = (int) MyApplication.convertDpToPixel(25F, this);

        init();

    }

    private void init() {
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

        adapter = new MyAdapter(myDataset, color_circle_set);
        recyclerView.setAdapter(adapter);


    }

    public void pre(View view) {
//        Log.d("position", "pre: " + MyAdapter.getPosition());
//        MyAdapter.setPosition(MyAdapter.getPosition()-1);
//        Log.d("position", "pre: " + MyAdapter.getPosition());
        recyclerView.scrollToPosition(--position);
        Log.d("position", "pre: " + position);
        if (position == 0) {
            ImageButton button = (ImageButton) findViewById(R.id.pre);
            button.setVisibility(View.INVISIBLE);
        }
        if (position < 7) {
            ImageButton button = (ImageButton) findViewById(R.id.succ);
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
            ImageButton button = (ImageButton) findViewById(R.id.pre);
            button.setVisibility(View.VISIBLE);
        }
        if (position == 7) {
            ImageButton button = (ImageButton) findViewById(R.id.succ);
            button.setVisibility(View.INVISIBLE);
        }
    }
}
