package com.example.zhcs.moudle_1.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.CommentBean;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MHolder>{

    private Context context;
    private List<CommentBean.RowsDTO> mdata;

    public CommentAdapter(Context context,List<CommentBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        CommentBean.RowsDTO data = mdata.get(position);

        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getAvatar())
                .error(R.mipmap.user_head).transform(new CircleCrop()).into(holder.img);

        holder.name.setText(data.getUserName());

        holder.date.setText(data.getCommentTime() + "评论");

        holder.content.setText(data.getContent());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private TextView date;
        private TextView content;

        public MHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            content = itemView.findViewById(R.id.content);
        }
    }

}
