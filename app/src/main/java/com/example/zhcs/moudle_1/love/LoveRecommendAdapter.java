package com.example.zhcs.moudle_1.love;

import android.content.Context;
import android.text.Layout;
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
import com.example.zhcs.bean.LoveRecommendBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class LoveRecommendAdapter extends RecyclerView.Adapter<LoveRecommendAdapter.MHolder>{

    private Context context;
    private List<LoveRecommendBean.RowsDTO> mdata;

    public LoveRecommendAdapter(Context context, List<LoveRecommendBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_loverecommend,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        LoveRecommendBean.RowsDTO data = mdata.get(position);

        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);

        holder.name.setText(data.getName());

        holder.organ.setText("发布组织: \u0020" + data.getAuthor());

        holder.don.setText(data.getDonateCount() + "人已捐助，共捐助" + data.getMoneyNow() + "元");

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name;
        private TextView organ;
        private TextView don;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            organ = itemView.findViewById(R.id.organ);
            don = itemView.findViewById(R.id.don);
        }
    }

}
