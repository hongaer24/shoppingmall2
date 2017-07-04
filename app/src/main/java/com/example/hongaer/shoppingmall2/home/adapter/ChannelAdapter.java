package com.example.hongaer.shoppingmall2.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;

/**
 * Created by hongaer on 2017/7/4.
 */

public class ChannelAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultDataBean.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context mContext, List<ResultDataBean.ResultBean.ChannelInfoBean> channel_info) {
                        this.mContext=mContext;
                        this.datas=channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
        if(convertView==null){
             convertView=View.inflate(mContext, R.layout.item_channel,null);
             holder=new ViewHolder();
             holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_channel);
             holder.tv_title= (TextView) convertView.findViewById(R.id.tv_channel);
             convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        ResultDataBean.ResultBean.ChannelInfoBean channelInfoBean=datas.get(position);
        holder.tv_title.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext).load(Constans.BASE_URL_IMAGES+channelInfoBean.getImage()).into( holder.iv_icon);


        return convertView;
    }
     static  class ViewHolder{
            ImageView iv_icon;
            TextView  tv_title;


     }
}
