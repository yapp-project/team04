package yapp14th.co.kr.myplant.recyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextUtils;
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


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.components.ColorPickerView;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

import static android.content.Context.MODE_PRIVATE;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    boolean mypage = false;
    private String[] dataset;
    private int[] color_circle_set;

   private int red;
   private int blue;
   private int green;

   private String last_emotion;
   private Boolean input_empty = true;
   private int chrome_seekbar;
   private int brightness_seekbar;
   private Boolean chorme_sb_touch = false;
   private Boolean brigntness_sb_touch= false;

    String prevLast;
    String[] prevColorSet;

    float[] prevPinXSet;
    float[] prevPinYSet;

    public MyAdapter(boolean mypage, String[] dataset, int[] color_circle_set) {
        this.mypage = mypage;
        this.dataset = dataset;
        this.color_circle_set = color_circle_set;
    }

    public MyAdapter(boolean mypage, String[] dataset, int[] color_circle_set, String prevLast, String[] prevColorSet, float[] prevPinXSet, float[] prevPinYSet) {
        this.mypage = mypage;
        this.dataset = dataset;
        this.color_circle_set = color_circle_set;
        this.prevLast = prevLast;
        this.prevColorSet = prevColorSet;
        this.prevPinXSet = prevPinXSet;
        this.prevPinYSet = prevPinYSet;
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
        holder.colortest.positionInit(position);

        if (position != 7) {
            holder.last.setVisibility(View.GONE);
            holder.input.setVisibility(View.GONE);
//            holder.input.clearFocus();
            holder.name.setVisibility(View.VISIBLE);
//            holder.name.requestFocus();
            holder.name.setText(dataset[position]);
            holder.button.setVisibility(View.INVISIBLE);
        } else {
            holder.name.setVisibility(View.GONE);
            holder.last.setVisibility(View.VISIBLE);
            holder.input.setVisibility(View.VISIBLE);
            holder.input.setText(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last));
//            holder.input.requestFocus(View.FOCUS_DOWN);
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(this);
        }

        if (prevColorSet != null) {
            int color = Color.parseColor(prevColorSet[position]);
            int alpha = Color.alpha(color);
            blue = Color.blue(color);
            green = Color.green(color);
            red = Color.red(color);
            Log.d("color$position : ", alpha + "" + blue + "" + green + "" + red);

            // 1. 색상 초기값 설정
            holder.colorChanged(color, red, green, blue);
            holder.colortest.colorInit(color, red, green, blue);

            // 2. 명도 초기값 설정
            float[] hsv = new float[3];
            Color.RGBToHSV(red, green, blue, hsv);
            holder.intro_sb_brightness.setProgress((int) (hsv[2] * 100));

            // 3. 채도 초기값 설정
            holder.intro_sb_chroma.setProgress((int) (hsv[1] * 100));
        }
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public void onClick(View view) {

        dataset[7] = last_emotion;

        SharedPreferenceUtil.setData("last", last_emotion);


        if (input_empty) //마지막 감정 이름 지정이 되어있지 않은 경우
            Toast.makeText(view.getContext(), "감정의 이름을 지정해 주세요.", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(view.getContext(), Main4Activity.class);
            intent.putExtra("emotion", dataset);
            intent.putExtra("mypage", mypage);
            view.getContext().startActivity(intent);
            ((Main3Activity) view.getContext()).finish();
        }
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

            num = itemView.findViewById(R.id.num);
            name = itemView.findViewById(R.id.name);
            last = itemView.findViewById(R.id.last);
            input = itemView.findViewById(R.id.et_input);
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
            colortest.init(this, color_circle_set[0], color_circle_set[1], color_circle_set[2]);

            //명도 변경
            intro_sb_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    brigntness_sb_touch = true;
                    brightness_seekbar = seekBar.getProgress();
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
                    chorme_sb_touch = true;
                    chrome_seekbar = seekBar.getProgress();
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

                    last_emotion = input.getText().toString();
                    //마지막 감정 edit text 비어있는지 확인
                    if (TextUtils.isEmpty(last_emotion))
                        input_empty = true;
                    else
                        input_empty = false;
                    Log.d("last", last_emotion);

                    SharedPreferenceUtil.setData("EMOTION_" + String.valueOf(getAdapterPosition() + 1), hexcode_tv.getText().toString());

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

            intro_sb_chroma.setProgress(chrome_seekbar);
            intro_sb_brightness.setProgress(brightness_seekbar);
            if(brigntness_sb_touch == false && chorme_sb_touch == false){
                chrome_seekbar = 100;
                brightness_seekbar = 100;
            }
            else{
                if(brigntness_sb_touch)
                    brigntness_sb_touch = false;
                else if(chorme_sb_touch)
                    chorme_sb_touch = false;
            }

            SharedPreferenceUtil.setData("EMOTION_"+String.valueOf(getAdapterPosition()+1),hexcode_tv.getText().toString());
            Log.d("emottt1", "EMOTION_"+String.valueOf(getAdapterPosition()+1));
            Log.d("emottt", SharedPreferenceUtil.getStringData(String.valueOf("EMOTION_"+String.valueOf(getAdapterPosition()+1))));
        }
    }

}