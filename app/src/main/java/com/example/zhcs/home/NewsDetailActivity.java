package com.example.zhcs.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.NewsDetailBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView name;
    private ImageView img;
    private TextView content;
    private TextView comment;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        /*获取上个页面传递过来过来的跳转详细的id和type*/
        int id = getIntent().getIntExtra("id",11);
        int type = getIntent().getIntExtra("type",0);

        /*新闻的标题*/
        name = findViewById(R.id.name);
        /*新闻的图片*/
        img = findViewById(R.id.img);
        /*新闻的主体内容*/
        content = findViewById(R.id.content);
        /*评论数量*/
        comment = findViewById(R.id.comment);
        /*新闻日期*/
        date = findViewById(R.id.data);

        if (type == 0){
            /*/prod-api/press/press/{id}：根据详细信息id获取新闻详细信息*/
            RetrofitUtil.get("/prod-api/press/press/" + id, new RetrofitUtil.OnRequest() {
                @Override
                public void onRequest(String json) {
                    /*获取返回数据中的data*/
                    NewsDetailBean.RowsDTO data = new Gson().fromJson(json,NewsDetailBean.class).getData();

                    /*将返回的数据依次填充页面*/
                    name.setText(data.getTitle());

                    /*新闻图片*/
                    Glide.with(NewsDetailActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getCover())
                            .error(R.mipmap.resource).transform(new CenterCrop()).into(img);

                    /*新闻主体 FROM_HTML_MODE_COMPACT：一个换行符就是一个换行符，FROM_HTML_MODE_LEGACY：一个换行符为两个换行符*/
                    content.setText(Html.fromHtml(data.getContent(),Html.FROM_HTML_MODE_COMPACT));

                    /*新闻评论数*/
                    comment.setText(data.getCommentNum() + "条评论");

                    /*新闻发布日期*/
                    date.setText(data.getPublishData() + "发布");
                }
            });
        }else {
            //TODO 新闻未完成
        }

    }

    public void back(View view) {
        finish();
    }
}






















