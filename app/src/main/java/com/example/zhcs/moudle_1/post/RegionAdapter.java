package com.example.zhcs.moudle_1.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.RegionBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.MHolder>{

    private Context context;
    private List<RegionBean.RowsDTO> mdata;

    public RegionAdapter(Context context, List<RegionBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_region,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        RegionBean.RowsDTO data = mdata.get(position);

        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.guide1).transform(new CenterCrop()).into(holder.img);

        holder.title.setText(data.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RegionDetailActivity.class);
                intent.putExtra("id",data.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView title;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }
    }

}
