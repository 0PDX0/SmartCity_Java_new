package com.example.zhcs.home.adap;

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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.NewsBean;
import com.example.zhcs.home.NewsDetailActivity;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MHolder>{

    private Context context;
    private List<NewsBean.RowsDTO> mdata;

    public ThemeAdapter(Context context, List<NewsBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    /**
     * onCreateViewHolder()：负责承载每个子项的布局
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*设置每条热点新闻使用的View*/
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_theme,parent,false));
    }

    /**
     * onBindViewHolder()：负责将每个子项holder绑定数据。俩参数是"RecyclerView.ViewHolder holder" , "int position"
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        /*获取到每个索引的热点新闻数据*/
        final NewsBean.RowsDTO data = mdata.get(position);

        /*拼接网络图片路径使用Glide加载*/
        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getCover())
                .error(R.mipmap.resource).transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.img);

        /*设置新闻的标题*/
        holder.name.setText(data.getTitle());

        /*设置每条热点新闻的图片点击事件监听*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*这里也是跳转新闻通用的跳转类*/
                Intent intent = new Intent(context, NewsDetailActivity.class);
                /*记得将这条新闻的id值给传递过去*/
                intent.putExtra("id",data.getId());
                /*页面跳转*/
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class MHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name;

        public MHolder(View view){
            super(view);

            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
        }
    }
}






















