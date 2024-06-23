package com.example.zhcs.moudle_2.subway;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;

import java.util.List;

public class SubwayDetaAdapter extends RecyclerView.Adapter<SubwayDetaAdapter.MHolder>{

    private Context context;
    private List<String> names;
    private String currentName;

    public SubwayDetaAdapter(Context context, List<String> names, String currentName){
        this.context = context;
        this.names = names;
        this.currentName = currentName;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_subwaydeta,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        holder.name.setText(names.get(position));

        if (currentName.equals(names.get(position))){
            holder.img.setColorFilter(Color.RED);
        }else {
            holder.img.setColorFilter(Color.parseColor("#0277BD"));
        }

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }
}
