package wai.clas.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

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
import wai.clas.model.AskManage;
import wai.clas.model.AskRecord;
import wai.clas.model.UserModel;

/**
 * 提问详情
 */
public class AskDetailActivity extends BaseActivity {
    AskManage model;
    List<AskRecord> records;
    CommonAdapter<AskRecord> commonAdapter;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.time_tv)
    TextView time_tv;
    @Bind(R.id.main_lv)
    ListView mainLv;
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.send_msg_et)
    EditText send_msg_et;
    @Bind(R.id.send_btn)
    Button send_btn;
    @Bind(R.id.main_srl)
    SwipeRefreshLayout main_srl;
    AskManage manage;

    @Override
    public void initViews() {
        model = (AskManage) getIntent().getSerializableExtra("model");
    }

    @Override
    public void initEvents() {
        toolbar.setLeftClick(() -> finish());
        titleTv.setText(model.getTitle());
        contentTv.setText(model.getContent());
        time_tv.setText(model.getCreatedAt());
        records = new ArrayList<>();
        commonAdapter = new CommonAdapter<AskRecord>(this, records, R.layout.item_record) {
            @Override
            public void convert(CommonViewHolder holder, AskRecord askRecord, int position) {
                holder.setText(R.id.title_tv, askRecord.getUser().getNickname());
                holder.setText(R.id.content_tv, askRecord.getAnswer());
                holder.setText(R.id.time_tv, askRecord.getCreatedAt());
                if (("0").equals(askRecord.getUser().getUsertype())) {
                    holder.setText(R.id.user_type_tv, "学生");
                } else {
                    holder.setText(R.id.user_type_tv, "教师");
                }
            }
        };
        mainLv.setAdapter(commonAdapter);

        main_srl.setOnRefreshListener(() -> refresh());
        send_btn.setOnClickListener(v -> {
            AskRecord record = new AskRecord();
            UserModel model = new UserModel();
            model.setObjectId(Utils.getCache("user_id"));
            model.setUsername(Utils.getCache("user_name"));
            model.setUsertype(Utils.getCache("user_type"));
            record.setAskid(manage);
            record.setUser(model);
            record.setAnswer(send_msg_et.getText().toString().trim());
            record.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        records.add(record);
                        commonAdapter.refresh(records);
                        send_msg_et.setText("");
                    } else {
                        ToastShort("发送失败，请检查网络连接");
                    }
                }
            });
        });
        refresh();
    }

    void refresh() {
        BmobQuery<AskRecord> query = new BmobQuery<>();
        manage = new AskManage();
        manage.setObjectId(model.getObjectId());
        query.addWhereEqualTo("askid", manage);
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<AskRecord>() {
            @Override
            public void done(List<AskRecord> list1, BmobException e) {
                if (e == null) {
                    records = list1;
                    commonAdapter.refresh(records);
                }
                main_srl.setRefreshing(false);
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ask_detail;
    }
}
