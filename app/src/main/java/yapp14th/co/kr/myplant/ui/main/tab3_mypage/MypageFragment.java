package yapp14th.co.kr.myplant.ui.main.tab3_mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseFragment;

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreateView
// TODO 필수 선언 4. onViewCreated

public class MypageFragment extends BaseFragment {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mypage;
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    @Override
    protected boolean getIsUseDataBinding() {
        return false;
    }

    // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
    private static MypageFragment INSTANCE = null;
    public static synchronized Fragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MypageFragment();
        }
        return INSTANCE;
    }

    // TODO 필수 선언 3 (onCreateView)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // TODO 필수 선언 4 (onViewCreated)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewPager viewPager = view.findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);

        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.rectangle);
        listImage.add(R.drawable.rectangle);
        listImage.add(R.drawable.rectangle);

        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        for (int i = 0; i < listImage.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();

        TextView tv_emotion_num = view.findViewById(R.id.tv_emotion_num);
        TextView tv_illus_num = view.findViewById(R.id.tv_illus_num);

        tv_emotion_num.setText(125 +"개");
        tv_illus_num.setText(4 + "개");
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
    void addItem(ImageFragment fragment) {
        fragments.add(fragment);
    }
}