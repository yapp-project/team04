package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;

import android.os.Bundle;


public class Main3Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private String[] myDataset;

    private SnapHelper snapHelper;

    int width;
    int height;
    int radius;
    private int[] color_circle_set;

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
        snapHelper = new LinearSnapHelper();

        recyclerView = findViewById(R.id.recyclerView);

        snapHelper.attachToRecyclerView(recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        //myDataset = new String[]{"기쁨", "행복", "신남", "평화", "슬픔", "불안", "분노", "짜증"};
        myDataset = getResources().getStringArray(R.array.emotions);
        color_circle_set = new int[]{width, height, radius};

        // specify an adapter (see also next example)

        adapter = new MyAdapter(myDataset, color_circle_set);
        recyclerView.setAdapter(adapter);


    }
}
