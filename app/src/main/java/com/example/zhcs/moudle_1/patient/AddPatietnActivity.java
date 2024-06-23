package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zhcs.R;
import com.example.zhcs.bean.PatientBean;
import com.example.zhcs.util.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class AddPatietnActivity extends AppCompatActivity {

    private EditText name;
    private Spinner sex;
    private EditText card;
    private EditText date;
    private EditText tell;
    private EditText address;
    private PatientBean.RowsDTO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patietn);

        initView();

        Serializable serializableExtra = getIntent().getSerializableExtra("data");
        if (serializableExtra != null){
            data = (PatientBean.RowsDTO) serializableExtra;

            name.setText(data.getName());
            sex.setSelection(Integer.parseInt(data.getSex()));
            card.setText(data.getCardId());
            date.setText(data.getBirthday());
            tell.setText(data.getTel());
            address.setText(data.getAddress());

        }

        /*给选择出生日期添加点击事件*/
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(AddPatietnActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//                    }
//                },2022,1,1).show();
//            }
//        });

        /*日期输入框获取焦点弹出日期选择器进行日期的选中填充*/
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    /*创建一个日期选择器对象*/
                    new DatePickerDialog(AddPatietnActivity.this, new DatePickerDialog.OnDateSetListener() {
                        /*将用户选中的日期填充进输入框*/
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    },2022,1,1).show();
                }
            }
        });

    }

    /**
     * 初始化视图变量
     */
    private void initView() {

        /*姓名*/
        name = findViewById(R.id.name);

        /*性别*/
        sex = findViewById(R.id.sex);

        /*身份证号*/
        card = findViewById(R.id.card);

        /*出生日期*/
        date = findViewById(R.id.date);

        /*手机号*/
        tell = findViewById(R.id.tell);

        /*地址*/
        address = findViewById(R.id.address);

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {



    }

    /**
     * 关闭当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 确认按钮
     * @param view
     */
    public void save(View view) {

        if (name.getText().toString().isEmpty() |
            card.getText().toString().isEmpty() |
            date.getText().toString().isEmpty() |
            tell.getText().toString().isEmpty() |
            address.getText().toString().isEmpty()){

            Toast.makeText(this, "您输入的信息有误", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<Object, Object> map = new HashMap<>();

        map.put("address",address.getText().toString());
        map.put("birthday",date.getText().toString());
        map.put("cardId",card.getText().toString());
        map.put("name",name.getText().toString());
        map.put("sex",String.valueOf(sex.getSelectedItemPosition()));
        map.put("tel",tell.getText().toString());

        RetrofitUtil.OnRequest onRequest = new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                try {

                    JSONObject obj = new JSONObject(json);
                    if (obj.getInt("code") == 200){
                        /*添加成功弹出成功提示*/
                        Toast.makeText(AddPatietnActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        /*添加失败弹出失败原因*/
                        Toast.makeText(AddPatietnActivity.this,"添加失败," + obj.getString("msg") ,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /* 判断上个页面是否传递了就诊人的数据进来，没有传递就是新增操作，如果传递了就代表是修改操作 */
        if (data == null){
            RetrofitUtil.post("/prod-api/api/hospital/patient",map,onRequest);
        } else{
            map.put("id",data.getId());
            RetrofitUtil.put("/prod-api/api/hospital/patient",map,onRequest);
        }


    }
}



































