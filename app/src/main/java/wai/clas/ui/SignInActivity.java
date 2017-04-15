package wai.clas.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import wai.clas.base.BaseActivity;
import wai.clas.method.CommonAdapter;
import wai.clas.method.CommonViewHolder;
import wai.clas.method.Utils;
import wai.clas.model.OpenClass;
import wai.clas.model.OpenClassRecord;
import wai.clas.model.UserModel;

/**
 * 签到页面
 */
public class SignInActivity extends BaseActivity {
    String user_type;
    @Bind(R.id.main_lv)
    ListView mainLv;
    CommonAdapter<OpenClassRecord> commonAdapter;
    List<OpenClass> list;
    List<OpenClassRecord> records;
    CommonAdapter<OpenClass> openClassCommonAdapter;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
        list = new ArrayList<>();
        records = new ArrayList<>();
        commonAdapter = new CommonAdapter<OpenClassRecord>(this, records, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, OpenClassRecord openClass, int position) {
                holder.setText(R.id.content_tv, (position + 1) + "   " + openClass.getStudent().getUsername());
                holder.setVisible(R.id.top_ll, false);
                holder.setText(R.id.time_tv, openClass.getCreatedAt().substring(5, 16));
            }
        };
        openClassCommonAdapter = new CommonAdapter<OpenClass>(this, list, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, OpenClass openClass, int position) {
                holder.setText(R.id.content_tv, (position + 1));
                holder.setVisible(R.id.top_ll, false);
                holder.setText(R.id.time_tv, openClass.getCreatedAt().substring(5, 16));
            }
        };
        UserModel model = new UserModel();
        model.setObjectId(Utils.getCache("user_id"));
        if (("1").equals(user_type)) {
            BmobQuery<OpenClass> query = new BmobQuery<>();
            query.findObjects(new FindListener<OpenClass>() {
                @Override
                public void done(List<OpenClass> li, BmobException e) {
                    if (e == null) {
                        list = li;
                        mainLv.setAdapter(openClassCommonAdapter);
                    }
                }
            });
            mainLv.setOnItemClickListener((parent, view, position, id) -> {

            });
        } else {
            BmobQuery<OpenClassRecord> recordBmobQuery = new BmobQuery<>();
            recordBmobQuery.include("student");
            recordBmobQuery.addWhereEqualTo("student", model);
            recordBmobQuery.findObjects(new FindListener<OpenClassRecord>() {
                @Override
                public void done(List<OpenClassRecord> list, BmobException e) {
                    if (e == null) {
                        records = list;
                        mainLv.setAdapter(commonAdapter);
                    }
                }
            });
        }

    }

    @Override
    public void initEvents() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_sign_in;
    }
}
