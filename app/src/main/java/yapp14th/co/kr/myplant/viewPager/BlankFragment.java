package yapp14th.co.kr.myplant.viewPager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.components.ColorPickerView;
import yapp14th.co.kr.myplant.recyclerView.Main3Activity;
import yapp14th.co.kr.myplant.recyclerView.Main4Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener, ColorPickerView.OnColorChangedListener {
    private String[] dataset;
    private int[] color_circle_set;


    int red;
    int blue;
    int green;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean et_show;

    private TextView num;
    private TextView name;
    private CardView last;
    private EditText input;
    private Button button;

    private LinearLayout color_pick;
    private View colorView;
    private ColorPickerView colortest;
    private TextView R_tv;
    private TextView G_tv;
    private TextView B_tv;
    private TextView hexcode_tv;
    private SeekBar intro_sb_brightness;
    private SeekBar intro_sb_chroma;

    public BlankFragment(String[] dataset, int[] color_circle_set) {
        // Required empty public constructor
        this.dataset = dataset;
        this.color_circle_set = color_circle_set;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("sFile", MODE_PRIVATE);

        num = view.findViewById(R.id.num);
        name = view.findViewById(R.id.name);
        last = view.findViewById(R.id.last);
        input = view.findViewById(R.id.input);
        button = view.findViewById(R.id.button);

        color_pick = view.findViewById(R.id.color_pick);
        colorView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_intro_colorpick, null);
        color_pick.addView(colorView);
        colortest = color_pick.findViewById(R.id.colorPickerView_test);

        R_tv = color_pick.findViewById(R.id.R_et);
        G_tv = color_pick.findViewById(R.id.G_et);
        B_tv = color_pick.findViewById(R.id.B_et);
        intro_sb_brightness = color_pick.findViewById(R.id.intro_sb_brightness);
        intro_sb_chroma = color_pick.findViewById(R.id.intro_sb_chroma);
        hexcode_tv = color_pick.findViewById(R.id.hex_code_et);
        colortest.init((ColorPickerView.OnColorChangedListener) this
                , color_circle_set[0], color_circle_set[1], color_circle_set[2]);

        //명도 변경
        intro_sb_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float[] hsv = new float[3];
                Color.RGBToHSV(red, green, blue, hsv);
                hsv[2] = (float) (intro_sb_brightness.getProgress()) / 100;
                colortest.changeValue(BlankFragment.this::colorChanged, hsv);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //채도 변경
        intro_sb_chroma.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float[] hsv = new float[3];
                Color.RGBToHSV(red, green, blue, hsv);
                hsv[1] = (float) (intro_sb_chroma.getProgress()) / 100;
                colortest.changeChroma(BlankFragment.this::colorChanged, hsv);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String last_emotion = input.getText().toString();
                Log.d("last", last_emotion);
                dataset[7] = last_emotion;
                editor = sharedPreferences.edit();
                editor.putString(input.getText().toString(), hexcode_tv.getText().toString());
                editor.commit();
            }
        });

        if (getArguments() != null) {
            Bundle args = getArguments();
            num.setText("0" + (args.getInt("position") + 1));

            if (args.getInt("position") != 7) {
                last.setVisibility(View.GONE);
                input.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                name.setText(args.getString("key"));
                button.setVisibility(View.INVISIBLE);
                et_show = false;

            } else {
                name.setVisibility(View.GONE);
                last.setVisibility(View.VISIBLE);
                input.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(this);
                et_show = true;
                //Log.d(holder.input.getText().toString(),holder.hexcode_tv.getText().toString());
            }
        }

        return view;
    }
    public void colorChanged(int color, int red, int green, int blue) {
        red = red;
        green = green;
        blue = blue;

        R_tv.setText(String.valueOf(red));
        G_tv.setText(String.valueOf(green));
        B_tv.setText(String.valueOf(blue));
        hexcode_tv.setText(String.format("#%04X", color));
        intro_sb_brightness.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        intro_sb_chroma.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        editor = sharedPreferences.edit();
        if (et_show) //사용자 정의 감정 색 선택
            editor.putString(input.getText().toString(), hexcode_tv.getText().toString());

        else // 정해진 감정 색 선택
            editor.putString(name.getText().toString(), hexcode_tv.getText().toString());
        editor.commit();

        Log.d("emottt1", sharedPreferences.getString(input.getText().toString(), "#0000000"));
        Log.d("emottt", sharedPreferences.getString(name.getText().toString(), "#0000000"));
    }

    @Override
    public void onClick(View view) {
        for (int i=0; i<8; i++) {
            if (sharedPreferences.getString(dataset[i], "-1").equals("-1")) {
                Toast.makeText(getContext(), "모든 감정에 다른 색상을 선정해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        editor.putString("last", dataset[7]);
        Intent intent = new Intent(view.getContext(), Main4Activity.class);
        intent.putExtra("emotion", dataset);
        view.getContext().startActivity(intent);
        ((Main2Activity) view.getContext()).finish();
    }
}
