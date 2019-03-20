package yapp14th.co.kr.myplant.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class BaseViewModel extends AndroidViewModel {
    // 데이터 로딩중 체크
    private boolean mIsDataLoading;

    public boolean ismIsDataLoading() {
        return mIsDataLoading;
    }

    public void setmIsDataLoading(boolean mIsDataLoading) {
        this.mIsDataLoading = mIsDataLoading;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}