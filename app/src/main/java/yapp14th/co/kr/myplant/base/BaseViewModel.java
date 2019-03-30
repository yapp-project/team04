package yapp14th.co.kr.myplant.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    // 데이터 로딩중 체크
    private boolean mIsDataLoading;
    // progressbar enabled 체크
    private ObservableField<Boolean> mIsProgressbarEnabled;

    public boolean ismIsDataLoading() {
        return mIsDataLoading;
    }

    public void setmIsDataLoading(boolean mIsDataLoading) {
        this.mIsDataLoading = mIsDataLoading;
    }

    public ObservableField<Boolean> getmIsProgressbarEnabled() {
        return mIsProgressbarEnabled;
    }

    public void setmIsProgressbarEnabled(ObservableField<Boolean> mIsProgressbarEnabled) {
        this.mIsProgressbarEnabled = mIsProgressbarEnabled;
    }
}