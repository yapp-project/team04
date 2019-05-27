package yapp14th.co.kr.myplant.ui.main.tab3_mypage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseFragment;
import yapp14th.co.kr.myplant.recyclerView.Main3Activity;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;


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
        FragmentAdapter fragmentAdapter = new FragmentAdapter(
                new int[]{R.drawable.rectangle, R.drawable.rectangle, R.drawable.rectangle, R.drawable.rectangle},
                new String[]{
                        SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_1),
                        SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_2),
                        SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_3),
                        SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_4)});

        // 3. 리싸이클러뷰에 어뎁터 설정
        illust.setAdapter(fragmentAdapter);

        iv_mypage_01 = view.findViewById(R.id.iv_mypage_01);
        iv_mypage_02 = view.findViewById(R.id.iv_mypage_02);
        iv_mypage_03 = view.findViewById(R.id.iv_mypage_03);
        iv_mypage_04 = view.findViewById(R.id.iv_mypage_04);
        iv_mypage_05 = view.findViewById(R.id.iv_mypage_05);
        iv_mypage_06 = view.findViewById(R.id.iv_mypage_06);
        iv_mypage_07 = view.findViewById(R.id.iv_mypage_07);
        iv_mypage_08 = view.findViewById(R.id.iv_mypage_08);

        updateImageViewList();


//        ArrayList<Integer> listImage = new ArrayList<>();
//        listImage.add(R.drawable.rectangle);
////        listImage.add(R.drawable.img_splash);
//        listImage.add(R.drawable.dropper);

//        viewPager.setClipToPadding(false);
//        int dpValue = 16;
//        float d = getResources().getDisplayMetrics().density;
//        int margin = (int) (dpValue * d);
//        viewPager.setPadding(margin, 0, margin, 0);
//        viewPager.setPageMargin(margin/2);

//        for (int i = 0; i < listImage.size(); i++) {
//            ImageFragment imageFragment = new ImageFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("imgRes", listImage.get(i));
//            imageFragment.setArguments(bundle);
//            fragmentAdapter.addItem(imageFragment);
//        }
//        fragmentAdapter.notifyDataSetChanged();

        TextView tv_emotion_num = view.findViewById(R.id.tv_emotion_num);
        TextView tv_illus_num = view.findViewById(R.id.tv_illus_num);
        TextView tv_mypage_08 = view.findViewById(R.id.tv_mypage_08);
        Switch switch1 = view.findViewById(R.id.switch1);
        ImageView mypage_color_change = view.findViewById(R.id.mypage_colorchange);

        mypage_color_change.setOnClickListener(v -> {
            Intent startIntent = new Intent(getActivity(), Main3Activity.class); //일단은 이 화면으로 했음
            startIntent.putExtra("mypage", true);
            startActivityForResult(startIntent, 1001);
        });


        tv_emotion_num.setText(getEmotionsCount());
        tv_illus_num.setText(getAlbumsCount());
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

    public String getEmotionsCount(){
        Realm realm = getRealmInstance;

        getRealmInstance.beginTransaction();

        RealmResults abcd = realm.where(CDay.class).findAll();

        getRealmInstance.commitTransaction();

        return abcd.size() + "개";
    }

    public String getAlbumsCount(){
        Realm realm = getRealmInstance;

        getRealmInstance.beginTransaction();

        RealmResults abcd = realm.where(CDay.class).findAll();

        getRealmInstance.commitTransaction();

        return abcd.size() + "개";
    }
}


class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.IllustViewHolder> {
    private int[] dataSet;
    private String[] filterSet;

    public FragmentAdapter(int[] dataSet, String[] filterSet) {
        this.dataSet = dataSet;
        this.filterSet = filterSet;
    }

    @NonNull
    @Override
    public FragmentAdapter.IllustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_illust, parent, false);
        FragmentAdapter.IllustViewHolder viewHolder = new FragmentAdapter.IllustViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter.IllustViewHolder holder, int position) {
        holder.imageView1.setImageResource(dataSet[position]);
        holder.imageView2.setColorFilter(Color.parseColor(filterSet[position]), PorterDuff.Mode.SRC);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }


    public class IllustViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        ImageView imageView2;

        public IllustViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.showillust);
            imageView2 = (ImageView) itemView.findViewById(R.id.img_filter);
        }
    }
}