package com.example.zhcs.data;

import android.util.Log;
import android.view.View;

import com.example.zhcs.R;
import com.example.zhcs.util.BaseFragment;

public class DataFragment extends BaseFragment {

    @Override
    protected View initView() {
        Log.i("碎片","数据分析的视图被实例化了");
        View view = View.inflate(getContext(), R.layout.fragment_data,null);

        return view;
    }


    @Override
    protected void initData() {
        super.initData();
    }

}
