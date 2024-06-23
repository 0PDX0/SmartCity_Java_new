package com.example.zhcs.home.adap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.MainActivity;
import com.example.zhcs.R;
import com.example.zhcs.bean.ServiceBean;
import com.example.zhcs.moudle_1.event.EventActivity;
import com.example.zhcs.moudle_1.gover.GoverActivity;
import com.example.zhcs.moudle_1.house.HouseActivity;
import com.example.zhcs.moudle_1.lib.LibraryActivity;
import com.example.zhcs.moudle_1.love.LoveActivity;
import com.example.zhcs.moudle_1.park.ParkActivity;
import com.example.zhcs.moudle_1.patient.HospitalActivity;
import com.example.zhcs.moudle_1.post.PostActivity;
import com.example.zhcs.moudle_1.rubbish.RubActivity;
import com.example.zhcs.moudle_2.bus.BusActivity;
import com.example.zhcs.moudle_2.law.LawerActivity;
import com.example.zhcs.moudle_2.move.MoveActivity;
import com.example.zhcs.moudle_2.movie.MovieActivity;
import com.example.zhcs.moudle_2.pet.PetActivity;
import com.example.zhcs.moudle_2.sub_car.SubCarActivity;
import com.example.zhcs.moudle_2.subway.SubwayActivity;
import com.example.zhcs.moudle_2.volunteer.VolunActivity;
import com.example.zhcs.moudle_3.illegal.IllegalActivity;
import com.example.zhcs.moudle_3.law.KitchenActivity;
import com.example.zhcs.moudle_3.pay.CallQueryActivity;
import com.example.zhcs.moudle_3.work.WorkActivity;
import com.example.zhcs.util.SPUtil;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MHolder>{

    private Context context;
    private List<ServiceBean.RowsDTO> mdata;
    private boolean flag;

    public ServiceAdapter(Context context, List<ServiceBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    public ServiceAdapter(Context context, List<ServiceBean.RowsDTO> mdata, boolean flag){
        this.context = context;
        this.mdata = mdata;
        this.flag = flag;
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
        /*实例化封装一步到位*/
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_service,parent,false));
    }

    /**
     * onBindViewHolder()：负责将每个子项holder绑定数据。两参数是"RecyclerView.ViewHolder holder" , "int position"
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        /*根据点击的是哪个服务的索引拿到哪一个服务的数据*/
        final ServiceBean.RowsDTO data = mdata.get(position);

        /**/
        if (flag && position == mdata.size() - 1){
            /*设置更多服务的图标*/
            holder.img.setImageResource(R.mipmap.more);

            holder.img.setColorFilter(Color.BLACK);

            holder.name.setText("更多服务");

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*设置点击更多服务将当前ViewPager设置为第二个页面也就是全部服务页面*/
                    ((MainActivity)context).ser();
                }
            });

            return;
        }

        /*拼接网络图片地址*/
        Glide.with(context).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                .error(R.mipmap.more3).transform(new CenterCrop()).into(holder.img);


        /*渲染服务的名字*/
        holder.name.setText(data.getServiceName());

        /*给每个服务设置点击事件监听*/
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*将点击的服务名称获取出来*/
                String serviceName = data.getServiceName();

                /*进行比较，判断是哪个服务就进行哪个跳转*/
                /*moudle_1*/
                if (serviceName.equals("停哪儿")){
                    context.startActivity(new Intent(context, ParkActivity.class));
                } else if (serviceName.equals("数字图书馆")){
                    context.startActivity(new Intent(context, LibraryActivity.class));
                } else if (serviceName.equals("青年驿站")){
                    context.startActivity(new Intent(context, PostActivity.class));
                } else if (serviceName.equals("活动管理")){
                    context.startActivity(new Intent(context, EventActivity.class));
                } else if (serviceName.equals("爱心捐赠")){
                    context.startActivity(new Intent(context, LoveActivity.class));
                } else if (serviceName.equals("政府服务热线")){
                    context.startActivity(new Intent(context, GoverActivity.class));
                } else if (serviceName.equals("找房子")){
                    context.startActivity(new Intent(context, HouseActivity.class));
                } else if (serviceName.equals("门诊预约")){
                    context.startActivity(new Intent(context, HospitalActivity.class));
                } else if (serviceName.equals("垃圾分类")){
                    context.startActivity(new Intent(context, RubActivity.class));
                }

                /*moudle_2*/
                else if (serviceName.equals("教育")){   //律师咨询
                    context.startActivity(new Intent(context, LawerActivity.class));
                } else if (serviceName.equals("智慧巴士")){
                    context.startActivity(new Intent(context, BusActivity.class));
                } else if (serviceName.equals("外卖订餐")){     //预约检车
                    context.startActivity(new Intent(context, SubCarActivity.class));
                } else if (serviceName.equals("看电影")){
                    context.startActivity(new Intent(context, MovieActivity.class));
                } else if (serviceName.equals("志愿活动")){
                    context.startActivity(new Intent(context, VolunActivity.class));
                } else if (serviceName.equals("宠物医院")){
                    context.startActivity(new Intent(context, PetActivity.class));
                } else if (serviceName.equals("城市地铁")){
                    context.startActivity(new Intent(context, SubwayActivity.class));
                } else if (serviceName.equals("物流查询")){
                    context.startActivity(new Intent(context, MoveActivity.class));
                }

                /*moudle_3*/
                else if (serviceName.equals("智慧交管")){
                    context.startActivity(new Intent(context, IllegalActivity.class));
                } else if (serviceName.equals("生活缴费")){
                    context.startActivity(new Intent(context, CallQueryActivity.class));
                } else if (serviceName.equals("找工作")){
                    context.startActivity(new Intent(context, WorkActivity.class));
                } else if (serviceName.equals("厨房助手")){
                    context.startActivity(new Intent(context, KitchenActivity.class));
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return mdata.size();
    }

    /**
     * 封装的视图控件类，方便使用
     */
    class MHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }
}




























