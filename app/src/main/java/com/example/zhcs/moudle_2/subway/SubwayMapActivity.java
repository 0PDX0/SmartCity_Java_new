package com.example.zhcs.moudle_2.subway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SubwayMapActivity extends AppCompatActivity {

    private RecyclerView recyc_subway_map;
    private ImageView map_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_map);

        initView();

        /*初始化地图线路数据*/
        initSubwayMap();

        /*初始化地铁总览图*/
        initMapImage();
    }

    /**
     * 初始化地铁总览图
     */
    private void initMapImage() {
        /*/prod-api/api/metro/city*/

        RetrofitUtil.get("/prod-api/api/metro/city", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                try {

                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject object = (JSONObject) jsonObject.get("data");

                    Glide.with(SubwayMapActivity.this).load(SPUtil.get(SPUtil.HTTP) + object.getString("imgUrl"))
                            .transform(new CenterCrop()).into(map_img);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /**
     * 初始化地铁所有线路
     */
    private void initSubwayMap() {
        /*/prod-api/api/metro/line/list*/
        RetrofitUtil.get("/prod-api/api/metro/line/list", new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {
                try {

                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<String> namelist = new ArrayList<>();  //地铁线路名
                    int[] colors = new int[jsonArray.length()]; //地铁线路颜色

                    for (int i = 0 ; i < jsonArray.length() ; i++){
                        namelist.add(jsonArray.getJSONObject(i).getString("lineName"));

                        colors[i] = Color.parseColor("#" + getColor());
                    }

                    recyc_subway_map.setLayoutManager(new GridLayoutManager(SubwayMapActivity.this,2,LinearLayoutManager.VERTICAL,false));

                    recyc_subway_map.setAdapter(new SubwayMapAdapter(SubwayMapActivity.this,namelist,colors));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    /**
     * 初始化视图
     */
    private void initView() {

        /*地铁线路*/
        recyc_subway_map = findViewById(R.id.recyc_subway_map);

        /*地图线路图*/
        map_img = findViewById(R.id.map_img);

    }

    Set<String> set = new HashSet<>();
    public String getColor(){
        String res = "";

        Random random = new Random();
        for (int i = 0 ; i < 6 ; i++){
            int next = random.nextInt(16);

            if (next < 10){
                res += next;
            }else {
                res += (char)('A' + next - 10);
            }
        }

        while (set.contains(res)){
            res = getColor();
        }

        set.add(res);
        return res;
    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}