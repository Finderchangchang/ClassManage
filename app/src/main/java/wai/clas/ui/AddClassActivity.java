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
                UserModel user = new UserModel();
                user.setObjectId(Utils.getCache("user_id"));
//                model.setTitle(user);
//                manage.setTitle(title);
//                manage.setContent(contentEt.getText().toString().trim());
//                manage.save(new SaveListener<String>() {
//                    @Override
//                    public void done(String s, BmobException e) {
//                        if (e == null) {
//                            ToastShort("添加成功");
//                            setResult(99);
//                            finish();
//                        } else {
//                            ToastShort("请检查网络连接");
//                        }
//                    }
//                });
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
                        .setPhotoCount(2)
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
                        .setPhotoCount(1)
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
                        .setPhotoCount(3)
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
                ToastShort(imgs.size() + "");
            }
        }
        if (resultCode == 88) {
            if (imgs.size() >= click_position) {
                imgs.remove(click_position - 1);
                ToastShort(imgs.size() + "");
                refreshImg();
            }
        }
    }

    int now_size = 0;//当前图片
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
        } else if (imgs.size() == 2) {
            class3Iv.setImageResource(R.mipmap.add_img);
            class1Iv.setVisibility(View.VISIBLE);
            class2Iv.setVisibility(View.VISIBLE);
            class3Iv.setVisibility(View.VISIBLE);
        }
    }

    void load() {
        List<String> photos = new ArrayList<>();
        final String[] filePaths = new String[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            filePaths[i] = photos.get(i);
        }
        now_size = photos.size();
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    for (int i = 0; i < files.size(); i++) {
                        if (i == 0) {
                            class1Iv.setImageBitmap(Utils.getBitmapByFile(photos.get(0)));
                        } else if (i == 1) {
                            class2Iv.setImageBitmap(Utils.getBitmapByFile(photos.get(1)));
                        } else if (i == 2) {
                            class3Iv.setImageBitmap(Utils.getBitmapByFile(photos.get(2)));
                        }
                    }
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {

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

    @Override
    public int setLayout() {
        return R.layout.activity_add_class;
    }
}
