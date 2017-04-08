package wai.clas.ui;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import wai.clas.base.BaseActivity;
import wai.clas.method.Utils;
import wai.clas.model.AskManage;
import wai.clas.model.AskRecord;
import wai.clas.model.UserModel;

/**
 * 提问详情
 */
public class AskDetailActivity extends BaseActivity {
    String id;

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initEvents() {
        BmobQuery<AskRecord> query = new BmobQuery<>();
        AskManage manage = new AskManage();
        manage.setObjectId(id);
        query.addWhereEqualTo("askid", manage);
        query.include("teacher,student,askid");
        query.findObjects(new FindListener<AskRecord>() {
            @Override
            public void done(List<AskRecord> list1, BmobException e) {
                if (e == null) {

                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ask_detail;
    }
}
