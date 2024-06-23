package com.example.zhcs.person;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.UserBean;
import com.example.zhcs.guide.LoginActicity;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.example.zhcs.util.BaseFragment;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

public class PersonFragment extends BaseFragment {

    int imgs[] = {R.drawable.ic_baseline_person_24,
                  R.drawable.ic_baseline_lock_24,
                  R.drawable.ic_baseline_format_list_bulleted_24,
                  R.drawable.ic_baseline_feedback_24};

    String[] names = {"个人信息",
                    "修改密码",
                    "我的订单",
                    "意见反馈"};

    int[] colors = {Color.rgb(115,43,245),
                    Color.BLUE,
                    Color.RED,
                    Color.GREEN};

    Drawable[] drawables = new Drawable[5];

    private ImageView user_head;
    private TextView name;
    private RecyclerView recyc_person;
    private Button out_login;

    @Override
    protected View initView() {
        Log.i("碎片","个人中心的视图被实例化了");
        View view = View.inflate(getContext(), R.layout.fragment_person,null);

        findView(view);

        return view;
    }

    /**
     * 初始化视图数据
     * @param view
     */
    private void findView(View view) {
        /*用户头像*/
        user_head = view.findViewById(R.id.user_head);
        /*用户名*/
        name = view.findViewById(R.id.name);
        /*选项卡列表*/
        recyc_person = view.findViewById(R.id.recyc_person);
        /*退出登录*/
        out_login = view.findViewById(R.id.out_login);

    }

    @Override
    public void onStart() {
        super.onStart();

        initMsg();
    }

    /**
     * 用户信息回显
     */
    private void initMsg() {

        /*发送请求*/
        RetrofitUtil.get("/prod-api/api/common/user/getInfo", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                /*将返回回来的Json格式的字符串转换为对象使用*/
                UserBean.UserDTO user = new Gson().fromJson(json,UserBean.class).getUser();

                /*回显用户头像*/
                if (SPUtil.get(SPUtil.USER_HEAD) == null){
                    Glide.with(getContext()).load(SPUtil.get(SPUtil.HTTP) + user.getAvater())
                            .error(R.mipmap.user_head).transform(new CenterCrop()).into(user_head);
                }else {
                    Glide.with(getContext()).load(BitmapFactory.decodeFile(SPUtil.get(SPUtil.USER_HEAD)))
                            .error(R.mipmap.user_head).transform(new CenterCrop()).into(user_head);
                }

                /*回显用户名*/
                name.setText(user.getUserName());

            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        /*设置drawable*/
        for (int i = 0 ; i < imgs.length ; i++) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(),imgs[i], getResources().newTheme());

            /*TODO 这个地方很奇怪，设置R.color的颜色与实际不一致，但是Color类里的常量又可以*/
            drawable.setTint(colors[i]);
//            drawable.setColorFilter(colors[i], PorterDuff.Mode.ADD);    //PorterDuff.Mode.OVERLAY：覆盖

            drawables[i] = drawable;

        }


        /*设置线性布局*/
        recyc_person.setLayoutManager(new LinearLayoutManager(getContext()));
        /*设置适配器*/
        recyc_person.setAdapter(new SmartAdapter(getContext(),drawables,names));

        /*退出登录按钮设置点击事件监听*/
        out_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*将Token设置为null*/
                SPUtil.put(SPUtil.TOKEN,null);
                /*弹出提示框*/
                Toast.makeText(getContext(),"退出成功，请重新登录",Toast.LENGTH_SHORT).show();
                /*跳转登录页面*/
                startActivity(new Intent(getContext(), LoginActicity.class));
                /*结束页面*/
                getActivity().finish();
            }
        });
    }

}
