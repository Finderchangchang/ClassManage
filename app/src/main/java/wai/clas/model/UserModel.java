package wai.clas.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Finder丶畅畅 on 2017/4/6 22:08
 * QQ群481606175
 */

public class UserModel extends BmobUser {
    String usertype;

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
