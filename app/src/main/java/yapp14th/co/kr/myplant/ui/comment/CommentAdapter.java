package yapp14th.co.kr.myplant.ui.comment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.components.ColorPickerView;
import yapp14th.co.kr.myplant.recyclerView.Main3Activity;
import yapp14th.co.kr.myplant.recyclerView.Main4Activity;
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO;
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil;

import static android.content.Context.MODE_PRIVATE;

// 도데체 왜 데바가 안먹히는 지 모르겠음.......
class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements View.OnClickListener {
    CDayVO[] dataSet;

    public CommentAdapter(CDayVO[] dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(dataSet[position].getComment());
        CDayVO item = dataSet[position];

        int emotionType = item.getEmotionType();
        int emotionColor = Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_$emotionType"));
        holder.iv_color.setColorFilter(emotionColor, PorterDuff.Mode.SRC);

        // holder.tv_color.setText(item.getEmotionStr((short) emotionType));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, item.getYear());
        calendar.set(Calendar.YEAR, item.getMonth());
        calendar.set(Calendar.YEAR, item.getDay());

        holder.tv_date.setText(sdf.format(calendar));
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Main4Activity.class);
        intent.putExtra("emotion", dataSet);
        view.getContext().startActivity(intent);
        ((Main3Activity) view.getContext()).finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        ImageView iv_color;
        TextView tv_color;
        TextView tv_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            iv_color = itemView.findViewById(R.id.iv_color);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

    }

}
