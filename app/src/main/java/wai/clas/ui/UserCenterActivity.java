package wai.clas.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.MenuView;
import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;
import wai.clas.model.UserModel;

public class UserCenterActivity extends BaseActivity {

    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.user_iv)
    ImageView userIv;
    @Bind(R.id.user_center_ll)
    LinearLayout userCenterLl;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.qd_mv)
    MenuView qdMv;
    @Bind(R.id.tw_mv)
    MenuView twMv;
    @Bind(R.id.exit_mv)
    MenuView exitMv;
    @Bind(R.id.jc_mv)
    MenuView jc_mv;

    @Override
    public void initViews() {
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        nameTv.setText(Utils.getCache("name"));
        exitMv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserCenterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出当前账号吗?");
            builder.setNegativeButton("确定", (dialog, which) -> {
                UserModel.logOut();
                Utils.putCache("user_name", "");
                MainActivity.main.finish();
                finish();
            });
            builder.setPositiveButton("取消", (dialog2, which) -> {

            });
            builder.show();
        });
        jc_mv.setOnClickListener(view -> Utils.IntentPost(ClassTestActivity.class,intent -> intent.putExtra("kk","1")));
        twMv.setOnClickListener(view -> Utils.IntentPost(AskManageActivity.class,intent -> intent.putExtra("kk","1")));
        qdMv.setOnClickListener(view -> Utils.IntentPost(SignInActivity.class));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_user_center;
    }
}
