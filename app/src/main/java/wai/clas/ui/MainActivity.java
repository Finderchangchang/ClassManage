package wai.clas.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;

public class MainActivity extends BaseActivity {

    String user_type;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
    }

    @Override
    public void initEvents() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

}
