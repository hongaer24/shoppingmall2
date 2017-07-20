package com.example.hongaer.shoppingmall2.user.fragmet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.LoginActivity;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.hankkin.gradationscroll.GradationScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hongaer on 2017/7/1.
 */

public class UserFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {

    @BindView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_all_order)
    TextView tvAllOrder;
    @BindView(R.id.tv_user_pay)
    TextView tvUserPay;
    @BindView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @BindView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @BindView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @BindView(R.id.tv_user_location)
    TextView tvUserLocation;
    @BindView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @BindView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.tv_user_prize)
    TextView tvUserPrize;
    @BindView(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @BindView(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @BindView(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @BindView(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @BindView(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @BindView(R.id.ib_user_message)
    ImageButton ibUserMessage;
    Unbinder unbinder;
    private int height;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        return view;
    }

    public void initData() {
        super.initData();
        initListeners();

    }
    private void initListeners() {

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                //得到头部相对布局的高度

                height = rlHeader.getHeight();

                //监听ScrollView滑动监听
                scrollview.setScrollViewListener(UserFragment.this);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        tvUsercenter.setTextColor(Color.argb((int)0, 255,255,255));
        tvUsercenter.setBackgroundColor(Color.argb((int)0, 0,0,255));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_username, R.id.ib_user_setting, R.id.ib_user_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
               // Toast.makeText(mContext,"登录注册",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_user_setting:
                Toast.makeText(mContext,"设置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_message:
                Toast.makeText(mContext,"消息",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置标题的背景颜色  -透明
            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 0,0,255));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            //滑动距离 ： 总距离 = 改变的透明度 ： 总透明度
            //改变的透明度 = (滑动距离 ：总距离) *总透明度

            tvUsercenter.setTextColor(Color.argb((int) alpha, 255,255,255));
            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 0,0,255));
        } else {
            //滑动到banner下面设置普通颜色 - 非透明
            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 0,0,255));
        }
    }
}
