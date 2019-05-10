package yapp14th.co.kr.myplant.recyclerView;

import android.content.Context;
import android.content.Intent;
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


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private String[] dataset;
    private String extra;


    private int[] colorset;
    int red;
    int blue;
    int green;
    int color;


    public MyAdapter(String[] dataset, int[] colorset) {
        this.dataset = dataset;
        this.colorset = colorset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.num.setText("0" + (position + 1));
        if (position != 7) {
            holder.last.setVisibility(View.GONE);
            holder.input.setVisibility(View.GONE);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(dataset[position]);
            holder.button.setVisibility(View.INVISIBLE);
        } else {
            holder.name.setVisibility(View.GONE);
            holder.last.setVisibility(View.VISIBLE);
            holder.input.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(this);

        }

    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Main4Activity.class);
        intent.putExtra("name", extra);
        view.getContext().startActivity(intent);
        ((Main3Activity) view.getContext()).finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ColorPickerView.OnColorChangedListener {
        public TextView num;
        public TextView name;
        public CardView last;
        public EditText input;
        public Button button;

        public LinearLayout color_pick;
        public View colorView;
        public ColorPickerView colortest;
        public TextView R_tv;
        public TextView G_tv;
        public TextView B_tv;
        public TextView hexcode_tv;
        public SeekBar intro_sb_brightness;
        public SeekBar intro_sb_chroma;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
            colortest.init(this, colorset[0], colorset[1], colorset[2]);

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

                    extra = input.getText().toString();
                }
            });

        }

        public void colorChanged(int color, int red, int green, int blue) {

            red = red;
            green = green;
            blue = blue;
            color = color;
            R_tv.setText(String.valueOf(red));
            G_tv.setText(String.valueOf(green));
            B_tv.setText(String.valueOf(blue));
            hexcode_tv.setText(String.format("#%04X", color));
            intro_sb_brightness.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            intro_sb_chroma.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

}
