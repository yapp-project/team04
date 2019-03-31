package yapp14th.co.kr.myplant.recyclerView;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import yapp14th.co.kr.myplant.R;
import yapp14th.co.kr.myplant.ui.intro.IntroColorPickActivity;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private String[] dataset;
    private String extra;

    public MyAdapter(String[] dataset) {
        this.dataset = dataset;
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
        holder.num.setText("0" + (position+1));
        if(position != 7) {
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
        holder.textView.setText(dataset[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), IntroColorPickActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext() , Main4Activity.class);
        intent.putExtra("name", extra);
        view.getContext().startActivity(intent);
        ((Main3Activity)view.getContext()).finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView num;
        public TextView name;
        public CardView last;
        public EditText input;
        public TextView textView;
        public Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num);
            name = itemView.findViewById(R.id.name);
            last = itemView.findViewById(R.id.last);
            input = itemView.findViewById(R.id.input);
            textView = itemView.findViewById(R.id.color_text);
            button = itemView.findViewById(R.id.button);

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
    }
}
