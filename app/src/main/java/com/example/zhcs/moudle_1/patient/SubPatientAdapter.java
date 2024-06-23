package com.example.zhcs.moudle_1.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;
import com.example.zhcs.util.RetrofitUtil;

import java.util.List;

public class SubPatientAdapter extends RecyclerView.Adapter<SubPatientAdapter.MHolder>{

    private Context context;
    private List<String> titles;
    private List<String> arr;
    private RetrofitUtil.OnRequest request;

    public SubPatientAdapter(Context context, List<String> titles, List<String> arr,RetrofitUtil.OnRequest request){
        this.context = context;
        this.titles = titles;
        this.arr = arr;
        this.request = request;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_sub_patient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        /*设置标题*/
        holder.title.setText(titles.get(position));
        /*设置可预约时间*/
        holder.arr.setText(arr.get(position));


        final int index = position;
        /*设置按钮的点击事件监听，将用户点击的列表项索引返回回去*/
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.onRequest(index + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arr == null){
            return titles.size();
        }
        return arr.size();
    }



    public class MHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView arr;
        private Button btn;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            arr = itemView.findViewById(R.id.arr);
            btn = itemView.findViewById(R.id.btn);
        }
    }

}
