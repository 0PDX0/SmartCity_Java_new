package com.example.zhcs.smart.adap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zhcs.R;

public class LitterAdapter extends RecyclerView.Adapter<LitterAdapter.MHolder>{

    private int[] imgs;
    private String[] names;
    private Context context;

    public LitterAdapter(Context context, int[] imgs, String[] names){
        this.context = context;
        this.imgs = imgs;
        this.names = names;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_litter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        holder.name.setText(names[position]);

        Glide.with(context).load(imgs[position])
                .error(R.mipmap.huanbao).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    public class MHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }
}
