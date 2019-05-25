package yapp14th.co.kr.myplant.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import buv.co.kr.base.BaseDialog;
import io.realm.Realm;
import yapp14th.co.kr.myplant.R;

public abstract class BaseFragment extends Fragment {
    private int layoutRes = getLayoutRes();
    private boolean isUseDataBinding = getIsUseDataBinding();
    protected BaseDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (layoutRes > -1) {
            if (isUseDataBinding)
                return onDataBinding(inflater, container);
            else
                return inflater.inflate(layoutRes, container, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public View onDataBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subScribeUI();
    }

    // livedata 있는 경우 observe 설정
    public void subScribeUI() {

    }

    public BaseDialog getDialog() {
        return dialog;
    }

    public void openDialog(int type, String title, String subTitle, int layoutRes, View.OnClickListener okListener, View.OnClickListener cancelListener) {
        // 초기 설정
        dialog.setInit(layoutRes, type);
        dialog.setTitle(title, subTitle);

        dialog.setOkButtonListener(okListener);
        dialog.setCancelButtonListener(cancelListener);
        dialog.callFunction();
    }

    protected abstract int getLayoutRes();

    protected abstract boolean getIsUseDataBinding();

    public Realm getRealmInstance = Realm.getDefaultInstance();
}
