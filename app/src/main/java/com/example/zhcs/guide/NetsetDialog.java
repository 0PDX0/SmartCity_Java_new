package com.example.zhcs.guide;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zhcs.R;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;

public class NetsetDialog extends Dialog {

    private EditText ipEt;
    private Button btn;

    public NetsetDialog(@NonNull Context context) {
        super(context);
    }

    /*进行初始化操作*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netset);

        //ip地址输入框
        ipEt = findViewById(R.id.ip_et);
        //确认按钮
        btn = findViewById(R.id.btn);

        //设置输入框类型只能为数字型
        ipEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        //设置输入框的内容只能为0123456789:.
        ipEt.setKeyListener(DigitsKeyListener.getInstance("0123456789:."));

        //确认按钮监听
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ipEt.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"请输入正确的地址",Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(),"IP端口设置成功",Toast.LENGTH_SHORT).show();
                //将用户输入的地址放入共享参数(user)中保存
                SPUtil.put(SPUtil.HTTP,"http://" + ipEt.getText().toString());
                //TODO 初始化网络请求封装类
                RetrofitUtil.init(getContext());

                /**
                 * 在 Android 中，Dialog（对话框）类有两个取消方法：cancel() 和 dismiss()。
                 *
                 * cancel() 方法会触发 Dialog.OnCancelListener 接口中的 onCancle() 回调方法。
                 * 该方法会在用户点击对话框以外的区域或者按下返回键时调用。我们可以在这个回调方法中指定一些逻辑操作，
                 * 如确认取消对话框后的后续操作等。此外，如果使用 Dialog.Builder 创建对话框，
                 * 我们需要在此方法中手动调用对话框的 dismiss() 方法，以确保对话框被正确地关闭。
                 *
                 * dismiss() 方法只是简单地将对话框从屏幕上移除，我们可以用它来处理用户手动关闭对话框的操作，比如点击某个按钮或者手动调用该方法。
                 * 需要注意的是，Dialog必须在所在Activity销毁之前销毁,否则会报：android.view.WindowLeaked异常。
                 */
                cancel();

            }
        });
    }


    /**
     * 对话框show,显示时调用此方法
     */
    @Override
    public void show() {
        super.show();

        /*获取window对象*/
        Window window = getWindow();

        /*设置背景，ColorDrawable这个就是绘制一个纯色背景，用 XML 太浪费，直接再代码中创建ColorDrawable更简单，(这个背景再弹出框的附近，并没有覆盖整个背景)*/
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*这里获取的是它自己的布局控件，不是Activity整个窗口的*/
        /*WindowManager.LayoutParams 是 WindowManager 接口的嵌套类：它继承于 ViewGroup.LayoutParams：它用于向WindowManager描述Window的管理策略*/
        WindowManager.LayoutParams attr = window.getAttributes();   //.getAttributes()：检索与此面板关联的当前窗口属性

        /*设置它的宽高*/
        attr.width = (int) (attr.width * 0.7);

        /*设置它的对齐方式*/
        attr.gravity = Gravity.CENTER;

        /*将设置好的布局对象添加进去*/
        window.setAttributes(attr);

    }
}





//
//    NetsetDialog x = new NetsetDialog(GuideActivity.this);
//            x.setOnCancelListener(new DialogInterface.OnCancelListener() {
//@Override
//public void onCancel(DialogInterface dialog) {
//
//        }
//        });









