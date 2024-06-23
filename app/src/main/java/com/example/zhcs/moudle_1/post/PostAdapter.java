package com.example.zhcs.moudle_1.post;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.example.zhcs.bean.PostBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MHolder>{

    private Context context;
    private List<PostBean.RowsDTO> mdata;

    public PostAdapter(Context context, List<PostBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        PostBean.RowsDTO data = mdata.get(position);

        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getCoverImgUrl())
                .transform(new CenterCrop()).into(holder.img);

        holder.name.setText(data.getName());

        holder.beds.setText("剩余床位：男|" + data.getBedsCountBoy() + " 女|" + data.getBendCountGirl());

        holder.address.setText(data.getAddress());

        holder.content.setText(data.getIntroduce());

        holder.up_down.setTag("0");
        holder.up_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.up_down.getTag().toString().equals("0")){

                    holder.content.setVisibility(View.VISIBLE);
                    holder.up_down.setText("隐藏介绍");

                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24, context.getTheme());
                    drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    drawable.setTint(Color.GRAY);   //必须设置颜色，也是服了
//                    holder.up_down.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null);
                    holder.up_down.setCompoundDrawables(drawable,null,null,null);

                    holder.up_down.setTag("1");
                }else {

                    holder.content.setVisibility(View.GONE);
                    holder.up_down.setText("显示介绍");


                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24, context.getTheme());
                    drawable.setTint(Color.GRAY);

                    holder.up_down.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null);

                    holder.up_down.setTag("0");
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PostDetailActivity.class);
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
        private TextView name;
        private TextView beds;
        private TextView address;
        private TextView content;
        private TextView up_down;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            beds = itemView.findViewById(R.id.beds);
            address = itemView.findViewById(R.id.address);
            content = itemView.findViewById(R.id.content);
            up_down = itemView.findViewById(R.id.up_down);

        }
    }

}
