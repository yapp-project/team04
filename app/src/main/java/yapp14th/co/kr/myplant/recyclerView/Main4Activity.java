package yapp14th.co.kr.myplant.recyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.databinding.ActivityMain4Binding;
import yapp14th.co.kr.myplant.ui.insert.InsertActivity;
import yapp14th.co.kr.myplant.ui.main.MainActivity;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

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


        binding.emotion1.setText(emotion[0]);
        binding.emotion2.setText(emotion[1]);
        binding.emotion3.setText(emotion[2]);
        binding.emotion4.setText(emotion[3]);
        binding.emotion5.setText(emotion[4]);
        binding.emotion6.setText(emotion[5]);
        binding.emotion7.setText(emotion[6]);
        binding.emotion8.setText(emotion[7]);

        binding.emotion1Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("1")));
        binding.emotion2Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("2")));
        binding.emotion3Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("3")));
        binding.emotion4Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("4")));
        binding.emotion5Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("5")));
        binding.emotion6Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("6")));
        binding.emotion7Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("7")));
        binding.emotion8Iv.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData("8")));

        binding.finishBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), InsertActivity.class));
            finish();
        });
    }
}