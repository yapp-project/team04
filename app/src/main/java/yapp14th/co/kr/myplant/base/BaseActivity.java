package yapp14th.co.kr.myplant.base;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import buv.co.kr.base.BaseDialog;
import yapp14th.co.kr.myplant.R;

public abstract class BaseActivity extends AppCompatActivity {
    private int layoutRes = -1;
    private boolean isUseDataBinding = false;
    protected BaseDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new BaseDialog(getApplicationContext());

        if (layoutRes > -1) {
            if (isUseDataBinding)
                setContentView(layoutRes);
            else
                onDataBinding();
        }

        setupViews();
        subScribeUI();
    }

    public void onDataBinding() {

    }

    // 뷰 컴포넌트 설정
    protected abstract void setupViews();

    // livedata 있는 경우 observe 설정
    public void subScribeUI() {

    }

    // 툴바 세팅
    public void setToolbar(int backgroundColor) {
        // 1. 툴바 있는지 확인하여 있으면 세팅 (toolbar id 이름은 toolbar로 세팅)
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setBackgroundResource(backgroundColor);

            // 2. actionbar 설정
            setSupportActionBar(toolbar);

            ActionBar actionbar = getSupportActionBar();
            if (actionbar != null) {
                actionbar.setDisplayShowTitleEnabled(false);
                actionbar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public BaseDialog getDialog(){
        return dialog;
    }

    public void openDialog(int type, String title, String subTitle, int layoutRes, View.OnClickListener okListener, View.OnClickListener cancelListener) {
        // 초기 설정
        dialog.setInit(layoutRes, type);
        dialog.setTitle(title, subTitle);

        dialog.setOkButtonListener(okListener);
        dialog.setCancelButtonListener(cancelListener);
        dialog.callFunction(getApplicationContext());
    }

    protected abstract int getLayoutRes();

    protected abstract boolean getIsUseDataBinding();
}
