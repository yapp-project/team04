package yapp14th.co.kr.myplant.viewPager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ViewPager viewPager;

    private String[] myDataset;

    int width;
    int height;
    int radius;
    private int[] color_circle_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        width = (int) MyApplication.convertDpToPixel(90F, this);
        height = (int) MyApplication.convertDpToPixel(90F, this);
        radius = (int) MyApplication.convertDpToPixel(25F, this);

        init();
    }

    private void init() {
        viewPager = findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
// ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(8);

        myDataset = getResources().getStringArray(R.array.emotions);
        myDataset[7] = "짜증";

        color_circle_set = new int[]{width, height, radius};

//        ArrayList<String> listText = new ArrayList<>();
//        listText.add("기쁨");
//        listText.add("행복");
//        listText.add("신남");
//        listText.add("평화");
//        listText.add("슬픔");
//        listText.add("불안");
//        listText.add("분노");
//        listText.add("짜증");

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < myDataset.length; i++) {
            BlankFragment blankFragment = new BlankFragment(myDataset, color_circle_set);
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            bundle.putString("key", myDataset[i]);
            blankFragment.setArguments(bundle);
            fragmentAdapter.addItem(blankFragment);
        }
        fragmentAdapter.notifyDataSetChanged();

        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);
    }

    public void pre(View view) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            if (viewPager.getCurrentItem() == 0) {
                ImageButton button = (ImageButton) findViewById(R.id.pre);
                button.setVisibility(View.INVISIBLE);
            }
        if (viewPager.getCurrentItem() < 7) {
            ImageButton button = (ImageButton) findViewById(R.id.succ);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void succ(View view) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (viewPager.getCurrentItem() > 0) {
                ImageButton button = (ImageButton) findViewById(R.id.pre);
                button.setVisibility(View.VISIBLE);
            }
            if (viewPager.getCurrentItem() == 7) {
                ImageButton button = (ImageButton) findViewById(R.id.succ);
                button.setVisibility(View.INVISIBLE);
            }
    }
}

class FragmentAdapter extends FragmentPagerAdapter {

    // ViewPager에 들어갈 Fragment들을 담을 리스트
    private ArrayList<Fragment> fragments = new ArrayList<>();

    // 필수 생성자
    FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    // List에 Fragment를 담을 함수
    void addItem(Fragment fragment) {
        fragments.add(fragment);
    }
}
