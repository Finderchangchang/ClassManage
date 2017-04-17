package wai.clas.ui;

import android.os.Bundle;
import android.widget.ListView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wai.clas.base.BaseActivity;
import wai.clas.method.CommonAdapter;
import wai.clas.method.CommonViewHolder;
import wai.clas.model.Xuanze;

public class XuanZeActivity extends BaseActivity {
    List<Xuanze> list = new ArrayList<>();
    CommonAdapter<Xuanze> adapter;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.main_lv)
    ListView mainLv;

    @Override
    public void initViews() {
        toolbar.setLeftClick(() -> finish());
        list = new ArrayList<>();
        adapter = new CommonAdapter<Xuanze>(this, list, R.layout.item_question) {
            @Override
            public void convert(CommonViewHolder holder, Xuanze xuanze, int position) {
                holder.setText(R.id.title_tv, xuanze.getDrivingProblem1());
                holder.setGBText(R.id.a_rb, xuanze.getaChoice());
                holder.setGBText(R.id.b_rb, xuanze.getbChoice());
                holder.setGBText(R.id.c_rb, xuanze.getcChoice());
                holder.setGBText(R.id.d_rb, xuanze.getdChoice());
            }
        };
        toolbar.setLeftClick(() -> finish());
        mainLv.setAdapter(adapter);
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
        list.add(new Xuanze("我是问题", "我是A", "我是B", "我是C", "我是D", "A"));
    }

    @Override
    public void initEvents() {
        adapter.refresh(list);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_xuan_ze;
    }
}
