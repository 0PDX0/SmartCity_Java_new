package com.example.zhcs.moudle_1.event;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.example.zhcs.bean.EventBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MHolder>{

    private Context context;
    private List<EventBean.RowsDTO> mdata;

    public EventAdapter(Context context, List<EventBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_event,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        EventBean.RowsDTO data = mdata.get(position);

        /*活动宣传图*/
        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);

        /*活动名称*/
        holder.name.setText(data.getName());

        /*活动说明*/
        if (data.getContent().contains("<p>")){
            holder.content.setText(Html.fromHtml(data.getContent(),Html.FROM_HTML_MODE_LEGACY));
        }else {
            holder.content.setText(data.getContent());
        }

        /*点赞人数*/
        holder.like.setText(data.getLikeNum() + "人点赞");

        /*报名人数*/
        holder.signup.setText(data.getSignupNum() + "人报名");

        /*给所有活动添加点击事件，将该活动的id传递过去*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EventDetailActivity.class);
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
        private TextView content;
        private TextView like;
        private TextView signup;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            like = itemView.findViewById(R.id.like);
            signup = itemView.findViewById(R.id.signup);

        }
    }
}



















