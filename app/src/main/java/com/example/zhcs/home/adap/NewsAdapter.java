package com.example.zhcs.home.adap;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.NewsBean;
import com.example.zhcs.home.NewsDetailActivity;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MHolder>{

    private Context context;
    private List<NewsBean.RowsDTO> mdata;

    /*构造方法，传入上下文与新闻列表使用*/
    public NewsAdapter(Context context, List<NewsBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }


    /**
     * onCreateViewHolder():负责承载每个子项的布局
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_news,parent,false));
    }


    /**
     * onBindViewHolder()：负责将每个子项holder绑定数据。俩参数是"RecyclerView.ViewHolder holder" , "int position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        /*绑定每个标题导航对应的新闻列表，这里将构造方法传入的新闻列表的集合单个单个取出来*/
        final NewsBean.RowsDTO data = mdata.get(position);

        /*加载网络图片，记得加上域名，.error(若是加载失败就加载这张图片)*/
        /*RequestOptions的transforms方法可以同时加载多个Transformation，进行复合变换。不然没法又设置圆角又设置缩放，要放在他这一起*/
        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getCover())
                .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);

        /*设置它的标题*/
        holder.name.setText(data.getTitle());

        /*设置它的主要内容，主题部分 Html.fromhtml方法，意思是可以将比如文本 框中的字符串进行HTML格式化，*/
        /*其中两个的flags所代表的意思分别是：
          FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
          FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔*/
        holder.content.setText(Html.fromHtml(data.getContent(),Html.FROM_HTML_MODE_COMPACT));


        /*设置评论数*/
        holder.comment.setText(data.getCommentNum() + "条评论");

        /*设置发布时间*/
        holder.data.setText(data.getPublishDate() + "发布");

        //设置它每条数据主体的图片点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*跳转新闻详细页*/
                Intent intent = new Intent(context, NewsDetailActivity.class);
                /*将当前新闻数据的id传递过去,给那边做回显使用*/
                intent.putExtra("id",data.getId());
                /*跳转*/
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView content;
        private TextView comment;
        private TextView data;

        public MHolder(View view){
            super(view);
            /*获取传入的View视图下的控件实例*/
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            content = view.findViewById(R.id.content);
            comment = view.findViewById(R.id.comment);
            data = view.findViewById(R.id.data);

        }

    }
}


































