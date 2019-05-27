package yapp14th.co.kr.myplant.ui.intro;

import androidx.appcompat.app.AppCompatActivity;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.recyclerView.Main5Activity;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PushAgreeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_agree);

        if (!SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.PUSH_CHECK_FINISHED))
            init();
        else
            gotoColorActivity();

    }

    private void init() {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                push_commit(true);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                push_commit(false);
            }
        });
    }

    private void push_commit(boolean b) {
        SharedPreferenceUtil.setData(SharedPreferenceUtil.PUSH_CHECK_FINISHED, b);
        gotoColorActivity();
    }

    private void gotoColorActivity() {
        Intent intent = new Intent(getApplicationContext(), Main5Activity.class);
        startActivity(intent);
        finish();
    }
}
