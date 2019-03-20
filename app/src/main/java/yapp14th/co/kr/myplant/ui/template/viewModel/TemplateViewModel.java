package yapp14th.co.kr.myplant.ui.template.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TemplateViewModel extends AndroidViewModel {
    String input = "abcdefg";
    public String getInput() {
        return input;
    }

    public TemplateViewModel(@NonNull Application application) {
        super(application);
    }
}