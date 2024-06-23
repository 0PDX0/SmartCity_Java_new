package com.example.zhcs.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private Context context;

    /**
     * onCreate是指创建该fragment,类似于Activity.onCreate,你可以在其中初始化除了View之外的一些东西
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getContext();
    }


    /**
     * onCreateView是创建该fragment对应的视图，你必须在这里创建自己的视图并返回给调用者
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }


    /*抽象方法，用来给字类使用返回它们分别的View*/
    protected abstract View initView();



    /**
     * 提示：一般onCreateView()用于初始化Fragment的视图，
     * onViewCreated()一般用于初始化视图内各个控件，
     * 而onCreate()用于初始化与Fragment视图无关的变量。
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    protected void initData(){

    };


}




























































