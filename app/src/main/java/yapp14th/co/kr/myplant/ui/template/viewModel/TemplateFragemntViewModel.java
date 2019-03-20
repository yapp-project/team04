package yapp14th.co.kr.myplant.ui.template.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TemplateFragemntViewModel extends AndroidViewModel {
    String input = "fragment_text_updated_by viewModel";
    public String getInput() {
        return input;
    }

    public TemplateFragemntViewModel(@NonNull Application application) {
        super(application);
    }
}