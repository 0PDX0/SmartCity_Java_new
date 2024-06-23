package com.example.zhcs.moudle_1.gover;

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
import com.example.zhcs.R;
import com.example.zhcs.bean.LoveTypeBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class AppealTypeAdapter extends RecyclerView.Adapter<AppealTypeAdapter.MHolder>{

    private Context context;
    private List<LoveTypeBean.DataDTO> mdata;

    public AppealTypeAdapter(Context context, List<LoveTypeBean.DataDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    /**
     * 负责每个Holder的布局
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_appeal_type,parent,false));
    }

    /**
     * 负责每个Holder的数据渲染
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        LoveTypeBean.DataDTO data = mdata.get(position);

        holder.name1.setText(data.getName());

        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.resource).into(holder.img1);


        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(data);
            }
        });

        int index = position + (mdata.size() + 1) / 2;
//        int index = (mdata.size() +1 ) >> 1;
        /*判断当前列数据的下面那块数据是否为空，一旦为空就将其图片隐藏掉,不然会显示多余的图片(因为下面的Glide设置了.error)*/
        if (mdata.get(index) == null){
            holder.img2.setVisibility(View.INVISIBLE);  //View.INVISIBLE：隐藏，但保留空间
        }else {
            LoveTypeBean.DataDTO data1 = mdata.get(index);

            holder.name2.setText(data1.getName());

            Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data1.getImgUrl())
                    .error(R.mipmap.resource).into(holder.img2);

            holder.img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action(data);
                }
            });
        }

    }

    private void action(LoveTypeBean.DataDTO data){
        if (data.getName().equals("其他诉求")){
            context.startActivity(new Intent(context,AddAppealActivity.class));
            return;
        }

        Intent intent = new Intent(context,AppealActivity.class);

        intent.putExtra("id",data.getId());

        context.startActivity(intent);
    }

    /**
     * 注意这里的个数是所有服务的一半，
     * @return
     */
    @Override
    public int getItemCount() {
        return (mdata.size() + 1) / 2;
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private TextView name1;
        private ImageView img1;
        private TextView name2;
        private ImageView img2;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            name1 = itemView.findViewById(R.id.name1);
            img1 = itemView.findViewById(R.id.img1);
            name2 = itemView.findViewById(R.id.name2);
            img2 = itemView.findViewById(R.id.img2);

        }
    }
}

















