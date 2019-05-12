package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.databinding.ActivityMain4Binding;
import yapp14th.co.kr.myplant.ui.insert.InsertActivity;
import yapp14th.co.kr.myplant.ui.main.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main4);
        binding.setActivity(this);

        Intent intent = getIntent();
        String[] emotion = intent.getStringArrayExtra("emotion");

        SharedPreferences pref= getSharedPreferences("sFile",MODE_PRIVATE);

        binding.emotion1.setText(emotion[0]);
        binding.emotion2.setText(emotion[1]);
        binding.emotion3.setText(emotion[2]);
        binding.emotion4.setText(emotion[3]);
        binding.emotion5.setText(emotion[4]);
        binding.emotion6.setText(emotion[5]);
        binding.emotion7.setText(emotion[6]);
        binding.emotion8.setText(emotion[7]);


        binding.emotion1Iv.setColorFilter(Color.parseColor(pref.getString(emotion[0], "#000000")));
        binding.emotion2Iv.setColorFilter(Color.parseColor(pref.getString(emotion[1], "#000000")));
        binding.emotion3Iv.setColorFilter(Color.parseColor(pref.getString(emotion[2], "#000000")));
        binding.emotion4Iv.setColorFilter(Color.parseColor(pref.getString(emotion[3], "#000000")));
        binding.emotion5Iv.setColorFilter(Color.parseColor(pref.getString(emotion[4], "#000000")));
        binding.emotion6Iv.setColorFilter(Color.parseColor(pref.getString(emotion[5], "#000000")));
        binding.emotion7Iv.setColorFilter(Color.parseColor(pref.getString(emotion[6], "#000000")));
        binding.emotion8Iv.setColorFilter(Color.parseColor(pref.getString(emotion[7], "#000000")));

        binding.finishBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
    }
}
