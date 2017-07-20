package com.example.hongaer.shoppingmall2.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.hongaer.shoppingmall2.R;

public class SplashActivity extends Activity {
      Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


       mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getPreferences(MODE_PRIVATE);
                boolean isFirst=sp.getBoolean("isFirst",true);
                Intent intent=new Intent();
                if(isFirst){
                    sp.edit().putBoolean("isFirst",false).commit();
                    //如果是第一次安装进入
                    intent.setClass(SplashActivity.this,GuideActivity.class);

                }else{

                    intent.setClass(SplashActivity.this,MainActivity.class);
                }
                  startActivity(intent);
                   finish();

            }
        },2000);
    }
}
