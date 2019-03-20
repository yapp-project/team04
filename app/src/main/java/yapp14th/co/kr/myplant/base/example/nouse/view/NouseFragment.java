package yapp14th.co.kr.myplant.base.example.nouse.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseFragment;

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreateView
// TODO 필수 선언 4. onViewCreated

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

    // TODO 필수 선언 3 (onCreateView)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // TODO 필수 선언 4 (onViewCreated)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
