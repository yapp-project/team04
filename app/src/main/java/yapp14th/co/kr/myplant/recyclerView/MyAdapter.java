package yapp14th.co.kr.myplant.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import android.widget.SeekBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import yapp14th.co.kr.myplant.MyApplication;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.components.ColorPickerView;
import static android.content.Context.MODE_PRIVATE;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private String[] dataset;
   private int[] color_circle_set;


   int red;
   int blue;
   int green;
    public SharedPreferences sharedPreferences;
    Boolean et_show;

    public MyAdapter(String[] dataset,int[] color_circle_set) {
        this.dataset = dataset;
        this.color_circle_set = color_circle_set;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {

        holder.num.setText("0" + (position + 1));
        if (position!= 7) {
            holder.last.setVisibility(View.GONE);
            holder.input.setVisibility(View.GONE);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(dataset[position]);
            holder.button.setVisibility(View.INVISIBLE);
            et_show = false;

        } else {
            holder.name.setVisibility(View.GONE);
            holder.last.setVisibility(View.VISIBLE);
            holder.input.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(this);
            et_show = true;
            //Log.d(holder.input.getText().toString(),holder.hexcode_tv.getText().toString());
        }

    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext() , Main4Activity.class);
        intent.putExtra("emotion", dataset);
        view.getContext().startActivity(intent);
        ((Main3Activity) view.getContext()).finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ColorPickerView.OnColorChangedListener {
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



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sharedPreferences = itemView.getContext().getSharedPreferences("sFile",MODE_PRIVATE);
            num = itemView.findViewById(R.id.num);
            name = itemView.findViewById(R.id.name);
            last = itemView.findViewById(R.id.last);
            input = itemView.findViewById(R.id.input);
            button = itemView.findViewById(R.id.button);

            color_pick = itemView.findViewById(R.id.color_pick);
            colorView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.activity_intro_colorpick, null);
            color_pick.addView(colorView);
            colortest = color_pick.findViewById(R.id.colorPickerView_test);

            R_tv = color_pick.findViewById(R.id.R_et);
            G_tv = color_pick.findViewById(R.id.G_et);
            B_tv = color_pick.findViewById(R.id.B_et);
            intro_sb_brightness = color_pick.findViewById(R.id.intro_sb_brightness);
            intro_sb_chroma = color_pick.findViewById(R.id.intro_sb_chroma);
            hexcode_tv = color_pick.findViewById(R.id.hex_code_et);
            colortest.init(this,color_circle_set[0],color_circle_set[1],color_circle_set[2]);

            //명도 변경
            intro_sb_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float[] hsv = new float[3];
                    Color.RGBToHSV(red, green, blue, hsv);
                    hsv[2] = (float) (intro_sb_brightness.getProgress()) / 100;
                    colortest.changeValue(ViewHolder.this::colorChanged, hsv);
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
                    colortest.changeChroma(ViewHolder.this::colorChanged, hsv);
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
                    Log.d("last",last_emotion);
                    dataset[7] = last_emotion;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(input.getText().toString(),hexcode_tv.getText().toString());
                    editor.commit();
                }
            });

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

            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (et_show) //사용자 정의 감정 색 선택
                editor.putString(input.getText().toString(), hexcode_tv.getText().toString());

            else // 정해진 감정 색 선택
                editor.putString(name.getText().toString(), hexcode_tv.getText().toString());
            editor.commit();

            Log.d("emottt1", sharedPreferences.getString(input.getText().toString(),"#0000000"));
            Log.d("emottt", sharedPreferences.getString(name.getText().toString(),"#0000000"));
        }
    }

}
