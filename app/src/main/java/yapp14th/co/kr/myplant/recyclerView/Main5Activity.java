package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.ui.insert.InsertActivity;
import yapp14th.co.kr.myplant.ui.main.MainActivity;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;
import yapp14th.co.kr.myplant.viewPager.Main2Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main5Activity extends AppCompatActivity {
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        if (!SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.COLOR_PICK_FINISHED))
            init();
        else
            gotoInsertActivity();
    }

    private void init() {
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
            startActivity(intent);
            finish();
        });
    }

    private void gotoInsertActivity(){
        Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
        startActivity(intent);
        finish();
    }
}
