package wai.clas.ui;

import android.os.Bundle;
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
import wai.clas.base.BaseActivity;
import wai.clas.method.CommonAdapter;
import wai.clas.method.CommonViewHolder;
import wai.clas.model.AskManage;
import wai.clas.model.AskRecord;

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
                holder.setText(R.id.title_tv, askRecord.getUser().getUsername());
                holder.setText(R.id.content_tv, askRecord.getAnswer());
                holder.setText(R.id.time_tv, askRecord.getCreatedAt());
            }
        };
        mainLv.setAdapter(commonAdapter);
        BmobQuery<AskRecord> query = new BmobQuery<>();
        AskManage manage = new AskManage();
        manage.setObjectId(model.getObjectId());
        query.addWhereEqualTo("askid", manage);
        query.include("user");
        query.findObjects(new FindListener<AskRecord>() {
            @Override
            public void done(List<AskRecord> list1, BmobException e) {
                if (e == null) {
                    records = list1;
                    commonAdapter.refresh(records);
                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ask_detail;
    }
}
