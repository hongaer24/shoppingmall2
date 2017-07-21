package com.example.hongaer.shoppingmall2.type.fragmet;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.base.BaseFragment;

/**
 * Created by hongaer on 2017/7/15.
 */

public class TagFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("标签Fragment内容");
    }
}
