package wai.clas.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 课堂测验
 * Created by Finder丶畅畅 on 2017/4/7 22:20
 * QQ群481606175
 */

public class ClassTest extends BmobObject implements Serializable {
    String title;
    String content;
    String img1;
    String img2;
    String img3;

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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
}
