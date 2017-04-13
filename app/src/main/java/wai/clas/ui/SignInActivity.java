package wai.clas.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import wai.clas.base.BaseActivity;
import wai.clas.method.CommonAdapter;
import wai.clas.method.CommonViewHolder;
import wai.clas.method.Utils;
import wai.clas.model.OpenClass;
import wai.clas.model.UserModel;

/**
 * 签到页面
 */
public class SignInActivity extends BaseActivity {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    String user_type;
    @Bind(R.id.teacher_name_tv)
    TextView teacherNameTv;
    @Bind(R.id.state_iv)
    ImageView stateIv;
    @Bind(R.id.main_lv)
    ListView mainLv;
    CommonAdapter<OpenClass> commonAdapter;
    List<OpenClass> list;
    boolean state = false;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
        list = new ArrayList<>();
        commonAdapter = new CommonAdapter<OpenClass>(this, list, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, OpenClass openClass, int position) {
                holder.setText(R.id.content_tv, position);
                holder.setVisible(R.id.top_ll, false);
                holder.setText(R.id.time_tv, openClass.getCreatedAt());
            }
        };
        mainLv.setAdapter(commonAdapter);
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mAMapLocationListener);
        BmobQuery<OpenClass> button_query = new BmobQuery<>();
        button_query.include("teacher");
        button_query.addWhereEqualTo("state", "true");
        button_query.findObjects(new FindListener<OpenClass>() {
            @Override
            public void done(List<OpenClass> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mLocationClient.startLocation();
                        OpenClass openClass = list.get(0);
                        state = openClass.isState();
                        stateIv.setImageResource(openClass.isState() ? R.mipmap.btn_open : R.mipmap.btn_close);
                    }
                }
            }
        });
        stateIv.setOnClickListener(v -> {
            if (!state) {
                if (("1").equals(user_type)) {//教师：插入一条记录
                    OpenClass openClass = new OpenClass();
                    openClass.setLat(lat + "");
                    openClass.setLng(lon + "");
                    openClass.setState(true);
                    UserModel model = new UserModel();
                    model.setObjectId(Utils.getCache("user_id"));
                    openClass.setTeacher(model);
                    openClass.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                } else {//学生

                }
            }
            state = !state;
            stateIv.setImageResource(state ? R.mipmap.btn_open : R.mipmap.btn_close);
        });
        if (("1").equals(user_type)) {//教师
            BmobQuery<OpenClass> query = new BmobQuery<>();
            query.include("teacher");
            UserModel model = new UserModel();
            model.setObjectId(Utils.getCache("user_id"));
            query.addWhereEqualTo("teacher", model);
            query.findObjects(new FindListener<OpenClass>() {
                @Override
                public void done(List<OpenClass> classes, BmobException e) {
                    if (e == null) {
                        list = classes;
                        commonAdapter.refresh(list);
                    }
                }
            });
        }
    }

    @Override
    public void initEvents() {

    }

    Double lat;
    Double lon;
    /**
     * 开启定位
     */
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                }
            }
        }
    };

    @Override
    public int setLayout() {
        return R.layout.activity_sign_in;
    }
}
