package yapp14th.co.kr.myplant.viewPager;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import yapp14th.co.kr.myplant.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView textView = view.findViewById(R.id.textView);

        if (getArguments() != null) {
            Bundle args = getArguments();
            textView.setText(args.getString("key"));
        }

        return view;
    }

}
