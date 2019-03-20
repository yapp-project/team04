package yapp14th.co.kr.myplant.ui.nouse.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import buv.co.kr.base.BaseDialog;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseFragment;
import yapp14th.co.kr.myplant.databinding.FragmentNouseBinding;
import yapp14th.co.kr.myplant.ui.nouse.viewModel.NouseFragemntViewModel;

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreateView
// TODO 필수 선언 4. onViewCreated

// 선택 선언 1. Fragment 를 싱글턴으로 사용할 시
// 선택 선언 2. 데이터 바인딩
// 선택 선언 3. ViewModel
// 선택 선언 4. 다이얼로그 사용 시
// 선택 선언 5. LiveData 사용 시

public class NouseFragment extends BaseFragment {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_nouse;
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    @Override
    protected boolean getIsUseDataBinding() {
        return true;
    }

    // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
    private static NouseFragment INSTANCE = null;
    public static synchronized Fragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NouseFragment();
        }
        return INSTANCE;
    }

    // 선택 선언 2_1 (데이터 바인딩)
    FragmentNouseBinding binding;

    // 선택 선언 3_1 (ViewModel)
    NouseFragemntViewModel nouseFragmentVM;
    public NouseFragemntViewModel getNouseVM() {
        return nouseFragmentVM;
    }

    // TODO 필수 선언 3 (onCreateView)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 선택 선언 3_2 (ViewModel 사용 시 반드시 super 호출 전에 정의해야 함!!!)
        nouseFragmentVM = ViewModelProviders.of(this).get(NouseFragemntViewModel.class);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // TODO 필수 선언 4 (onViewCreated)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 선택 선언 4_1 (다이얼로그 사용 시)
        dialog = new BaseDialog(Objects.requireNonNull(getActivity()));
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
    }

    // 선택 선언 2_2 (데이터바인딩 사용 시)
    @Override
    public View onDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        binding.setNouseFragmentView(this);
        binding.setNouseFragmentVM(nouseFragmentVM);
        return binding.getRoot();
    }

    // 선택 선언 5 (LiveData 사용 시)
    @Override
    public void subScribeUI() {
        super.subScribeUI();
    }
}
