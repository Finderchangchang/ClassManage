package wai.clas.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Finder丶畅畅 on 2017/4/8 21:04
 * QQ群481606175
 */

public class AskRecord extends BmobObject {
    AskManage askid;
    UserModel teacher;
    UserModel student;

    public AskManage getAskid() {
        return askid;
    }

    public void setAskid(AskManage askid) {
        this.askid = askid;
    }

    public UserModel getTeacher() {
        return teacher;
    }

    public void setTeacher(UserModel teacher) {
        this.teacher = teacher;
    }

    public UserModel getStudent() {
        return student;
    }

    public void setStudent(UserModel student) {
        this.student = student;
    }
}
