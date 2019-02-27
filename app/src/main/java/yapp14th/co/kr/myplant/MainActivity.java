package yapp14th.co.kr.myplant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import yapp14th.co.kr.myplant.databinding.ActivityMainBinding;

// 기본적인 화면구현
// - 스플래시, 로그인
// - 메인 화면
//      카메라
// - 메인 화면 -> 마이페이지 + 히스토리
// - 가족 공유 (캘린더, 가족 연동 로직 뷰)

// - SNS 로그인 구현 (카카오, 네이버)
// - 알람 매니저
// - 서버 통신

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String input = "abcd";

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        binding.tvTitle.setText("abcd");
    }

    public void onButtonClick(String abcd){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}