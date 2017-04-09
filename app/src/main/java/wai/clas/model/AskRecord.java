package wai.clas.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Finder丶畅畅 on 2017/4/8 21:04
 * QQ群481606175
 */

public class AskRecord extends BmobObject {
    AskManage askid;
    UserModel user;
    String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AskManage getAskid() {
        return askid;
    }

    public void setAskid(AskManage askid) {
        this.askid = askid;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
