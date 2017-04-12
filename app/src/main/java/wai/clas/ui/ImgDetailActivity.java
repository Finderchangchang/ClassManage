package wai.clas.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
        if (url != null) {
            toolbar.setRighttv("删除");
            toolbar.setRightClick(() -> {
                setResult(88);
                finish();
            });
            totalIv.setImageBitmap(Utils.getBitmapByFile(url));
        } else {
            Glide.with(this)
                    .load(getIntent().getStringExtra("url1"))
                    .into(totalIv);
        }
    }

    @Override
    public void initEvents() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_img_detail;
    }
}
