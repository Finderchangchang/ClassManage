package wai.clas.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/13.
 */

public class OpenClassRecord extends BmobObject {
    UserModel student;

    public UserModel getStudent() {
        return student;
    }

    public void setStudent(UserModel student) {
        this.student = student;
    }
}
