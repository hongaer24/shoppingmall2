package com.example.hongaer.shoppingmall2.shoppingcart.fragmet;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MainActivity;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;

import java.util.List;

/**
 * Created by hongaer on 2017/7/1.
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private TextView textView;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private LinearLayout ll_empty_shopcart;
    private ShoppingCartAdapter adapter;
    private static final int ACTION_EDIT = 1;
    private static final int ACTION_COMPLETE = 2;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        ll_empty_shopcart= (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        tvEmptyCartTobuy.setOnClickListener(this);
        initListener();


        return view;
        }
    public void onClick(View v) {
        if ( v == btnCheckOut ) {
            // Handle clicks for btnCheckOut
        } else if ( v == btnDelete ) {
            adapter.deleteData();
            adapter.checkAll();
            if(adapter.getItemCount()==0){
                emptyShoppingCart();
            }
            // Handle clicks for btnDelete
        } else if ( v == btnCollection ) {
            // Handle clicks for btnCollection
        }else  if(v==tvEmptyCartTobuy){
            Intent intent=new Intent(mContext, MainActivity.class);
            intent.putExtra("checkid",R.id.rb_home);
            startActivity(intent);

        }
    }

    private void initListener() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //1.得到状态
                  int action= (int) v.getTag();
                 //2.根据不同状态来处理
                if(action==ACTION_EDIT){
                    //切换为完成状态
                    showDelete();
                }else {
                    //切换成编辑状态
                    hideDelete();
                }

            }
        });
    }

    private void hideDelete() {
        //1.设置状态和文本
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //2.变成非勾选状态
        if(adapter!=null){
            adapter.checkAll_no(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        //3删除视图隐藏
        llDelete.setVisibility(View.GONE);
        //4结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
          //1.设置状态和文本
         tvShopcartEdit.setTag(ACTION_COMPLETE);
         tvShopcartEdit.setText("完成");
          //2.变成非勾选状态
          if(adapter!=null){
              adapter.checkAll_no(false);
              adapter.checkAll();
          }
          //3删除视图显示
        llDelete.setVisibility(View.VISIBLE);
          //4结算视图隐藏
        llCheckAll.setVisibility(View.GONE);


    }


    public void initData() {
        super.initData();
        List<GoodsBean> goodsBeanList= CartStorage.getInstance().getAllData();
       tvShopcartEdit.setVisibility(View.VISIBLE);
        Log.i("78787878","主页数据被初始化了==="+goodsBeanList);
        if(goodsBeanList!=null&&goodsBeanList.size()>0){
            //有数据隐藏
            llCheckAll.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        }else {
            // 没数据显示
            emptyShoppingCart();

        }
        Log.e("tag","主页数据被初始化了");
       // textView.setText("购物车");

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        hideDelete();
    }

    /*private void showData() {
        List<GoodsBean> goodsBeanList= CartStorage.getInstance().getAllData();
        tvShopcartEdit.setVisibility(View.VISIBLE);
        Log.i("78787878","主页数据被初始化了==="+goodsBeanList);
          if(goodsBeanList!=null&&goodsBeanList.size()>0){
               //有数据隐藏
              llCheckAll.setVisibility(View.VISIBLE);
              ll_empty_shopcart.setVisibility(View.GONE);
              adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, checkboxAll, cbAll);
              recyclerview.setAdapter(adapter);
              recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

          }else {
              // 没数据显示
              emptyShoppingCart();

          }
    }
*/
    private void emptyShoppingCart() {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }
}
