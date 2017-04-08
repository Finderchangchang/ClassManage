package wai.clas.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.LoadingDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;

public class MainActivity extends BaseActivity {

    String user_type;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.main_tv1)
    TextView mainTv1;
    @Bind(R.id.main_tv2)
    TextView mainTv2;
    @Bind(R.id.main_tv3)
    TextView mainTv3;
    @Bind(R.id.main_tv4)
    TextView mainTv4;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
    }

    @Override
    public void initEvents() {
        if (("0").equals(user_type)) {//用户
            mainTv1.setText("签到");
            mainTv2.setText("课堂测验");
            mainTv3.setText("提问管理");
            mainTv4.setText("个人中心");
        }
        mainTv1.setOnClickListener(v -> new LoadingDialog(this).show());//签到管理
        mainTv2.setOnClickListener(view -> Utils.IntentPost(ClassTestActivity.class));//课程管理
        mainTv3.setOnClickListener(view -> Utils.IntentPost(AskManageActivity.class));//提问管理
        mainTv4.setOnClickListener(v -> Utils.IntentPost(UserCenterActivity.class));//个人中心
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

}
