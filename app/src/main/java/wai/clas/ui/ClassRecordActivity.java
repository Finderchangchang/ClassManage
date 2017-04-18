package wai.clas.ui;

import android.widget.ListView;
import android.widget.TextView;

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
import wai.clas.model.OpenClass;
import wai.clas.model.OpenClassRecord;

/**
 * 课程记录
 */
public class ClassRecordActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.main_lv)
    ListView mainLv;
    @Bind(R.id.sign_num_tv)
    TextView sign_num_tv;
    String id;
    List<OpenClassRecord> list;
    CommonAdapter<OpenClassRecord> adapter;

    @Override
    public void initViews() {
        toolbar.setLeftClick(() -> finish());
        list = new ArrayList<>();
        adapter = new CommonAdapter<OpenClassRecord>(this, list, R.layout.item_ask) {
            @Override
            public void convert(CommonViewHolder holder, OpenClassRecord openClassRecord, int position) {
                holder.setText(R.id.title_tv, openClassRecord.getStudent().getNickname());
                holder.setText(R.id.time_tv, openClassRecord.getCreatedAt().substring(5, 16));
            }
        };
        mainLv.setAdapter(adapter);
    }

    @Override
    public void initEvents() {
        id = getIntent().getStringExtra("id");
        BmobQuery<OpenClassRecord> query = new BmobQuery<>();
        OpenClass openClass = new OpenClass();
        openClass.setObjectId(id);
        query.include("student");
        query.addWhereEqualTo("oclass", openClass);
        query.findObjects(new FindListener<OpenClassRecord>() {
            @Override
            public void done(List<OpenClassRecord> li, BmobException e) {
                if (e == null) {
                    list = li;
                    sign_num_tv.setText("签到人数：" + li.size() + "人");
                    adapter.refresh(list);
                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_class_record;
    }
}
