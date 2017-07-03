package com.example.hongaer.shoppingmall2.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongaer on 2017/7/3.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    public static final int HOT = 5;
    private final ResultDataBean.ResultBean resultbean;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


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
        this.mContext = mContext;
        this.resultbean = resultbean;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        }
         return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                   if(getItemViewType(position)==BANNER){
                       BannerViewHolder bannerViewHolder= (BannerViewHolder) holder;
                       bannerViewHolder.setData(resultbean.getBanner_info());
                   }


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
    class BannerViewHolder extends RecyclerView.ViewHolder{

            private Context mContext;
            private Banner banner;
        public BannerViewHolder(Context mContext,View itemView ) {
            super(itemView);
            this.mContext=mContext;
            this.banner= (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultDataBean.ResultBean.BannerInfoBean> banner_info) {
                       List<String> imagesUrl=new ArrayList<>();
                        for(int i=0;i<banner_info.size();i++){
                            String imageUrl=banner_info.get(i).getImage();
                            imagesUrl.add(imageUrl);
                        }
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                        banner.setBannerAnimation(Transformer.Accordion);
                        banner.setImages(imagesUrl, new OnLoadImageListener() {
                            @Override
                            public void OnLoadImage(ImageView view, Object url) {

                                Glide.with(mContext).load(Constans.BASE_URL_IMAGES+url).into(view);
                            }
                        });

        }
    }
}
