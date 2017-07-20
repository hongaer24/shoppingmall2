package com.example.hongaer.shoppingmall2.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hongaer.shoppingmall2.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    int[] imgRes=new int[]{
            R.mipmap.map_1,
            R.mipmap.map_2,
            R.mipmap.map_3,
            R.mipmap.map_4
    };

    private List<View> mViewList=new ArrayList<>();
    private ViewPager mVpGuide;
    private Button mbtnStart;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initData();
         mbtnStart= (Button) findViewById(R.id.btn_start);
        mVpGuide = (ViewPager) findViewById(R.id.viewpager);
        mVpGuide.setAdapter(new MyPagerAdapter());
        mVpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                  if(position==imgRes.length-1){
                        mbtnStart.setVisibility(View.VISIBLE);

                  }else {
                       mbtnStart.setVisibility(View.GONE);
                  }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(GuideActivity.this,MainActivity.class));
                 finish();
            }
        });
    }

    private void initData() {
            for(int i=0;i<imgRes.length;i++) {
                View view = getLayoutInflater().inflate(R.layout.guide_item, null);
                ImageView ivGuide = (ImageView) view.findViewById(R.id.iv_guide);
                ivGuide.setBackgroundResource(imgRes[i]);
                mViewList.add(view);
            }
    }
    class MyPagerAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
              View view=mViewList.get(position);
              container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
                   container.removeView(mViewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
