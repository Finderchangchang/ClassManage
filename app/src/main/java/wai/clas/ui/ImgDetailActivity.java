package wai.clas.ui;

import android.os.Bundle;
import android.widget.ImageView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;

public class ImgDetailActivity extends BaseActivity {
    String url;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.total_iv)
    ImageView totalIv;

    @Override
    public void initViews() {
        url = getIntent().getStringExtra("url");
        toolbar.setLeftClick(() -> finish());
        toolbar.setRightClick(() -> {
            setResult(88);
            finish();
        });
    }

    @Override
    public void initEvents() {
        totalIv.setImageBitmap(Utils.getBitmapByFile(url));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_img_detail;
    }
}
