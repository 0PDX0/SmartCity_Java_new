package com.example.zhcs.moudle_2.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;

import java.util.List;

public class SubwayMapAdapter extends RecyclerView.Adapter<SubwayMapAdapter.MHolder>{

    private Context context;
    private List<String> names;
    private int[] colors;

    public SubwayMapAdapter(Context context, List<String> names, int[] colors){
        this.context = context;
        this.names= names;
        this.colors = colors;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_subwaymap,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        holder.line.setText(names.get(position));

        holder.view.setBackgroundColor(colors[position]);

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView line;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.view);
            line = itemView.findViewById(R.id.line);

        }
    }

}
