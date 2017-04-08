package wai.clas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.tsz.afinal.view.MenuView;
import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import wai.clas.base.BaseActivity;
import wai.clas.model.UserModel;

public class UserCenterActivity extends BaseActivity {

    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.user_iv)
    ImageView userIv;
    @Bind(R.id.user_center_ll)
    LinearLayout userCenterLl;
    @Bind(R.id.exit_mv)
    MenuView exitMv;

    @Override
    public void initViews() {
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        exitMv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserCenterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出当前账号吗?");
            builder.setNegativeButton("确定", (dialog, which) -> {
                UserModel.logOut();
            });
            builder.setPositiveButton("取消", (dialog2, which) -> {

            });
            builder.show();
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_user_center;
    }
}
