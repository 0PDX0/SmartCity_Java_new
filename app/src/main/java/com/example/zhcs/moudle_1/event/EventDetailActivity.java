package com.example.zhcs.moudle_1.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.CommentBean;
import com.example.zhcs.bean.EventBean;
import com.example.zhcs.bean.EventDetailBean;
import com.example.zhcs.bean.NewsTypeBean;
import com.example.zhcs.bean.SignupBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDetailActivity extends AppCompatActivity {

    private int id;
    private ImageView img;
    private TextView name;
    private Button sign_btn;
    private TextView content;
    private RecyclerView event_recyc;
    private TextView comment_cut;
    private RecyclerView comment_recyc;
    private EditText comment_et;
    private Button comment_btn;
    private CommentBean Commentdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        /*获取传递过来的活动id*/
        id = getIntent().getIntExtra("id",0);
        /*获取指定id的新闻详细信息*/
        initById(id);

        /*初始化视图信息*/
        initView();

        /*初始化用户报名信息*/
        initSignup();

        /*初始化评论列表信息*/
        initComment();

        /*记载推荐活动列表*/
        initRecommend();
    }

    /**
     * 初始化用户报名信息
     */
    private void initSignup() {

        RetrofitUtil.get("/prod-api/api/activity/signup/check?activityId=" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                SignupBean signup = new Gson().fromJson(json,SignupBean.class);

                if (signup.getIsSignup().equals("true")){

                    /*TODO 按钮变色不可点击*/
                    sign_btn.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC);

                    sign_btn.setText("已报名");

                    /*设置按钮不可点击*/
                    sign_btn.setEnabled(false);
                }

            }
        });

    }

    /**
     * 初始化视图信息
     */
    private void initView() {

        /*活动图片信息*/
        img = findViewById(R.id.img);

        /*活动名称*/
        name = findViewById(R.id.name);

        /*报名按钮*/
        sign_btn = findViewById(R.id.sign_btn);

        /*活动详细*/
        content = findViewById(R.id.content);

        /*推荐活动列表信息*/
        event_recyc = findViewById(R.id.event_recyc);

        /*评论列表格个数*/
        comment_cut = findViewById(R.id.comment_cnt);

        /*评论列表信息*/
        comment_recyc = findViewById(R.id.comment_recyc);

        /*评论输入框*/
        comment_et = findViewById(R.id.comment_et);

        /*评论按钮*/
        comment_btn = findViewById(R.id.comment_btn);

        /*监听报名按钮*/
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map map = new HashMap();
                map.put("activityId",id);

                RetrofitUtil.post("/prod-api/api/activity/signup", map, new RetrofitUtil.OnRequest() {
                    @Override
                    public void onRequest(String json) {
                        try {

                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.getInt("code") == 200){

//                                sign_btn.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC);
//
//                                sign_btn.setText("已报名");
//
//                                /*设置按钮不可点击*/
//                                sign_btn.setEnabled(false);

                                /*报名成功后重新初始化检查报名信息*/
                                initSignup();

                                Toast.makeText(EventDetailActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        /*监听评论按钮*/
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map map = new HashMap();
                map.put("activityId",id);
                map.put("content",comment_et.getText().toString());

                RetrofitUtil.post("/prod-api/api/activity/comment", map, new RetrofitUtil.OnRequest() {
                    @Override
                    public void onRequest(String json) {
                        try {
                            
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.getInt("code") == 200){
                                Toast.makeText(EventDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                /*评论成功后重新初始化评论列表信息*/
                                initComment();
                            }
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 获取指定id的活动详细信息
     * @param id
     */
    public void initById(int id){
        RetrofitUtil.get("/prod-api/api/activity/activity/" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                EventDetailBean.RowsDTO data = new Gson().fromJson(json,EventDetailBean.class).getData();

                /*加载活动图片*/
                Glide.with(EventDetailActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                        .error(R.mipmap.resource).transform(new CenterCrop()).into(img);
                /*加载活动名称*/
                name.setText(data.getName());
                /*加载活动详细*/
                if (data.getContent().contains("<p>")){
                    content.setText(Html.fromHtml(data.getContent(),Html.FROM_HTML_MODE_LEGACY));
                }


            }
        });
    }

    /**
      初始化推荐活动列表信息
    */
    public void initRecommend(){
        RetrofitUtil.get("/prod-api/api/activity/activity/list?recommend=Y", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<EventBean.RowsDTO> data = new Gson().fromJson(json,EventBean.class).getRows();

                event_recyc.setLayoutManager(new LinearLayoutManager(EventDetailActivity.this));

                event_recyc.setAdapter(new EventAdapter(EventDetailActivity.this,data));

            }
        });
    }

    /**
     * 初始化评论列表信息
     */
    public void initComment(){
        RetrofitUtil.get("/prod-api/api/activity/comment/list?activityId=" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                Commentdata = new Gson().fromJson(json,CommentBean.class);

                /*评论个数*/
                comment_cut.setText("评论列表（共" + Commentdata.getTotal() + "条评论)");

                List<CommentBean.RowsDTO> data1 = Commentdata.getRows();

                comment_recyc.setLayoutManager(new LinearLayoutManager(EventDetailActivity.this));

                comment_recyc.setAdapter(new CommentAdapter(EventDetailActivity.this,data1));

//                comment_recyc.addItemDecoration(new DividerItemDecoration(EventDetailActivity.this,DividerItemDecoration.VERTICAL));


            }
        });
    }
}