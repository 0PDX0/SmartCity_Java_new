package com.example.zhcs.moudle_1.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;
import com.example.zhcs.bean.PolicyBean;
import com.example.zhcs.bean.RegionDetailBean;

import java.util.List;

public class RegionDetailAdapter extends RecyclerView.Adapter<RegionDetailAdapter.MHolder>{

    private Context context;
    private List<PolicyBean.DataDTO> mdata;

    public RegionDetailAdapter(Context context, List<PolicyBean.DataDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_regiondetail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        PolicyBean.DataDTO data = mdata.get(position);

        holder.title.setText(data.getTitle());

        holder.date.setText(data.getCreateTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PolicyActivity.class);
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

        private TextView title;
        private TextView date;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
        }
    }

}
