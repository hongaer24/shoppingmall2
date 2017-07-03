package com.example.hongaer.shoppingmall2.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.adapter.HomeFragmentAdapter;
import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by hongaer on 2017/7/1.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private ResultDataBean.ResultBean resultbean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView)
                view.findViewById(R.id.tv_message_home);
//设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        String url = Constans.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                           Log.e(TAG,"首页请求失败==="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag","首页请求成功==="+response);
                        processData(response);
                    }

                });
    }

    private void processData(String json) {
        ResultDataBean resultdatabean = JSON.parseObject(json, ResultDataBean.class);
        resultbean = resultdatabean.getResult();
             //Log.e(TAG,"解析成功===="+ resultbean.getBanner_info().get(1).getImage();
         if(resultbean!=null){
                adapter=new HomeFragmentAdapter(mContext,resultbean);
                rvHome.setAdapter(adapter);
                rvHome.setLayoutManager(new GridLayoutManager(mContext,1));
         }

         else{}

    }

    private void initListener() {
//置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//回到顶部
                rvHome.scrollToPosition(0);
            }
        });
//搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索",
                        Toast.LENGTH_SHORT).show();
            }
        });
//消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}