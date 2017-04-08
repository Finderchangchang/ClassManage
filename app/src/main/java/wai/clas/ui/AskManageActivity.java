package wai.clas.ui;

import android.text.TextUtils;
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
import wai.clas.model.AskManage;
import wai.clas.model.ClassTest;
import wai.clas.model.UserModel;

/**
 * 提问管理
 * 1.学生--根据当前登录id获得所有提问
 * 2.将所有问题都显示出来
 */
public class AskManageActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    TitleBar toolbar;
    @Bind(R.id.main_lv)
    ListView main_lv;
    List<AskManage> list;
    CommonAdapter<AskManage> commonAdapter;

    @Override
    public void initViews() {
        toolbar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        list = new ArrayList<>();
        commonAdapter = new CommonAdapter<AskManage>(this, list, R.layout.item_ask) {
            @Override
            public void convert(CommonViewHolder holder, AskManage askManage, int position) {
                holder.setText(R.id.title_tv, askManage.getTitle());
                String content = askManage.getContent();
                if (!TextUtils.isEmpty(content)) {
                    if (content.length() > 40) {
                        holder.setText(R.id.content_tv, content + "...");
                    } else {
                        holder.setText(R.id.content_tv, content);
                    }
                }
                holder.setText(R.id.time_tv, askManage.getCreatedAt());
            }
        };
        main_lv.setAdapter(commonAdapter);
        BmobQuery<AskManage> query = new BmobQuery<>();
        UserModel userModel = new UserModel();
        userModel.setObjectId(Utils.getCache("user_id"));
        query.addWhereEqualTo("student", userModel);
        query.findObjects(new FindListener<AskManage>() {
            @Override
            public void done(List<AskManage> list1, BmobException e) {
                if (e == null) {
                    list = list1;
                    commonAdapter.refresh(list);
                }
            }
        });
        main_lv.setOnItemClickListener((adapterView, view, i, l) ->
                Utils.IntentPost(AskDetailActivity.class, intent ->
                        intent.putExtra("id", list.get(i).getObjectId())));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ask_manage;
    }
}
