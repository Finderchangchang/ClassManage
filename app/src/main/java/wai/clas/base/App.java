package wai.clas.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by Finder丶畅畅 on 2017/1/14 21:25
 * QQ群481606175
 */

public class App extends Application {
    private static Context context;
    String key = "1b78313a1de32d8e43213d897e5a80a4";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Bmob.initialize(this, key);
    }


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }
}
