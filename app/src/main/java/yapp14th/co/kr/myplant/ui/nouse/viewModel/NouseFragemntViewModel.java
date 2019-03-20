package yapp14th.co.kr.myplant.ui.nouse.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NouseFragemntViewModel extends AndroidViewModel {
    String input = "fragment_text_updated_by viewModel";
    public String getInput() {
        return input;
    }

    public NouseFragemntViewModel(@NonNull Application application) {
        super(application);
    }
}