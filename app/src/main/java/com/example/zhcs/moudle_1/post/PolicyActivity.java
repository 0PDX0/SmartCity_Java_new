package com.example.zhcs.moudle_1.post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.zhcs.R;
import com.example.zhcs.bean.PolicyBean;
import com.example.zhcs.bean.PolicyDetailBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.List;

public class PolicyActivity extends AppCompatActivity {

    private int id;
    private TextView title;
    private TextView author;
    private TextView tv_date;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        id = getIntent().getIntExtra("id",0);

        initView();

        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {

        RetrofitUtil.get("/prod-api/api/youth-inn/talent-policy/" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                PolicyDetailBean.DataDTO data = new Gson().fromJson(json, PolicyDetailBean.class).getData();

                title.setText(data.getTitle());

                author.setText(data.getAuthor());

                tv_date.setText(data.getCreateTime() + "发布");

                if (data.getContent().contains("<p>")){
                    content.setText(Html.fromHtml(data.getContent(),Html.FROM_HTML_MODE_LEGACY));
                }else {
                    content.setText(data.getContent());
                }

            }
        });

    }

    /**
     * 初始化视图
     */
    private void initView() {

        /*标题信息*/
        title = findViewById(R.id.title);

        /*发布人*/
        author = findViewById(R.id.tv_author);

        /*发布时间*/
        tv_date = findViewById(R.id.tv_date);

        /*人才政策详细信息*/
        content = findViewById(R.id.content);


    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}