package com.example.hongaer.shoppingmall2.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;

/**
 * Created by hongaer on 2017/7/3.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    public static final int HOT = 5;
    private final Context mContext;
    private final ResultDataBean.ResultBean resultbean;
    private final LayoutInflater mLayoutInflater;
    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;
    /**
     * 活动
     */
    public static final int ACT = 2;
    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */

    /**
 *  当前类型
 */

    public int currentType = BANNER;

    public HomeFragmentAdapter(Context mContext, ResultDataBean.ResultBean resultbean) {
          this.mContext=mContext;
          this.resultbean=resultbean;
          mLayoutInflater=LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
