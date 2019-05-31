package yapp14th.co.kr.myplant.ui.main.tab3_mypage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseFragment;
import yapp14th.co.kr.myplant.recyclerView.Main3Activity;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth;
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl;
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYearEmotions;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

import static yapp14th.co.kr.myplant.utils.DefaultVariableKt.adjustAlpha;
import static yapp14th.co.kr.myplant.utils.DefaultVariableKt.getCurrentYear;
import static yapp14th.co.kr.myplant.utils.DefaultVariableKt.getcalendarResources;
import static yapp14th.co.kr.myplant.utils.RealmUseCaseKt.getAlbumsCount;
import static yapp14th.co.kr.myplant.utils.RealmUseCaseKt.getEmotionsCount;

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

    private CircleImageView iv_mypage_01;
    private CircleImageView iv_mypage_02;
    private CircleImageView iv_mypage_03;
    private CircleImageView iv_mypage_04;
    private CircleImageView iv_mypage_05;
    private CircleImageView iv_mypage_06;
    private CircleImageView iv_mypage_07;
    private CircleImageView iv_mypage_08;

    // TODO 필수 선언 4 (onViewCreated)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. 리싸이클러뷰 레이아웃 매니저 설정
        RecyclerView illust = view.findViewById(R.id.illust);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        illust.setLayoutManager(mLayoutManager);

        // 2. 어뎁터 인스턴스 생성
        ArrayList<Integer> dataSet = new ArrayList<>();
        ArrayList<String> filterSet = new ArrayList<>();

        for (int year = 2019; year < getCurrentYear() + 1; year++) {
            new GetYearEmotions(new HomeRepositoryImpl(), Schedulers.io()).invoke(
                    year, (calendars) -> {
                        for (CalendarMonth calendar : calendars) {
                            // 각 월 별 그림 산출 시작
                            List<CDayVO> monthDays = calendar.get_dayList();
                            int[] array = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
                            for (int i = 0; i < monthDays.size(); i++) {
                                array[monthDays.get(i).getEmotionType()]++;
                            }

                            int maxIndex = 0;
                            for (int emotionNum = 0; emotionNum < array.length - 1; emotionNum++) {
                                maxIndex = array[maxIndex] < array[emotionNum + 1] ? emotionNum + 1 : maxIndex;
                            }

                            // 데이터 갱신
                            if (maxIndex != 0) {
                                dataSet.add(getcalendarResources()[maxIndex]);
                                filterSet.add(SharedPreferenceUtil.getStringData("EMOTION_" + maxIndex));
                            }
                        }

                        MypageListAdapter fragmentAdapter = new MypageListAdapter(
                                dataSet,
                                filterSet);

                        // 3. 리싸이클러뷰에 어뎁터 설정
                        illust.setAdapter(fragmentAdapter);

                        TextView tv_illus_num = view.findViewById(R.id.tv_illus_num);
                        tv_illus_num.setText(filterSet.size() + "개");

                        return Unit.INSTANCE;
                    }

                    , (throwable) -> {
                        System.out.println(throwable.toString());
                        return Unit.INSTANCE;
                    });
        }

        iv_mypage_01 = view.findViewById(R.id.iv_mypage_01);
        iv_mypage_02 = view.findViewById(R.id.iv_mypage_02);
        iv_mypage_03 = view.findViewById(R.id.iv_mypage_03);
        iv_mypage_04 = view.findViewById(R.id.iv_mypage_04);
        iv_mypage_05 = view.findViewById(R.id.iv_mypage_05);
        iv_mypage_06 = view.findViewById(R.id.iv_mypage_06);
        iv_mypage_07 = view.findViewById(R.id.iv_mypage_07);
        iv_mypage_08 = view.findViewById(R.id.iv_mypage_08);

        updateImageViewList();

        TextView tv_emotion_num = view.findViewById(R.id.tv_emotion_num);
        TextView tv_mypage_08 = view.findViewById(R.id.tv_mypage_08);
        Switch switch1 = view.findViewById(R.id.switch1);

        switch1.setChecked(SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.PUSH_CHECK_ENABLED));
        switch1.setOnCheckedChangeListener((compoundButton, checked) -> {
                    SharedPreferenceUtil.setData(SharedPreferenceUtil.PUSH_CHECK_ENABLED, checked);
                    Log.d("푸시 설정 ", SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.PUSH_CHECK_ENABLED) + "");
                }
        );

        ImageView mypage_color_change = view.findViewById(R.id.mypage_colorchange);

        mypage_color_change.setOnClickListener(v -> {
            Intent startIntent = new Intent(getActivity(), Main3Activity.class); //일단은 이 화면으로 했음
            startIntent.putExtra("mypage", true);
            startActivityForResult(startIntent, 1001);
        });


        tv_emotion_num.setText(getEmotionsCount() + "개");
        tv_mypage_08.setText(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last));
    }

    private void updateImageViewList() {
        iv_mypage_01.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_1)));
        iv_mypage_02.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_2)));
        iv_mypage_03.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_3)));
        iv_mypage_04.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_4)));
        iv_mypage_05.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_5)));
        iv_mypage_06.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_6)));
        iv_mypage_07.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_7)));
        iv_mypage_08.setColorFilter(Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_8)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            updateImageViewList();
        }
    }
}


class MypageListAdapter extends RecyclerView.Adapter<MypageListAdapter.IllustViewHolder> {
    private ArrayList<Integer> dataSet;
    private ArrayList<String> filterSet;

    public MypageListAdapter(ArrayList<Integer> dataSet, ArrayList<String> filterSet) {
        this.dataSet = dataSet;
        this.filterSet = filterSet;
    }

    @NonNull
    @Override
    public MypageListAdapter.IllustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_illust, parent, false);
        MypageListAdapter.IllustViewHolder viewHolder = new MypageListAdapter.IllustViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MypageListAdapter.IllustViewHolder holder, int position) {
        holder.imageView1.setImageResource(dataSet.get(position));

        int color = adjustAlpha(Color.parseColor(filterSet.get(position)), 0.4f);
        holder.imageView2.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class IllustViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        ImageView imageView2;

        public IllustViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.img_picture);
            imageView2 = (ImageView) itemView.findViewById(R.id.img_filter);
        }
    }
}