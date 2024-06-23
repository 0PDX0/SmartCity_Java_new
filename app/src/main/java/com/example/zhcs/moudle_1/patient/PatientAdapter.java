package com.example.zhcs.moudle_1.patient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;
import com.example.zhcs.bean.PatientBean;

import java.io.Serializable;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MHolder>{

    private Context context;
    private List<PatientBean.RowsDTO> mdata;

    public PatientAdapter(Context context, List<PatientBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_patient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        PatientBean.RowsDTO data = mdata.get(position);

        /**/
        holder.title.setText(data.getName());

        /**/
        String desc = "身份证号：" + data.getCardId() +
                "\n手机号：" + data.getTel();

        holder.content.setText(desc);

        /*给模块箭头添加点击事件，点击箭头选择科室预约*/
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,DepartActivity.class));
            }
        });


        /*给子模块添加点击事件*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddPatietnActivity.class);
                intent.putExtra("data", (Serializable) data);
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
        private TextView content;
        private ImageView img;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            img = itemView.findViewById(R.id.img);
        }
    }
}
