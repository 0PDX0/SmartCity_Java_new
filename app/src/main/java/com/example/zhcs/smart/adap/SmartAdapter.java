package com.example.zhcs.smart.adap;

import android.content.Context;
import android.content.Intent;
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
import com.example.zhcs.person.FeedbackActivity;
import com.example.zhcs.person.ModifyPwdActivity;
import com.example.zhcs.person.OrderActivity;
import com.example.zhcs.person.PersonMsgActivity;
import com.example.zhcs.smart.partisan.DangJianActivity;
import com.example.zhcs.smart.community.ShequActivity;
import com.example.zhcs.smart.environ.HuanbaoActivity;
import com.example.zhcs.smart.model.KaimoActivity;
import com.example.zhcs.smart.old.YanglaoActivity;
import com.example.zhcs.smart.produce.ZhizaoActivity;
import com.example.zhcs.smart.relief.CasePubActivity;
import com.example.zhcs.smart.relief.FupingActivity;
import com.example.zhcs.smart.relief.HelpActivity;
import com.example.zhcs.smart.relief.InterviewActivity;
import com.example.zhcs.smart.relief.RelieCaseActivity;
import com.example.zhcs.smart.relief.VillageActivity;

public class SmartAdapter extends RecyclerView.Adapter<SmartAdapter.MHolder>{

    private Context context;
    private int[] imgs;
    private String[] imgss;
    private String[] names;
    private Drawable[] drawables;
    private int[] colors;

    /*设置两个构造方法方便看是使用网络图片还是本地图片*/
    public SmartAdapter(Context context, int[] imgs, String[] names){
        this.context = context;
        this.imgs = imgs;
        this.names = names;
    }

    public SmartAdapter(Context context, int[] imgs, String[] names, int[] colors){
        this.context = context;
        this.imgs = imgs;
        this.names = names;
        this.colors = colors;
    }

    public SmartAdapter(Context context, String[] imgss, String[] names){
        this.context = context;
        this.imgss = imgss;
        this.names = names;
    }

    public SmartAdapter(Context context, Drawable[] drawables, String[] names){
        this.context = context;
        this.drawables = drawables;
        this.names = names;
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
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_smart,parent,false));
    }

    /**
     * onBindViewHolder()：负责将每个子项holder绑定数据。
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        holder.tv.setText(names[position]);

        if (imgs != null){
            Glide.with(context).load(imgs[position]).
                    error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);

            if (colors != null){
                holder.img.setColorFilter(colors[position]);
            }
            //        holder.img.setImageResource(imgs[position]);
        }else if (imgss != null){
            Glide.with(context).load(imgss[position]).
                    error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);

        }else if (drawables != null){
            Glide.with(context).load(drawables[position]).
                    error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);
        }

        /*TODO */
//        if (names[position].equals("垃圾物品展示")){
//            holder.itemView.setPadding(0,100,0,0);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.tv.getText().toString();

                /*智慧城市*/
                if (name.equals("精准扶贫")){
                    context.startActivity(new Intent(context, FupingActivity.class));
                }else if (name.equals("时代楷模")){
                    context.startActivity(new Intent(context, KaimoActivity.class));
                }else if (name.equals("智慧党建")){
                    context.startActivity(new Intent(context, DangJianActivity.class));
                }else if (name.equals("智慧环保")){
                    context.startActivity(new Intent(context, HuanbaoActivity.class));
                }else if (name.equals("智慧社区")){
                    context.startActivity(new Intent(context, ShequActivity.class));
                }else if (name.equals("智慧养老")){
                    context.startActivity(new Intent(context, YanglaoActivity.class));
                }else if (name.equals("中国制造")){
                    context.startActivity(new Intent(context, ZhizaoActivity.class));
                }

                /*智慧城市中精准扶贫*/
                else if (name.equals("扶贫案例")){
                    context.startActivity(new Intent(context, RelieCaseActivity.class));
                }else if (name.equals("村情村貌")){
                    context.startActivity(new Intent(context, VillageActivity.class));
                }else if (name.equals("收到求助")){
                    context.startActivity(new Intent(context, HelpActivity.class));
                }else if (name.equals("入户走访")){
                    context.startActivity(new Intent(context, InterviewActivity.class));
                }else if (name.equals("案例发布")){
                    context.startActivity(new Intent(context, CasePubActivity.class));
                }

                /*个人中心*/
                else if (name.equals("个人信息")){
                    context.startActivity(new Intent(context, PersonMsgActivity.class));
                }else if (name.equals("修改密码")){
                    context.startActivity(new Intent(context, ModifyPwdActivity.class));
                }else if (name.equals("我的订单")){
                    context.startActivity(new Intent(context, OrderActivity.class));
                }else if (name.equals("意见反馈")){
                    context.startActivity(new Intent(context, FeedbackActivity.class));
                }

                /*时代楷模*/
                else if (name.equals("楷模列表")){

                }else if (name.equals("英雄故事")){

                }else if (name.equals("学习心得")){

                }else if (name.equals("公益活动")){

                }else if (name.equals("身边的英雄")){

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    class MHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView tv;

        public MHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }

    }
}
