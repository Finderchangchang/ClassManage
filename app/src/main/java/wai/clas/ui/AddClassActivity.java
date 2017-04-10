package wai.clas.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
    }

    @Override
    public int setLayout() {
        return R.layout.activity_add_class;
    }
}
