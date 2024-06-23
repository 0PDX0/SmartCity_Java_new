package com.example.zhcs.smart.relief;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhcs.R;
import com.example.zhcs.bean.FupingBean;

public class FupingDetailActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name;
    private TextView content;
    private TextView date;
    private FupingBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuping_detail);

        /*获取上个页面传递过来的新闻数据对象*/
        data = (FupingBean) getIntent().getSerializableExtra("data");

        /*新闻详细页的新闻图片*/
        img = findViewById(R.id.img);
        /*新闻详细页的标题*/
        name = findViewById(R.id.name);
        /*新闻详细页的新闻主体内容*/
        content = findViewById(R.id.content);
        /*新闻的发布日期*/
        date = findViewById(R.id.date);

        /*初始化数据*/
        initData();
    }

    /**
     * 数据渲染
     */
    private void initData() {

        /*老样子进行新闻图片属性的判断，先判断是否是软件的位图资源*/
        if (data.getImg() == null){
            /*再判断是否是*/
            if (data.getPath() == null){
                img.setVisibility(View.GONE);
            }else {
                Glide.with(content).load(BitmapFactory.decodeFile(data.getPath()))
                        .error(R.mipmap.resource).into(img);

            }
        }else {
            Glide.with(content).load(data.getImg())
                    .error(R.mipmap.resource).into(img);
        }

        /*新闻标题*/
        name.setText(data.getTitle());
        /*新闻主体内容*/
        content.setText(data.getContent());
        /*新闻的发布日期*/
        date.setText(data.getDate() + "发布");

    }

    /**
     * 返回页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}




































