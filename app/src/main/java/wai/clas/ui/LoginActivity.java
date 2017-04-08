package wai.clas.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;
import wai.clas.model.UserModel;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    RadioGroup login_rg;

    @Override
    public void initViews() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        login_rg = (RadioGroup) findViewById(R.id.login_rg);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
        String name = Utils.getCache("user_name");
        if (!TextUtils.isEmpty(name)) {
//            mEmailView.setText(name);
//            mEmailView.setSelection(name.length());
            Utils.IntentPost(MainActivity.class);
        }
    }

    @Override
    public void initEvents() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    /**
     * 验证账号密码
     */
    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {//登录成功
            BmobUser.loginByAccount(email, password, new LogInListener<UserModel>() {
                @Override
                public void done(UserModel userModel, BmobException e) {
                    if (e != null) {
                        ToastShort("用户名或密码错误，请重新输入");
                    } else {
                        switch (userModel.getUsertype()) {
                            case "0":
                                switch (login_rg.getCheckedRadioButtonId()) {
                                    case R.id.teacher_rb:
                                        ToastShort("用户和账号类型不匹配，请重新选择");
                                        break;
                                    default:
                                        finish();
                                        Utils.IntentPost(MainActivity.class);
                                        Map<String, String> map = new HashMap<>();
                                        map.put("user_id", userModel.getObjectId());
                                        map.put("user_name", userModel.getUsername());
                                        map.put("user_type", userModel.getUsertype());
                                        Utils.putCache(map);
                                        break;
                                }
                                break;
                            default:
                                switch (login_rg.getCheckedRadioButtonId()) {
                                    case R.id.teacher_rb:
                                        finish();
                                        Map<String, String> map = new HashMap<>();
                                        map.put("user_id", userModel.getObjectId());
                                        map.put("user_name", userModel.getUsername());
                                        map.put("user_type", userModel.getUsertype());
                                        Utils.putCache(map);
                                        Utils.IntentPost(MainActivity.class);
                                        break;
                                    default:
                                        ToastShort("用户和账号类型不匹配，请重新选择");
                                        break;
                                }
                                break;
                        }
                    }
                }
            });
        }
    }


}

