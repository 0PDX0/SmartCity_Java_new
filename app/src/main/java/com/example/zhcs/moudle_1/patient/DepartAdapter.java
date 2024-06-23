package com.example.zhcs.moudle_1.patient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zhcs.bean.DepartBean;

import java.util.List;

public class DepartAdapter extends BaseAdapter {

    private Context context;
    private List<DepartBean.RowsDTO> mdata;

    public DepartAdapter(Context context, List<DepartBean.RowsDTO> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
