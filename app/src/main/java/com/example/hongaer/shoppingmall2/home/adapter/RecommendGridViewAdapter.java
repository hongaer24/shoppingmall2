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
 * Created by hongaer on 2017/7/5.
 */

public class RecommendGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultDataBean.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context mContext, List<ResultDataBean.ResultBean.RecommendInfoBean> recommend_info) {
                     this.mContext=mContext;
                     this.datas=recommend_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.item_recommend_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ResultDataBean.ResultBean.RecommendInfoBean recommendInfoBean=datas.get(position);
        Glide.with(mContext).load(Constans.BASE_URL_IMAGES+recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText(recommendInfoBean.getCover_price());


        return convertView;
    }
    static  class ViewHolder{
      TextView tv_name;
      TextView tv_price;
      ImageView iv_recommend;



    }
}
