package com.example.zhcs.person;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.UserBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PersonMsgActivity extends AppCompatActivity {

    private ImageView user_head;
    private EditText name;
    private Spinner sex;
    private EditText phone;
    private String phonenumber;
    private boolean flag;
    private String editPhoneNumber;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_msg);

        /*初始化视图数据*/
        initView();
    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*用户头像框*/
        user_head = findViewById(R.id.user_head);

        /*用户昵称数据框*/
        name = findViewById(R.id.name);

        /*用户性别下拉框*/
        sex = findViewById(R.id.sex);

        /*用户联系电话*/
        phone = findViewById(R.id.phone);

        /*用户信息回显*/
        initMsg();

        /*用户修改头像功能模块*/
        initHead();

    }


    /**
     * 用户信息回显
     */
    private void initMsg() {
        /*/prod-api/api/common/user/getInfo：查询个人基本信息*/
        RetrofitUtil.get("prod-api/api/common/user/getInfo", new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {
                /*将查询出来的用户信息封装进实体类*/
                UserBean.UserDTO user = new Gson().fromJson(json,UserBean.class).getUser();

                /*判断用户头像的地址类型，是否将路径存入过本地共享参数中*/
                if (SPUtil.get(SPUtil.USER_HEAD) == null){
                    /*拼接网络地址加载头像*/
                    Glide.with(PersonMsgActivity.this).load(SPUtil.get(SPUtil.HTTP) + user.getAvater())
                            .error(R.mipmap.user_head).transform(new CircleCrop()).into(user_head);

                }else {
                    /*BitmapFactory.decodeFile记载存在本目录的路径文件，返回一个Bitmap对象*/
                    Glide.with(PersonMsgActivity.this).load(BitmapFactory.decodeFile(SPUtil.get(SPUtil.USER_HEAD)))
                            .error(R.mipmap.user_head).transform(new CircleCrop()).into(user_head);

                }

                /*用户昵称回显渲染*/
                name.setText(user.getNickName());

                /*用户性别下拉框回显*/
                sex.setSelection(Integer.parseInt(user.getSex()));

                /*用户电话号码回显处理渲染，截取用户号码前七位，再用*号补全*/
                phone.setText(user.getPhonenumber().substring(0, 7) + "****");
                phone.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

                /*取出用户完整的电话号码*/
                phonenumber = user.getPhonenumber();

                /*时刻记录用户的手机号输入框变化*/
                editPhoneNumber = user.getPhonenumber();

                /*记录用户焦点变更*/
                phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            /*当用户获取焦点，将未加密的手机号展出出来*/
                            phone.setText(editPhoneNumber);
                        }else {
                            /*当失去焦点，先判断用户输入是否为11位，再进行加密的拼接*/
                            if (editPhoneNumber.length() == 11){
                                phone.setText(editPhoneNumber.substring(0,7) + "****");
                            }
                        }
                    }
                });

                /*电话号码输入框的文本监听器*/
                phone.addTextChangedListener(new TextWatcher() {

                    // charSequence为在你按键之前显示的字符串  start为新字符串与charSequence开始出现差异的下标  count表示原字符串的count个字符  after表示将会被after个字符替换
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        Toast.makeText(PersonMsgActivity.this,s,Toast.LENGTH_SHORT).show();
                    }

                    // 按键之前字符串的start位置的before个字符已经被count个字符替换形成新字符串charSequence
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        flag = true;
//                        Toast.makeText(PersonMsgActivity.this,s,Toast.LENGTH_SHORT).show();

                        /*当字符串包含*就代表加密状态，不进行字符串的更新*/
                        if (!s.toString().contains("*")){
                            editPhoneNumber = s.toString();
                        }
                    }

                    // afterTextChanged中 editable为EditText显示的内容
                    @Override
                    public void afterTextChanged(Editable s) {
//                        Toast.makeText(PersonMsgActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    /**
     * 修改头像功能模块
     */
    private void initHead() {
        /*用户头像框点击监听*/
        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*这里判断我们当前手机系统的SDK版本是否是23以上，23以下的版本不用申请，它在应用安装的时候就授权好了*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    /*这个if判断用户是否已经开启了外部存储写入的权限(WRITE_EXTERNAL_STORAGE：外部存储写入，返回值PackageManager.PERMISSION_GRANTED：就代表已被授予该权限)*/
                    /*如需检查用户是否已向您的应用授予特定权限，请将该权限传入 ContextCompat.checkSelPermission()方法。根据您的应用是否具有相应权限，此方法会返回 PERMISSION_GRANTED(授予) 或 PERMISSION_DENIED(否认)*/
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        //TODO 申请外部存储写入的权限  write_external_storage
                        /*如需请求一项权限，请使用 RequestPermission。如需同时请求多项权限，请使用 RequestMultiplePermissions*/
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                    }else {

                        /*进入这里代表用户授予了外部存储写入的权限，直接创建意图打开系统相册*/
                        /*打开系统 APP 的资源选择器选取资源（图片/文件），通常可以使用以下 3 个 Action:
                        Intent.ACTION_PICK
                        Intent.ACTION_GET_CONTENT
                        Intent.ACTION_OPEN_DOCUMENT
                        一般 Android 系统内置的相关 APP 中均有实现了这 3 个 Action（如: 相册、文件管理），三的均能打开系统 APP 的资源选择器选择资源（图片、视频、文件、通讯录等）并返回，但三者的使用并不完全相同。有些第三方 APP 实现了这 3 个 Action 的，也可以用于选取相应的资源。
                        一般使用 ACTION_PICK 选择图片，使用 ACTION_GET_CONTENT 或 ACTION_OPEN_DOCUMENT 选择文件。*/
                        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
                    }
                }
            }
        });
    }




    /*onRequestPermissionsResult是一个回调方法，用于处理应用程序请求权限的结果。
    当应用程序请求权限时，系统会弹出一个对话框，询问用户是否授予该权限。
    当用户做出选择后，系统会调用onRequestPermissionsResult方法，
    将结果传递给应用程序。在该方法中，应用程序可以根据结果采取相应的措施，
    例如显示一个对话框或执行其他操作。在上述引用中，onRequestPermissionsResult方法被用于处理相机权限请求的结果。*/
    /**
     * 处理权限请求结果
     * 检查权限时，由于会在机器上弹出选择框，当你选择完是否获取权限后，执行onRequestPermissionResult回调函数，获取选择的结果
     * @param requestCode
     *              请求权限时传入的请求码，用于区别是哪一次请求的
     * @param permissions
     *              所请求的所有权限的数组
     * @param grantResults
     *              权限授予结果，和 permissions 数组参数中的权限一一对应，元素值为两种情况，如下:
     *              授予: PackageManager.PERMISSION_GRANTED
     *              拒绝: PackageManager.PERMISSION_DENIED
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        Toast.makeText(this,"权限请求调用了",Toast.LENGTH_SHORT).show();

        /*因为我们也只请求了一个权限，所以直接判断grantResults[0]是否授权成功*/
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            /*授权成功直接跳入系统相册中*/
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
        }else {
            /*授权失败提示用户暂无权限*/
            Toast.makeText(this,"暂无权限",Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 从相册选择图片返回后的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*判断是否成功正常返回，没问题再往下执行*/
        if (resultCode == RESULT_OK){

            /*获取用户选择图片的Uri数据*/
            Uri uri = data.getData();

            Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);

            /*Cursor游标的指针指向查询的第一个结果*/
            cursor.moveToFirst();

            /*寻找索引为(获取列名为_data的索引，索引从0开始，没找到返回-1)的列的值，getColumnIndexOrThrow(String columnName)——从零开始返回指定列名称，如果不存在将抛出IllegalArgumentException 异常。*/
            /*这个path返回的是一个路径文件地址,storage/emulated/0”对应的就是你的机内文件的文件管理器的根目录。*/
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            /*将文件路径解码为位图后再使用图片加载器加载，BitmapFactory.decodeFile：将文件路径解码为位图*/
            Glide.with(PersonMsgActivity.this).load(BitmapFactory.decodeFile(path))
                    .error(R.mipmap.user_head).transform(new CircleCrop()).into(user_head);

        }
    }

    /**
     * 结束当前页面
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

        /*判断用户是否修改号码，号码中还是否存在*号，任意一项不满足都不能让他修改*/
        if (editPhoneNumber.length() != 11){
            Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        /*创建一个Map集合，将用户需要保存的信息存入集合中*/
        HashMap<Object,Object> map = new HashMap<>();
        /*用户昵称*/
        map.put("nickName",name.getText().toString());

        /*用户性别 .getSelectedItemPosition()：获取用户性别单选框选择的索引值*/
        map.put("sex",sex.getSelectedItemPosition());

        /*用户电话号码*/
        map.put("phonenumber",editPhoneNumber);

        /*发送修改用户信息的请求*/
        RetrofitUtil.put("/prod-api/api/common/user", map, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                try {
                    /*将返回回来的json字符串转换为JSONObject对象*/
                    JSONObject obj = new JSONObject(json);

                    /*判断请求修改是否成功*/
                    if (obj.getInt("code") == 200){
                        Toast.makeText(PersonMsgActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

                        /*修改成功后将共享参数的数据也更新,path为本机文件的路径(storage/emulated/0)对应的就是你的机内文件的文件管理器的根目录*/
                        if (path != null){
                            SPUtil.put(SPUtil.USER_HEAD,path);
                        }

                        /*结束当前页面*/
                        finish();
                    }else {
                        Toast.makeText(PersonMsgActivity.this, "保存失败" + obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
















