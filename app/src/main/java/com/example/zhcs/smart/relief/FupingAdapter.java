package com.example.zhcs.smart.relief;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.FupingBean;
import com.example.zhcs.home.NewsDetailActivity;

import java.util.List;

public class FupingAdapter extends RecyclerView.Adapter<FupingAdapter.MHolder>{

    private Context context;
    private List<FupingBean> mdata;
    boolean flag;

    public FupingAdapter(Context context, List<FupingBean> mdata, boolean flag){
        this.context = context;
        this.mdata = mdata;
        this.flag = flag;
    }

    public FupingAdapter(Context context, List<FupingBean> mdata){
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
        return new MHolder(LayoutInflater.from(context).inflate(R.layout.item_fuping,parent,false));
    }

    /**
     * 负责将每个子项holder绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {

        /*根据用户点击的新闻索引获取出对应的新闻数据*/
        final FupingBean data = mdata.get(position);

        /*设置新闻的标题*/
        holder.name.setText(data.getTitle());

        /*设置新闻的正文*/
        holder.content.setText(data.getContent());

        /*设置新闻的发布时间*/
        holder.data.setText(data.getDate() + "发布");

        /*判断每条新闻的数据是否是软件上的位图资源*/
        if (data.getImg() == null){
            /*上面判断为空，再判断每条新闻的数据是否是本机的路径地址的*/
            if (data.getPath() == null){
                /*如果都没有图片数据就将图片隐藏起来*/
                holder.img.setVisibility(View.GONE);
            }else {
                /*这里进来就代表是有本机的路径地址的 BitmapFactory.decodeFile()文件转图片
                * BitmapFactory.decodeFile()是一个用于从文件中读取图像数据并生成Bitmap对象的方法。
                * 它可以根据提供的文件路径或文件描述符来读取图像数据，并根据提供的选项进行解码和缩放。
                * 但是，由于图像文件可能非常大，因此在读取和解码图像数据时可能会出现内存溢出的问题。
                * 为了解决这个问题，可以使用BitmapFactory.Options类中的inJustDecodeBounds
                * 和inSampleSize选项来控制图像的解码和缩放过程，以减少内存使用量。*/

                /*decodeResource()[第一个参数一般getResource就行了，第二个参数你要加载的位图资源的id]：可以将/res/drawable/内预先存入的图片转成bitmap对象*/
                Glide.with(context).load(BitmapFactory.decodeFile(data.getPath()))
                        .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);
            }
        }else {
            /*这里进来就代表是有软件的位图资源的*/
            Glide.with(context).load(data.getImg())
                    .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.img);
        }


        /*给点赞按钮设置点击事件*/
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT).show();

                /*android:setClickable方法一定要放在setOnClickListener事件之后，
                以为在setOnClickListener的时候会去重写View，因为默认的setClickable为true，
                所以我们一定要在setOnClickListener事件之后，再进行setClickable的设置。*/
                //.setClickable(false)：只有第一次点击将被处理(在意外双击/多次点击的情况下，忽略额外的点击)。
                //总的来说，setEnabled是按钮的总开关
                //setEnabled（true）时，setClickable（true），点击按钮是会响应点击事件的
                //setEnabled（true）时，setClickable（false），此时，即便设置了不可点击，但是也是会一闪一闪，因为setEnabled（true）
                //setClickable是单个按钮的开关，
                //为true是可以点击
                //为false是不可点击
                //之所以记录这个，是因为接手的项目，验证码只能获取一次，查看代码发现，设置了setEnabled（false）,所以后来的setClickable（true）都是无效的
                holder.likeBtn.setClickable(false); //说简单点，就是要在设置点击事件监听后设置这个，否则是没有效果的

//                /*设置背景的颜色”滤镜“,这里设置为灰色*/
                holder.likeBtn.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC);

            }
        });


        /*给新闻视图设置点击事件监听*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*设置跳转新闻详细页的意图*/
                Intent intent = new Intent(context, FupingDetailActivity.class);
                /*将新闻数据传递过去*/
                intent.putExtra("data",data);
                /*页面跳转*/
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView content;
        private Button likeBtn;
        private TextView data;
        private ImageView img;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            likeBtn = itemView.findViewById(R.id.like_btn);
            data = itemView.findViewById(R.id.data);
            img = itemView.findViewById(R.id.img);

        }
    }
}
