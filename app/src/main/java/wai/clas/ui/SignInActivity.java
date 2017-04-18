package wai.clas.ui;

import android.widget.ListView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    CommonAdapter<OpenClassRecord> commonAdapter;
    List<OpenClass> list;
    List<OpenClassRecord> records;
    CommonAdapter<OpenClass> openClassCommonAdapter;

    @Override
    public void initViews() {
        user_type = Utils.getCache("user_type");
        list = new ArrayList<>();
        records = new ArrayList<>();
        toolbar.setLeftClick(() -> finish());
        commonAdapter = new CommonAdapter<OpenClassRecord>(this, records, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, OpenClassRecord openClass, int position) {
                holder.setText(R.id.content_tv, (position + 1) + "   " + openClass.getStudent().getNickname());
                holder.setVisible(R.id.top_ll, false);
                holder.setText(R.id.time_tv, openClass.getCreatedAt().substring(5, 16));
            }
        };
        openClassCommonAdapter = new CommonAdapter<OpenClass>(this, list, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, OpenClass openClass, int position) {
                holder.setText(R.id.content_tv, (position + 1) + "");
                holder.setVisible(R.id.top_ll, false);
                holder.setText(R.id.time_tv, openClass.getCreatedAt().substring(5, 16));
            }
        };
        UserModel model = new UserModel();
        model.setObjectId(Utils.getCache("user_id"));
        if (("1").equals(user_type)) {//教师
            mainLv.setAdapter(openClassCommonAdapter);
            BmobQuery<OpenClass> query = new BmobQuery<>();
            query.include("teacher");
            query.order("-createdAt");
            query.findObjects(new FindListener<OpenClass>() {
                @Override
                public void done(List<OpenClass> li, BmobException e) {
                    if (e == null) {
                        list = li;
                        openClassCommonAdapter.refresh(list);
                    }
                }
            });
            mainLv.setOnItemClickListener((parent, view, position, id) ->
                    Utils.IntentPost(ClassRecordActivity.class, intent -> intent.putExtra("id", list.get(position).getObjectId()))
            );
        } else {//用户
            mainLv.setAdapter(commonAdapter);
            BmobQuery<OpenClassRecord> recordBmobQuery = new BmobQuery<>();
            recordBmobQuery.include("student");
            recordBmobQuery.addWhereEqualTo("student", model);
            recordBmobQuery.order("-createdAt");
            recordBmobQuery.findObjects(new FindListener<OpenClassRecord>() {
                @Override
                public void done(List<OpenClassRecord> list, BmobException e) {
                    if (e == null) {
                        records = list;
                        commonAdapter.refresh(records);
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
