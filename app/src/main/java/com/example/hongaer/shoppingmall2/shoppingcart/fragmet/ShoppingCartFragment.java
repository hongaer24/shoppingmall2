package com.example.hongaer.shoppingmall2.shoppingcart.fragmet;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;

import java.util.List;

/**
 * Created by hongaer on 2017/7/1.
 */

public class ShoppingCartFragment extends BaseFragment{
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
        textView.setText("购物车");

        List<GoodsBean> goodsBeanList= CartStorage.getInstance().getAllData();
              for(int i=0;i<goodsBeanList.size();i++){
                  Log.i("666687878",goodsBeanList.get(i).toString());
              }
    }
}
