package com.example.zhcs.smart;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.example.zhcs.util.BaseFragment;

public class SmartFragment extends BaseFragment {

    private RecyclerView recyc_view;

    private String[] imgss = {"https://tse3-mm.cn.bing.net/th/id/OIP-C.1MoJUFFgBjQ5cZfF68O7-QHaHa?w=207&h=207&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse2-mm.cn.bing.net/th/id/OIP-C.As86UzI-SsGgIMPi0dh6DAHaHa?w=187&h=187&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse3-mm.cn.bing.net/th/id/OIP-C.BOFw2FzpbDVXHrFvXlBVmAHaHa?w=208&h=208&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse4-mm.cn.bing.net/th/id/OIP-C.MmRY6CEQP4uq8-31VlhokQHaHT?w=187&h=187&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse4-mm.cn.bing.net/th/id/OIP-C.IxPgrgrpl7pCC1KsNLDxEAHaHa?w=217&h=217&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse2-mm.cn.bing.net/th/id/OIP-C.nGkt1Nr_w2pbcJ-0NCndfwAAAA?w=183&h=183&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            "https://tse2-mm.cn.bing.net/th/id/OIP-C.cdypRZ9fa5nj_XphvNTc7wHaHa?w=199&h=199&c=7&r=0&o=5&dpr=1.5&pid=1.7"};

    private int[] imgs = {R.mipmap.build,R.mipmap.build,R.mipmap.build,R.mipmap.build,R.mipmap.build,R.mipmap.build,R.mipmap.build,};

    private Drawable[] drawables = new Drawable[7];

    private int[] colors = {Color.rgb(210,83,255),
                            Color.BLUE,
                            Color.GREEN,
                            Color.YELLOW,
                            Color.rgb(38,135,38),
                            Color.rgb(225,186,66),
                            Color.rgb(38,135,38)};

    private String[] names = {"精准扶贫","时代楷模","智慧党建","智慧环保","智慧社区","智慧养老","中国制造"};

    @Override
    protected View initView() {
        Log.i("碎片","智慧城市的视图被实例化了");
        View view = View.inflate(getContext(), R.layout.fragment_smart,null);
        findView(view);

        return view;
    }

    private void findView(View view) {
        recyc_view = view.findViewById(R.id.recy_view);
    }

    @Override
    protected void initData() {
        super.initData();

        /*---------------------------------------------------------------*/
//        for (int i = 0 ; i < names.length ; i++){
//            Drawable drawable = ResourcesCompat.getDrawable(getResources(), imgs[i], getResources().newTheme());
//
//
//            drawables[i] = drawable;
//        }
        /*---------------------------------------------------------------*/

        /*设置布局方式*/
        recyc_view.setLayoutManager(new LinearLayoutManager(getContext()));

        /*设置分隔线*/
        recyc_view.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        /*设置适配器*/
        recyc_view.setAdapter(new SmartAdapter(getContext(),imgs,names,colors));
    }

}
