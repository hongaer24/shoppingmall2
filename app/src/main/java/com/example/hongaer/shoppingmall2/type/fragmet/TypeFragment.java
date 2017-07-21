package com.example.hongaer.shoppingmall2.type.fragmet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongaer on 2017/7/1.
 */

public class TypeFragment extends BaseFragment {

    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;

    String[] titls = {"分类","标签"};
    private ArrayList<BaseFragment> fragments;
    private Fragment tempFragemnt;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.bind(this, view);

        return view;
    }

    public void initData() {
        super.initData();

        tl1.setTabData(titls);
        //设置点击SegmentTabLayout的监听
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
              //  Toast.makeText(mContext, "position=" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        initFragment();

    }
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());
        //默认显示ListFragment
        switchFragment(fragments.get(0));
    }
    private void switchFragment(Fragment currentFragment) {
        if (tempFragemnt != currentFragment) {

            if (currentFragment != null) {
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!currentFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (tempFragemnt != null) {
                        transaction.hide(tempFragemnt);
                    }
                    //添加Fragment
                    transaction.add(R.id.fl_type, currentFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (tempFragemnt != null) {
                        transaction.hide(tempFragemnt);
                    }
                    transaction.show(currentFragment).commit();
                }
            }
            tempFragemnt = currentFragment;
        }
    }
                @OnClick(R.id.iv_type_search)
    public void onClick() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }
}








