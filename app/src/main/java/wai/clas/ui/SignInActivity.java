package wai.clas.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
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
    int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10;
    OpenClass openClass;
    public AMapLocationClientOption mLocationOption = null;

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            mLocationClient.startLocation();
        }
        mainLv.setAdapter(commonAdapter);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mAMapLocationListener);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationOption);
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
                            if (e == null) {
                                refresh();
                            } else {
                                ToastShort("开启失败");
                            }
                        }
                    });
                } else {//学生
                    LatLng latLng1 = new LatLng(lat, lon);
                    LatLng latLng2 = new LatLng(Double.parseDouble(openClass.getLat()), Double.parseDouble(openClass.getLng()));
                    float distance = AMapUtils.calculateLineDistance(latLng1, latLng2);
                    if (distance < 200) {
                        OpenClassRecord rclass = new OpenClassRecord();
                        UserModel model = new UserModel();
                        model.setObjectId(Utils.getCache("user_id"));
                        rclass.setStudent(model);
                        rclass.setOclass(openClass);
                        rclass.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    refresh();
                                    ToastShort("签到成功");
                                } else {
                                    ToastShort("开启失败");
                                }
                            }
                        });
                    } else {
                        ToastShort("请离老师近一点");
                    }
                }
            } else {
                if (("1").equals(user_type)) {//教师：修改状态
                    openClass.setState(false);
                    openClass.update(openClass.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                refresh();
                            }
                        }
                    });
                } else {
                    ToastShort("您已经上课，不必再签到了~");
                }
            }
        });
        refresh();
    }

    void refresh() {
        BmobQuery<OpenClass> query = new BmobQuery<>();
        UserModel model = new UserModel();
        model.setObjectId(Utils.getCache("user_id"));
        query.addWhereEqualTo("teacher", model);
        query.findObjects(new FindListener<OpenClass>() {
            @Override
            public void done(List<OpenClass> classes, BmobException e) {
                if (e == null) {
                    list = classes;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isState()) {
                            openClass = list.get(i);
                            state = true;
                            break;
                        }
                        if (i == list.size() - 1) {
                            state = false;
                        }
                    }
                    stateIv.setImageResource(openClass.isState() ? R.mipmap.btn_open : R.mipmap.btn_close);
                    if (("1").equals(user_type)) {//教师
                        commonAdapter.refresh(list);
                    } else {//学生(开启状态，查询记录表是否有记录。关闭直接显示：当前不是上课时间)

                    }
                } else {
                    ToastShort("刷新失败~~");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            mLocationClient.startLocation();
        }
    }

    @Override
    public void initEvents() {

    }

    Double lat = 0.0;
    Double lon = 0.0;
    /**
     * 开启定位
     */
    AMapLocationListener mAMapLocationListener = amapLocation -> {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                lat = amapLocation.getLatitude();
                lon = amapLocation.getLongitude();
            }
        }
    };


    @Override
    public int setLayout() {
        return R.layout.activity_sign_in;
    }
}
