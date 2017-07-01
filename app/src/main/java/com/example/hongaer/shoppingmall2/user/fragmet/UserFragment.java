package com.example.hongaer.shoppingmall2.user.fragmet;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.base.BaseFragment;

/**
 * Created by hongaer on 2017/7/1.
 */

public class UserFragment extends BaseFragment{
    private TextView textView;
    @Override
    public View initView() {
        Log.e("tag","主页视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }
    public void initData() {
        super.initData();
        Log.e("tag","主页数据被初始化了");
        textView.setText("个人中心");
    }
}
