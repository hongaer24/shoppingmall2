package com.example.hongaer.shoppingmall2.shoppingcart.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;

/**
 * Created by hongaer on 2017/7/8.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private final Context mContext;
    private ImageView btn_sub;
    private ImageView btn_add;
    private TextView tv_count;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;
    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        //把布局文件实例化，并且加载到AddSubView中
        View.inflate(context, R.layout.add_sub_view,this);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        tv_count = (TextView) findViewById(R.id.tv_count);

        int value=getValue();
         setValue(value);

        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.btn_add:
                 addNumber();
                break;

             case  R.id.btn_sub:
                 subNumber();
                 break;
         }
     //   Toast.makeText(mContext,"当前商品数目=="+value,Toast.LENGTH_SHORT).show();

    }

    private void subNumber() {
        if (value > minValue) {
            value -= 1;
        }
        setValue(value);
        if(onNumberChangeListener!=null){
             onNumberChangeListener.onNumberChange(value);
        }
    }

    private void addNumber() {
        if (value < maxValue) {
            value += 1;
        }
        setValue(value);
        if(onNumberChangeListener!=null){
            onNumberChangeListener.onNumberChange(value);
        }
    }

    public int getValue() {
        String countStr = tv_count.getText().toString().trim();//文本内容
        if (!TextUtils.isEmpty( countStr)) {
            value = Integer.parseInt(countStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_count.setText(value+"");

    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumberChangeListener{
       public void  onNumberChange(int value);
    }
    private OnNumberChangeListener onNumberChangeListener;
    public  void  setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener){
        this.onNumberChangeListener=onNumberChangeListener;
    }

}
