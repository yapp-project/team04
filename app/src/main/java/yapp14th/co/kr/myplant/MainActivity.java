package yapp14th.co.kr.myplant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}