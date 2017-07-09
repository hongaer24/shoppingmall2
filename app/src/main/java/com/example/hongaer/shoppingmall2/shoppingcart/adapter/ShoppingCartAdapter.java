package com.example.hongaer.shoppingmall2.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.view.AddSubView;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;

/**
 * Created by hongaer on 2017/7/9.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter< ShoppingCartAdapter .ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> datas;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView=View.inflate(mContext,R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          GoodsBean goodsBean=datas.get(position);
          holder.cbGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constans.BASE_URL_IMAGES+goodsBean.getFigure()).into(holder.ivGov);
          holder.tvDescGov.setText(goodsBean.getName());
          holder.tvPriceGov.setText(goodsBean.getCover_price());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cbGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private AddSubView addSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubView = (AddSubView) itemView.findViewById(R.id.AddSubView);
        }
    }


}
