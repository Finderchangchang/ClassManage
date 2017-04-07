package wai.clas.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 课堂测验
 * Created by Finder丶畅畅 on 2017/4/7 22:20
 * QQ群481606175
 */

public class ClassTest extends BmobObject implements Serializable {
    String title;
    String content;
    BmobFile img1;
    BmobFile img2;
    BmobFile img3;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getImg1() {
        return img1;
    }

    public void setImg1(BmobFile img1) {
        this.img1 = img1;
    }

    public BmobFile getImg2() {
        return img2;
    }

    public void setImg2(BmobFile img2) {
        this.img2 = img2;
    }

    public BmobFile getImg3() {
        return img3;
    }

    public void setImg3(BmobFile img3) {
        this.img3 = img3;
    }
}
