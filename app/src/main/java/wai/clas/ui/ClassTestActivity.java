package wai.clas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import net.tsz.afinal.view.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import wai.clas.base.BaseActivity;
import wai.clas.method.CommonAdapter;
import wai.clas.method.CommonViewHolder;
import wai.clas.method.Utils;
import wai.clas.model.ClassTest;

/**
 * 课堂测验
 */
public class ClassTestActivity extends BaseActivity {
    List<ClassTest> classTests;
    CommonAdapter<ClassTest> commonAdapter;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.main_lv)
    ListView mainLv;
    @Bind(R.id.upload_btn)
    Button uploadBtn;
    String user_type;
    @Bind(R.id.choice_tv)
    TextView choice_tv;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
        if (("0").equals(user_type)) {
            uploadBtn.setVisibility(View.GONE);
        }
        choice_tv.setOnClickListener(view -> Utils.IntentPost(XuanZeActivity.class));
        uploadBtn.setOnClickListener(view -> startActivityForResult(new Intent(ClassTestActivity.this, AddClassActivity.class), 0));
        toolbar.setLeftClick(() -> finish());
        classTests = new ArrayList<>();
        commonAdapter = new CommonAdapter<ClassTest>(this, classTests, R.layout.item_test) {
            @Override
            public void convert(CommonViewHolder holder, ClassTest classTest, int position) {
                holder.setText(R.id.title_tv, classTest.getTitle());
                String content = classTest.getContent();
                if (!TextUtils.isEmpty(content)) {
                    if (content.length() > 40) {
                        holder.setText(R.id.content_tv, content + "...");
                    } else {
                        holder.setText(R.id.content_tv, content);//15233710192
                    }
                }
                holder.setText(R.id.time_tv, classTest.getCreatedAt());
            }
        };
        mainLv.setAdapter(commonAdapter);
    }

    @Override
    public void initEvents() {
        load();
        mainLv.setOnItemClickListener((adapterView, view, i, l) ->
                Utils.IntentPost(TestDetailActivity.class, intent ->
                        intent.putExtra("model", classTests.get(i))));
    }

    void load() {
        BmobQuery<ClassTest> query = new BmobQuery<>();
        query.findObjects(new FindListener<ClassTest>() {
            @Override
            public void done(List<ClassTest> list, BmobException e) {
                if (e == null) {
                    classTests = list;
                    commonAdapter.refresh(classTests);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 99:
                load();
                break;
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_class_test;
    }
}
