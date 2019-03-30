package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import yapp14th.co.kr.myplant.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent = getIntent();
        String input = intent.getStringExtra("name");

        Toast.makeText(this, input, Toast.LENGTH_LONG).show();
    }
}
