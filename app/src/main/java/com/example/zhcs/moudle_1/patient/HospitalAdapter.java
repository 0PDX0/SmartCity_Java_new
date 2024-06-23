package com.example.zhcs.moudle_1.patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.HospitalBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MHolder>{

    private Context context;
    private List<HospitalBean.RowsDTO> mdata;

    public HospitalAdapter(Context context, List<HospitalBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_hospital,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        /*获取出每个医院的信息*/
        HospitalBean.RowsDTO data = mdata.get(position);

        /*设置医院的名字*/
        holder.name.setText(data.getHospitalName());

        /*设置医院的图片*/
        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.resource).transform(new CenterCrop(),new RoundedCorners(20)).into(holder.img);

        /*每次都要清空，不然星星就会越来越多*/
        holder.starList.removeAllViews();
//        holder.starList.removeView();

        /*循环遍历，将星星放入*/
        for (int i = 0; i < data.getLevel(); i++) {
            ImageView imageView = new ImageView(context);
//            imageView.setBackgroundResource(R.drawable.ic_baseline_star_fill);
//            imageView.setColorFilter(Color.YELLOW);

            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_baseline_star_fill,context.getTheme());
            drawable.setTint(Color.YELLOW);
//            drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.OVERLAY); //PorterDuff.Mode.OVERLAY：图片的外面那层
            imageView.setImageDrawable(drawable);

            holder.starList.addView(imageView);
        }

        /*将未五星的医院评分中不足五星的用灰色的星补齐*/
//        if (data.getLevel() < 5){
//            for (int i = 0 ; i < 5 - data.getLevel() ; i++){
//                ImageView imageView = new ImageView(context);
//                Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_baseline_star_fill,context.getTheme());
//                drawable.setTint(Color.GRAY);
//
//                imageView.setImageDrawable(drawable);
//
//                holder.starList.addView(imageView);
//            }
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*准备新闻详细页的意图*/
                Intent intent = new Intent(context,HospitalDetailActivity.class);
                /*将该医院打包带入这个页面*/
                intent.putExtra("data",data);       //Serializable
                /*跳转*/
                context.startActivity(intent);
            }
        });

    }

    /**
     * 统计数量，这个数量就是onBindViewHolder的执行次数
     * @return
     */
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name;
        private LinearLayout starList;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            starList = itemView.findViewById(R.id.star_list);

        }
    }
}
