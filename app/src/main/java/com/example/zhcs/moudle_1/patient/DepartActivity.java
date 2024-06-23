package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.zhcs.R;
import com.example.zhcs.bean.DepartBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DepartActivity extends AppCompatActivity {

    private ListView recyc_depart;
    private String[] names;
    private List<String> namelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        initView();
    }

    private void initView() {
        /*科室列表*/
        recyc_depart = findViewById(R.id.recyc_depart);
        recyc_depart.setDivider(new ColorDrawable(Color.LTGRAY));
        recyc_depart.setDividerHeight(1);

        initDepart();

    }

    /**
     * 初始化所有科室信息
     */
    private void initDepart() {

        RetrofitUtil.get("/prod-api/api/hospital/category/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<DepartBean.RowsDTO> mdata = new Gson().fromJson(json,DepartBean.class).getRows();

                names = new String[mdata.size()];
                for (int i = 0 ; i < mdata.size() ; i++){
                    names[i] = mdata.get(i).getCategoryName();
                    namelist.add(mdata.get(i).getCategoryName());
                }

                /*设置集合适配器*/
//                ArrayAdapter<DepartBean.RowsDTO> arrayAdapter = new ArrayAdapter<DepartBean.RowsDTO>(DepartActivity.this, android.R.layout.simple_list_item_1,mdata);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepartActivity.this, android.R.layout.simple_list_item_1,names);
                /*添加适配器*/
                recyc_depart.setAdapter(adapter);

                /*解决ListView只显示一项问题，【这里我其实没必要搞这么复杂，我只需要用一个相对布局包裹这个ListView就好了，也是有滚动效果的，但是这个是为了示范学习】*/
                setListViewHeight(recyc_depart);

                /*给ListView的每个选项添加点击事件*/
                recyc_depart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DepartBean.RowsDTO row = mdata.get(position);

                        Intent intent = new Intent(DepartActivity.this, SubPatienActivity.class);
                        intent.putExtra("name",row.getCategoryName());
                        intent.putExtra("type",row.getType());

                        startActivity(intent);

                    }
                });

            }
        });

    }

    /**
     * 解决ListView只显示一项问题
     * @param listView
     */
    public static void setListViewHeight(ListView listView){
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for (int i = 0 ; listAdapter.getCount() > i ; i++){   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i , null , listView);

            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();    //统计所有子项的高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }


    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }



}







/*public class NoScrollListView extends ListView{

        public NoScrollListView(Context context) {
            super(context);
        }

        public NoScrollListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

            super.onMeasure(widthMeasureSpec,expandSpec);
        }
    }*/







