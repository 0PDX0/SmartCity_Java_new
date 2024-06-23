package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhcs.R;

public class SubResultActivity extends AppCompatActivity {

    private String title;
    private String outPatient;
    private String arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_result);

        /*预约科室*/
        title = getIntent().getStringExtra("title");

        /*门诊类型*/
        outPatient = getIntent().getStringExtra("outPatient");

        /*预约时间*/
        arr = getIntent().getStringExtra("arr");

        initView();

    }

    private void initView() {

        /*标题视图*/
        TextView title1 = findViewById(R.id.title);
        /*门诊类型视图*/
        TextView outPatient1 = findViewById(R.id.outPatient);
        /*预约时间视图*/
        TextView arr1 = findViewById(R.id.arr);

        title1.setText(title);

        outPatient1.setText("门诊类型: " + outPatient);

        arr1.setText(arr);

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}











