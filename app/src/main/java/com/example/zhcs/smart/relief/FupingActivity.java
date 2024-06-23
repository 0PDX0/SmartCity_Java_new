package com.example.zhcs.smart.relief;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.BannerBean;
import com.example.zhcs.bean.FupingBean;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class FupingActivity extends AppCompatActivity {

    private Banner banner;
    private RecyclerView recyc_view;
    private RecyclerView news_recyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuping);

        /*初始化视图数据*/
        initView();

        /*初始化轮播图*/
        initBanner();

        /*初始化选项卡列表*/
        initTab();

        /*初始化新闻数据*/
        initNews();

    }

    /**
     * 初始化视图数据
     */
    private void initView() {
        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*选项卡列表*/
        recyc_view = findViewById(R.id.recyc_view);

        /*新闻列表*/
        news_recyc = findViewById(R.id.news_recyc);

    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        List<Integer> list = new ArrayList<>();

        list.add(R.mipmap.fuping_banner1);
        list.add(R.mipmap.fuping_banner2);
        list.add(R.mipmap.fuping_banner3);
        list.add(R.mipmap.fuping_banner4);
        list.add(R.mipmap.fuping_banner5);

        /*只有new BannerImageAdapter<泛型>放入泛型生成的回调才会有BannerImageHolder*/
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {

            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                /*使用Glide加载图片*/
                /* centerCrop:以填满整个ImageView为目的，将原图的中心对准ImageView的中心，等比例放大原图，直到填满ImageView为止（指的是ImageView的宽和高都要填满），原图超过ImageView的部分作裁剪处理。
                   fitXY:把原图按照指定的大小在View中显示，拉伸显示图片，不保持原比例，填满ImageView.
                   fitCenter:把图片按比例扩大/缩小到View的宽度，居中显示*/
                Glide.with(FupingActivity.this).load(list.get(position))
                        .transform(new CenterCrop()).into(holder.imageView);

            }
        }).setIndicator(new CircleIndicator(FupingActivity.this));

        banner.isAutoLoop(true);    //是否轮播
        banner.setLoopTime(2000);   //轮播间隔

        /*轮播图的点击事件*/
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Toast.makeText(FupingActivity.this,"您点击了第" + position + "张图片",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 初始化选项卡列表
     */
    private void initTab() {
        int[] imgs = {R.mipmap.fuping_icon1,
                    R.mipmap.fuping_icon2,
                    R.mipmap.fuping_icon3,
                    R.mipmap.fuping_icon4,
                    R.mipmap.fuping_icon5};

        String[] names = {"扶贫案例",
                        "村情村貌",
                        "收到求助",
                        "入户走访",
                        "案例发布"};

        /*设置线性布局渲染*/
        recyc_view.setLayoutManager(new LinearLayoutManager(FupingActivity.this));

        /*-----------------------------------------------------------------------------*/
        /*DividerItemDecoration：分隔器项目装饰*/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

//        ShapeDrawable shapeDrawable = new ShapeDrawable();    Drawable好多字类可以使用

        /*构造分隔线 分隔线的大小再drawable文件中设置*/
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.cs,null);
        drawable.setTint(Color.GRAY);   //设置色调
        /*将构造好的分隔线传入*/
        dividerItemDecoration.setDrawable(drawable);
        /*设置分隔线的方向*/
//        dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL);
        /*-----------------------------------------------------------------------------*/


        /*设置分隔线使用*/
//        recyc_view.addItemDecoration(dividerItemDecoration);
        /*默认系统的分隔线 ，其实一般这个也就够用了*/
        recyc_view.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));   //VERTICAL：横着的分隔线, HORIZONTAL：竖着的分隔线

        /*设置适配器*/
        recyc_view.setAdapter(new SmartAdapter(FupingActivity.this,imgs,names));

    }

    /**
     * 初始化新闻数据
     */
    private void initNews() {
        List<FupingBean> list = new ArrayList<>();

        list.add(new FupingBean("致力于农村基础设施建设，瑞因智控获百万级别种子轮投资", "2020-01-01",
                R.mipmap.fuping1,
                "农业装备研发新星黑龙江瑞因智控科技有限公司完成种子轮融资，投资额度数百万元投资方系杭州某电子商务有限公司。\n" +
                        "瑞因智控科技是一家致力于农村基础设施建设，提供科技创新型节能减排产品的创业公司。其产品研发与各地方政府政策相适应，以“精准扶贫”作为核心内容，基于电子信息智控技术，帮助农村中小型养殖中心达到低耗、低污染的生产效果。在科技创新方面，据查，公司拥有软件著作权一项，专利受理两项，其研发产品曾获节能减排大赛二等奖，具有长期研发价值。\n" +
                        "根据官方提供的数据，瑞因科技已在浙江省获得第一批机器订单，其产品本身直接响应当地“五水共治”号召，为养殖业污物处理提供了对应的解决方案。“农业的发展离不开政府的支持。”在农业发达的美国、日本，政府补贴达到40%以上。相比之下，我国的农业补贴虽只占20%左右，但仍不断上涨。1月29日，国务院办公厅印发《关于推进农业高新技术产业示范区建设发展的指导意见》，首次以农业高新技术产业为主题,明确到2025年,要布局建设一批国家农业高新技术产业示范区。\n"));
         list.add(new FupingBean("开阳信息锁进：饮水思源，精准扶贫", "2020-02-01",
                R.mipmap.fuping2,
                "近日，秦峰镇新塘村村民委员会为感谢锁进先生为秦峰镇新塘村的扶贫事业做出的杰出贡献，特聘锁进先生为新塘村的“名誉村主任”。" +
                        "锁进先生是江西渝网科技股份有限公司董事长，上饶开阳即渝网旗下全资子公司，此前一直专注于扶贫事业，经商的同时不忘回馈社会，" +
                        "积极履行应尽的社会责任，呼吁更多社会人士共同营造互相关心、互相帮助、互相支持的良好社会氛围。"));
        list.add(new FupingBean("常德联通：精准扶贫暖人心 感恩帮扶送锦旗", "2020-03-01",
                R.mipmap.fuping3,
                "“吃饭莫忘种田人，吃水不忘挖井人，非常感谢常德联通驻村工作队人员的辛勤付出”。" +
                        "近日，常德汉寿县洞庭村村委一行5人，将一面写有“联通扶贫用真情 洞庭人民永记心”的锦旗专程送到常德联通，" +
                        "党委委员周海华同志代表全体党员干部接收了锦旗，并与村支两委、扶贫工作队共同回顾了2020年帮扶工作，" +
                        "对2021年推进脱贫攻坚与乡村振兴有效衔接交流了意见和建议。\n" + " \n" + "汉寿县洞庭村村委一行送来锦旗。" +
                        "\n" + "常德联通一直以来都冲锋在脱贫攻坚的第一线，以“真扶贫、扶真贫”为原则，注重帮扶实效，成立以党委书记为第一责任人的帮扶领导小组，零距离开展帮扶工作。党员干部多次到村入户进行调研、走访、慰问，深入了解贫困户的家庭情况、致贫原因，多次召开党委会研究发展和资金投入，安排得力干部周训金同志加入驻村工作队，为帮扶责任村汉寿县周家村的脱贫攻坚做出应有的贡献。\n" +
                        "近年来，常德联通共向挂钩帮扶村和贫困户拨付款项近百万元，用于支持村委道路建设、文体广场修建、平安监控安装、环境卫生整治等，帮助全村主干道清除淤泥8KM, 让62户238人稳定脱贫，贫困人口实现全部脱贫，错评错退率实现0%，群众满意度达到了100%，脱贫攻坚工作取得决定性成效。\n" +
                        "一面锦旗凝聚着一片真情，是一份认可，一份信赖，更是一份责任。常德联通主要负责人表示：“锦旗承载着村民们的感激与信任，我们将以更高的标准、更实的行动、更大的热情推进为乡村振兴工作，为群众办好事、办实事。\n"));

        /*设置线性布局*/
        news_recyc.setLayoutManager(new LinearLayoutManager(FupingActivity.this));


        /*设置适配器*/
        news_recyc.setAdapter(new FupingAdapter(FupingActivity.this,list));

    }

    /**
     * 返回
     * @param view
     */
    public void back(View view) {
        finish();
    }
}

/*使用Glide加载图片*/
                /* centerCrop:以填满整个ImageView为目的，将原图的中心对准ImageView的中心，等比例放大原图，直到填满ImageView为止（指的是ImageView的宽和高都要填满），原图超过ImageView的部分作裁剪处理。
                   fitXY:把原图按照指定的大小在View中显示，拉伸显示图片，不保持原比例，填满ImageView.
                   fitCenter:把图片按比例扩大/缩小到View的宽度，居中显示*/
//                Glide.with(FupingActivity.this).load(list.get(position))
//                        .transform(new CenterCrop()).into()