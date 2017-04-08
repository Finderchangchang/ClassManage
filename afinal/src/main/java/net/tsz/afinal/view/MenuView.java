package net.tsz.afinal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.R;

/**
 * Created by Administrator on 2017/4/8.
 */

public class MenuView extends LinearLayout {
    int str_left_iv;
    String str_left_tv;
    String str_right_tv;

    TextView left_tv;
    TextView right_tv;
    ImageView left_iv;

    void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.v_menu, this);
        left_tv = (TextView) findViewById(R.id.left_tv);
        right_tv = (TextView) findViewById(R.id.right_tv);
        left_iv = (ImageView) findViewById(R.id.left_iv);
        if (!TextUtils.isEmpty(str_left_tv)) {
            left_tv.setText(str_left_tv);
        }
        if (!TextUtils.isEmpty(str_right_tv)) {
            right_tv.setText(str_right_tv);
        }
        if (str_left_iv != 0) {
            left_iv.setImageResource(str_left_iv);
        }
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuView, defStyleAttr, 0);
        str_left_iv = a.getResourceId(R.styleable.MenuView_menu_left_iv, 0);
        str_left_tv = a.getString(R.styleable.MenuView_menu_left_tv);
        str_right_tv = a.getString(R.styleable.MenuView_menu_right_tv);
        a.recycle();
        init(context);
    }

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
