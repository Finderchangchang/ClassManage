package wai.clas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.tsz.afinal.view.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.iwf.photopicker.PhotoPicker;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;
import wai.clas.model.AskManage;
import wai.clas.model.ClassTest;
import wai.clas.model.UserModel;

public class AddClassActivity extends BaseActivity {
    ClassTest model;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.title_et)
    EditText titleEt;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.class1_iv)
    ImageView class1Iv;
    @Bind(R.id.class2_iv)
    ImageView class2Iv;
    @Bind(R.id.class3_iv)
    ImageView class3Iv;
    @Bind(R.id.save_btn)
    Button saveBtn;


    @Override
    public void initViews() {
        model = new ClassTest();
    }

    @Override
    public void initEvents() {
        saveBtn.setOnClickListener(view -> {
            String title = titleEt.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastShort("问题标题不能为空");
            } else {
                if (imgs.size() > 0) {
                    load();
                } else {
                    save(new ArrayList<>());
                }
            }
        });
        class1Iv.setOnClickListener(view -> {
            click_position = 1;
            if (imgs.size() >= 1) {//显示详情
                Intent intent = new Intent(AddClassActivity.this, ImgDetailActivity.class);
                intent.putExtra("url", imgs.get(0));
                startActivityForResult(intent, 1);
            } else {//选取图片
                PhotoPicker.builder()
                        .setPhotoCount(3 - imgs.size())
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
            }
        });
        class2Iv.setOnClickListener(view -> {
            click_position = 2;
            if (imgs.size() >= 2) {//显示详情
                Intent intent = new Intent(AddClassActivity.this, ImgDetailActivity.class);
                intent.putExtra("url", imgs.get(1));
                startActivityForResult(intent, 1);
            } else {//选取图片
                PhotoPicker.builder()
                        .setPhotoCount(3 - imgs.size())
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
            }
        });
        class3Iv.setOnClickListener(view -> {
            click_position = 3;
            if (imgs.size() >= 3) {//显示详情
                Intent intent = new Intent(AddClassActivity.this, ImgDetailActivity.class);
                intent.putExtra("url", imgs.get(2));
                startActivityForResult(intent, 1);
            } else {
                PhotoPicker.builder()
                        .setPhotoCount(3 - imgs.size())
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
            }
        });
        refreshImg();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                imgs.addAll(photos);
                refreshImg();
            }
        }
        if (resultCode == 88) {
            if (imgs.size() >= click_position) {
                imgs.remove(click_position - 1);
                refreshImg();
            }
        }
    }

    int click_position = 0;
    List<String> imgs = new ArrayList<>();

    void refreshImg() {
        for (int i = 0; i < imgs.size(); i++) {
            if (i == 0) {
                class1Iv.setImageBitmap(Utils.getBitmapByFile(imgs.get(0)));
            } else if (i == 1) {
                class2Iv.setImageBitmap(Utils.getBitmapByFile(imgs.get(1)));
            } else if (i == 2) {
                class3Iv.setImageBitmap(Utils.getBitmapByFile(imgs.get(2)));
            }
        }
        if (imgs.size() == 0) {
            class1Iv.setImageResource(R.mipmap.add_img);
            class1Iv.setVisibility(View.VISIBLE);
            class2Iv.setVisibility(View.INVISIBLE);
            class3Iv.setVisibility(View.INVISIBLE);
        } else if (imgs.size() == 1) {
            class2Iv.setImageResource(R.mipmap.add_img);
            class1Iv.setVisibility(View.VISIBLE);
            class2Iv.setVisibility(View.VISIBLE);
            class3Iv.setVisibility(View.INVISIBLE);
        } else if (imgs.size() >= 2) {
            if (imgs.size() == 2) {
                class3Iv.setImageResource(R.mipmap.add_img);
            }
            class1Iv.setVisibility(View.VISIBLE);
            class2Iv.setVisibility(View.VISIBLE);
            class3Iv.setVisibility(View.VISIBLE);
        }
    }

    void load() {
        final String[] filePaths = new String[imgs.size()];
        for (int i = 0; i < imgs.size(); i++) {
            filePaths[i] = imgs.get(i);
        }
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    save(urls);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                ToastShort("上传失败，请重试");
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    void save(List<String> urls) {
        UserModel user = new UserModel();
        user.setObjectId(Utils.getCache("user_id"));
        model.setTeacher(user);
        model.setTitle(titleEt.getText().toString().trim());
        model.setContent(contentEt.getText().toString().trim());
        if (urls.size() != 0) {
            if (urls.size() > 2) {
                model.setImg3(urls.get(2));
                model.setImg2(urls.get(1));
                model.setImg1(urls.get(0));
            } else if (urls.size() > 1) {
                model.setImg2(urls.get(1));
                model.setImg1(urls.get(0));
            } else {
                model.setImg1(urls.get(0));
            }
        }
        model.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastShort("添加成功");
                    setResult(99);
                    finish();
                } else {
                    ToastShort("请检查网络连接");
                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_add_class;
    }
}
