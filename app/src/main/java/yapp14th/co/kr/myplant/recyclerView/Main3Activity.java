package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import yapp14th.co.kr.myplant.R;

import android.os.Bundle;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset;

    private SnapHelper snapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        init();
    }

    private void init() {
        snapHelper = new LinearSnapHelper();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        snapHelper.attachToRecyclerView(recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        myDataset = new String[]{"기쁨", "행복", "신남", "평화", "슬픔", "불안", "분노", "짜증"};

        // specify an adapter (see also next example)
        adapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);
    }
}
