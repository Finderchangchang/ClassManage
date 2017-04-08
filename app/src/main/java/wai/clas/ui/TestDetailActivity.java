package wai.clas.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wai.clas.base.BaseActivity;
import wai.clas.model.ClassTest;

/**
 * 课堂测验详情页面
 */
public class TestDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.test1_iv)
    ImageView test1Iv;
    @Bind(R.id.test2_iv)
    ImageView test2Iv;
    @Bind(R.id.test3_iv)
    ImageView test3Iv;
    ClassTest model;

    @Override
    public void initViews() {
        toolbar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        model = (ClassTest) getIntent().getSerializableExtra("model");
        titleTv.setText(model.getTitle());
        timeTv.setText(model.getCreatedAt());
        contentTv.setText(model.getContent());
        Glide.with(this)
                .load(model.getImg1())
                .into(test1Iv);
        Glide.with(this)
                .load(model.getImg2())
                .into(test2Iv);
        Glide.with(this)
                .load(model.getImg3())
                .into(test3Iv);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_test_detail;
    }
}
