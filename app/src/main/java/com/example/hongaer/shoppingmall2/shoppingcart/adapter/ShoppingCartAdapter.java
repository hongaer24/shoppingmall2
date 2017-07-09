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
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.hongaer.shoppingmall2.shoppingcart.view.AddSubView;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;

/**
 * Created by hongaer on 2017/7/9.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter< ShoppingCartAdapter .ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.tvShopcartTotal=tvShopcartTotal;
        this.checkboxAll=checkboxAll;
        showTotalPrice();
        // 设置点击事件
        setListener();
        //检验是否全选
       checkAll();
    }

    private void showTotalPrice() {
         tvShopcartTotal.setText("合计"+getTotalPrice());
    }

    private double getTotalPrice() {
           double totalPrice=0;
            if(datas!=null&&datas.size()>0){

                for (int i=0;i<datas.size();i++){
                    GoodsBean goodsBean=datas.get(i);
                    if(goodsBean.isSelected()){
                        totalPrice=totalPrice+Double.valueOf(goodsBean.getNumber())*Double.valueOf(goodsBean.getCover_price());
                    }
                }
            }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView=View.inflate(mContext,R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
          final GoodsBean goodsBean=datas.get(position);
          holder.cbGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constans.BASE_URL_IMAGES+goodsBean.getFigure()).into(holder.ivGov);
          holder.tvDescGov.setText(goodsBean.getName());
          holder.tvPriceGov.setText("¥"+goodsBean.getCover_price());
          holder.addSubView.setValue(goodsBean.getNumber());
          holder.addSubView.setMaxValue(7);
          holder.addSubView.setMinValue(1);

        //设置商品数量变化的监听
         holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
             @Override
             public void onNumberChange(int value) {
                 //1、当前列表内存更新
                   goodsBean.setNumber(value);
                 //2.本地更新
                 CartStorage.getInstance().updataData(goodsBean);

                 //3.刷新适配器
              notifyItemChanged(position);
                 //4.再次计算总计
                 showTotalPrice();
             }
         });




    }

    private void setListener() {
         setOnItemClickListener(new OnItemClickListener() {
             @Override
             public void OnItemClick(int position) {
                   //1.根据位置找到对应bean
                   GoodsBean goodsBean=datas.get(position);
                   //2.设置取反状态
                  goodsBean.setSelected(!goodsBean.isSelected());
                   //3.刷新状态
                   notifyItemChanged(position);
                   //4.检查是否全选
                    checkAll();
                    //5.重新计算总计
                     showTotalPrice();
             }
         });
           checkboxAll.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                     //1.得到状态
                     boolean isCheck=checkboxAll.isChecked();
                     //2.根据状态设置全选非全选
                     checkAll_no(isCheck);
                     //3.计算总价
                      showTotalPrice();
               }
           });

    }

    private void checkAll_no(boolean isCheck) {
        if(datas!=null&&datas.size()>0){
            for(int i=0;i<datas.size();i++){
                GoodsBean goodsBean=datas.get(i);
                 goodsBean.setSelected(isCheck);
                notifyItemChanged(i);

            }
        }
    }

    private void checkAll() {
               int num=0;
          if(datas!=null&&datas.size()>0){
              for(int i=0;i<datas.size();i++){
                  GoodsBean goodsBean=datas.get(i);
                    if(!goodsBean.isSelected()){
                        //非全选
                          checkboxAll.setChecked(false);
                    }else {
                        //全选
                          num++;
                    }
              }
                    if(num==datas.size()){
                        checkboxAll.setChecked(true);
                    }
          }

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if(onItemClickListener!=null){
                         onItemClickListener.OnItemClick(getLayoutPosition());
                     }
                }
            });
        }
    }
     public  interface  OnItemClickListener{

          public void OnItemClick(int position);

     }
     private   OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
