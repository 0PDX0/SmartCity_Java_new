package com.example.zhcs.person;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.UserBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifyPwdActivity extends AppCompatActivity {

    private EditText old_pwd;
    private EditText new_pwd;
    private EditText confirm_pwd;
    private ImageView user_head;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);

        /*初始化视图*/
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        /*用户旧密码*/
        old_pwd = findViewById(R.id.old_pwd);
        /*用户新密码*/
        new_pwd = findViewById(R.id.new_pwd);
        /*用户确认新密码*/
        confirm_pwd = findViewById(R.id.confirm_pwd);

        /*初始化数据*/
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {


    }

    /**
     * 返回上个页面
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 保存按钮
     * @param view
     */
    public void save(View view) {

        /*用户输入的旧密码*/
        String oldPwd = old_pwd.getText().toString().trim();
        /*用户输入的新密码*/
        String newPwd = new_pwd.getText().toString().trim();
        /*用户确认的新密码*/
        String confirmPwd = confirm_pwd.getText().toString().trim();

        /*判断用户输入是否为空*/
        if (oldPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()){
            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        /*判断用户两次密码输入是否相同*/
        if (!newPwd.equals(confirmPwd)){
            Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        /*将用户输入的旧密码和新密码封装进Map集合中*/
        Map map = new HashMap();
        map.put("oldPassword",oldPwd);
        map.put("newPassword",newPwd);


        /*发送请求*/
        RetrofitUtil.put("/prod-api/api/common/user/resetPwd", map, new RetrofitUtil.OnRequest() {

            /*回调判断用户是否修改成功*/
            @Override
            public void onRequest(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);

                    if (jsonObject.getInt("code") == 200){
                        Toast.makeText(ModifyPwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ModifyPwdActivity.this, jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}











