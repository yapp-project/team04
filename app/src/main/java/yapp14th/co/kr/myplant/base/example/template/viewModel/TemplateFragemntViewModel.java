package yapp14th.co.kr.myplant.base.example.template.viewModel;

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

    @Override
    protected void onCleared() {

    }
}