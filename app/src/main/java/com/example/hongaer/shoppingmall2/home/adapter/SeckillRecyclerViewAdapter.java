package com.example.hongaer.shoppingmall2.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.viewHolder> {
    private final List<ResultDataBean.ResultBean.SeckillInfoBean.ListBean> list;
    private final Context mContext;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultDataBean.ResultBean.SeckillInfoBean.ListBean> list) {
                   this.list=list;
                    this.mContext=mContext;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View itemView=View.inflate(mContext, R.layout.item_seckill,null);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        ResultDataBean.ResultBean.SeckillInfoBean.ListBean listBean=list.get(position);

        Glide.with(mContext).load(Constans.BASE_URL_IMAGES+listBean.getFigure()).into(holder.ivFigure);
        holder.tvCoverPrice.setText(listBean.getCover_price());
        holder.tvOriginPrice.setText(listBean.getOrigin_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;
          public viewHolder(View itemView) {
              super(itemView);
              ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
              tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
              tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
          }
      }
}
