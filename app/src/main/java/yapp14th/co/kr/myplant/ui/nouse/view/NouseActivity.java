package yapp14th.co.kr.myplant.ui.nouse.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import buv.co.kr.base.BaseDialog;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseActivity;
import yapp14th.co.kr.myplant.databinding.ActivityNouseBinding;
import yapp14th.co.kr.myplant.ui.nouse.viewModel.NouseViewModel;
import yapp14th.co.kr.myplant.utils.ActivityUtil;

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreate
// TODO 필수 선언 4. 툴바 설정

// 선택 선언 1. 데이터 바인딩 사용 시
// 선택 선언 2. ViewModel 사용 시
// 선택 선언 3. 다이얼로그 사용 시
// 선택 선언 4. 프레그먼트 사용 시
// 선택 선언 5. LiveData 사용 시

public class NouseActivity extends BaseActivity {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_nouse;
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정 (사용안할 시 반드시 false 처리할 것))
    @Override
    protected boolean getIsUseDataBinding() {
        return true;
    }

    // 선택 선언 1_1 (데이터 바인딩)
    ActivityNouseBinding binding;

    // 선택 선언 2_1 (ViewModel)
    NouseViewModel nouseVM;
    public NouseViewModel getNouseVM() {
        return nouseVM;
    }

    // TODO 필수 선언 3 (onCreate)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 선택 선언 2_2 (ViewModel 사용 시 반드시 super 호출 전에 정의해야 함!!!)
        nouseVM = ViewModelProviders.of(this).get(NouseViewModel.class);

        super.onCreate(savedInstanceState);

        // TODO 필수 선언 4 (툴바 설정)
        setToolbar(R.color.transparent);

        // 선택 선언 3 (다이얼로그 사용 시)
        dialog = new BaseDialog(NouseActivity.this);
        openDialog(MyApplication.DIALOG_OK, "제목", "부제목", R.layout.base_dialog,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

        // 선택 선언 4 (프레그먼트 사용 시)
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), NouseFragment.getInstance(), R.id.fl_container);
    }

    // 선택 선언 1_2 (데이터바인딩 사용 시)
    @Override
    public void onDataBinding() {
        super.onDataBinding();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nouse);

        binding.setNouseView(this);

        // 선택 선언 2_3 (ViewModel 사용 시)
        binding.setNouseVM(nouseVM);
    }

    // 선택 선언 5 (LiveData 사용 시 해당 함수 선언 후 사용)
    @Override
    public void subScribeUI() {
        super.subScribeUI();
    }
}
