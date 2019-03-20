package yapp14th.co.kr.myplant.ui.nouse.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NouseViewModel extends AndroidViewModel {
    String input = "abcdefg";
    public String getInput() {
        return input;
    }

    public NouseViewModel(@NonNull Application application) {
        super(application);
    }
}