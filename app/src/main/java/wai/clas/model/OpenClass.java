package wai.clas.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/13.
 */

public class OpenClass extends BmobObject {
    UserModel teacher;
    boolean state;
    String lng;
    String lat;

    public UserModel getTeacher() {
        return teacher;
    }

    public void setTeacher(UserModel teacher) {
        this.teacher = teacher;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
