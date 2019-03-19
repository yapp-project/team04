package yapp14th.co.kr.myplant.ui;

import android.os.Bundle;
import android.view.View;

import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.base.BaseActivity;

public class TemplateActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_template;
    }

    @Override
    protected boolean getIsUseDataBinding() {
        return true;
    }

    @Override
    protected void setupViews() {
        setToolbar(R.color.transparent);

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


}
