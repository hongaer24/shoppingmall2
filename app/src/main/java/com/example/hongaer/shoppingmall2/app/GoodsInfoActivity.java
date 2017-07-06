package com.example.hongaer.shoppingmall2.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.utils.Constans;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private LinearLayout ll_root;
    private Button btn_more;
    private GoodsBean goodsBean;
    // private CartProvider cartProvider;


    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);
        tvMoreShare = (TextView) findViewById(R.id.tv_more_share);
        tvMoreSearch = (TextView) findViewById(R.id.tv_more_search);
        tvMoreHome = (TextView) findViewById(R.id.tv_more_home);


        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);
        tvMoreShare.setOnClickListener(this);
        tvMoreSearch.setOnClickListener(this);
        tvMoreHome.setOnClickListener(this);

    }


    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            finish();
        } else if (v == ibGoodInfoMore) {
            Toast.makeText(GoodsInfoActivity.this, "更多", Toast.LENGTH_SHORT).show();
        } else if (v == btnGoodInfoAddcart) {
            Toast.makeText(GoodsInfoActivity.this, "添加购物车", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            Toast.makeText(GoodsInfoActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCollection) {
            Toast.makeText(GoodsInfoActivity.this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {
            Toast.makeText(GoodsInfoActivity.this, "购物车", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreShare) {
            Toast.makeText(GoodsInfoActivity.this, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreSearch) {
            Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreHome) {
            Toast.makeText(GoodsInfoActivity.this, "首页", Toast.LENGTH_SHORT).show();

        }
    }
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_goods_info);
            findViews();

            //接收数据
                 goodsBean= (GoodsBean) getIntent().getSerializableExtra("goodsBean");
                 if(goodsBean!=null){
                   //  Toast.makeText(this,"goodsBean=="+goodsBean.toString(),Toast.LENGTH_SHORT).show();
                     setDataForView(goodsBean);
                 }


        }
    /**
     *  设置数据
    */

    private void setDataForView(GoodsBean goodsBean) {
        Glide.with(this).load(Constans.BASE_URL_IMAGES+goodsBean.getFigure()).into( ivGoodInfoImage);
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText("¥"+goodsBean.getCover_price());
        setWebViewData(goodsBean.getProduct_id());

    }

    private void setWebViewData(String product_id) {
          if(product_id!=null){
              wbGoodInfoMore.loadUrl("http://www.atguigu.com");
              WebSettings webSettings =  wbGoodInfoMore.getSettings();
              webSettings.setUseWideViewPort(true);//双击可变大小
              webSettings.setJavaScriptEnabled(true);

              webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
              wbGoodInfoMore.setWebViewClient(new WebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                   //返回值是 true 的时候控制去 WebView 打开，为 false 调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
});


          }
    }

}