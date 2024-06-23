package com.example.zhcs.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MHolder>{

    private Context context;
    private List<String> mdata;
    private List<String> titles;
    RetrofitUtil.OnRequest onRequest;

    /*根据需求构造出了多个构造方法来使用*/
    public CommonAdapter(Context context,List<String> mdata, List<String> titles){
        this.context = context;
        this.mdata = mdata;
        this.titles = titles;
    }

    public CommonAdapter(Context context, List<String> mdata, List<String> titles, RetrofitUtil.OnRequest onRequest){
        this.context = context;
        this.mdata = mdata;
        this.titles = titles;
        this.onRequest = onRequest;
    }

    public CommonAdapter(Context context, List<String> mdata, RetrofitUtil.OnRequest onRequest){
        this.context = context;
        this.mdata = mdata;
        this.onRequest = onRequest;
    }

    public CommonAdapter(Context context, List<String> mdata){
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
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_common,parent,false));
    }

    /**
     * onBindViewHolder()：负责将每个子项holder绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, @SuppressLint("RecyclerView") int position) {
        /*如果用户没有传入标题信息就隐藏标题信息，传入了就设置标题信息(停哪服务这个标题是停车场名称)*/
        if (titles == null){
            holder.title.setVisibility(View.GONE);
        }else {
            holder.title.setText(titles.get(position));
        }

        /*判断用户是否传入了主体数据，如果没有传入就将主体数据给隐藏起来并将标题的内边距给缩小(本来内边距是15dp)*/
        if (mdata == null){
            holder.content.setVisibility(View.GONE);
            holder.title.setPadding(5,5,5,5);
        }else {
            String data = mdata.get(position);

            /*传入了数据再次进行判断是否有html标签，如果有再将传入的数据解析html标签*/
            if (data.contains("<b>")){
                /*有html标签，解析后再填充数据*/
                holder.content.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY));
            }else {
                /*没有html标签，直接填充数据*/
                holder.content.setText(data);
            }
        }

        /*给每个小框框设置点击事件，并借用这个传入的OnRequest对象使用方法进行那边的回调*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRequest != null){
                    /*将当前小框框的索引返回回去*/
                    onRequest.onRequest(position + "");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mdata != null){
            return mdata.size();
        }
        return titles.size();
    }

    public class MHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;

        public MHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

        }
    }
}
















