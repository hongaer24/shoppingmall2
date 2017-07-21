package com.example.hongaer.shoppingmall2.shoppingcart.fragmet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MainActivity;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.hongaer.shoppingmall2.shoppingcart.pay.PayResult;
import com.example.hongaer.shoppingmall2.shoppingcart.pay.SignUtils;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
            pay(v);
           // Toast.makeText(mContext,"去结算",Toast.LENGTH_SHORT).show();
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
    // 商户PID
   // public static final String PARTNER = "2088911876712776";
    public static final String PARTNER = "2088011085074233";
    // 商户收款账号
   // public static final String SELLER = "chenlei@atguigu.com";
    public static final String SELLER = "917356107@qq.com";
    // 商户私钥，pkcs8格式
  //  public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJJlvpnJbOf1UTjIvezfAmTaaz+wqrjuqDuEfmdqD02bcQStpmS23d9LlmPHxumlSGZDSgLhK21ICKbTwA8dteb51FUNIXyrGWOlR2tP+Ggw+IkXlcksLcA6a+wr6BTwa0nGUCGt++66AHm2ShP/Gs5pH+mY4AncvE0wdukSRfx5AgMBAAECgYBEIHj8VBIMN8seY7yOmJEioco7D1nDSbycReIU+fQ5J1cG2FCQmdDScvh/Yp6caEAy5qlwgZyV9GmiSPQdlLPrHlcUaU71zXCL+EHlunzCxOABq5zNIFCInq+vsDRx8OBrRRY1aQe2Z0LayQItk4tjOh10zLva2+VunpzKC8YZSQJBAM5h4IzN3F0prQVxyWmw0m9zqrcFDU67Kwi7MrGPuCIDz1VsIefGfEQ2BMH9r0eVJXAQb0IijcfVpsVDnuC98IsCQQC1mADlXK6wOiO0uEmB7wjbgBRakW7IMmT8TU2Mgja4UzJ5m6ODjRGAVwWzkpiLMZib2QsmgLcxuTerBDX1kEOLAkEAlpb8jkE35hKe2TYpzSDkq8YubtUU3LndsMVHPCCuLsOw6Ze5NbGywuLXneVJnGXLp3WWeR9VbNcMlSu+Jibx+QJBAKORv3zZ9yAVvoPSW6QGQ9wziiHqTfdWLVB18RxXTiLKDfUsFCUytEj+Gcyeh3kZu3TmE/0ig+DuDQ6mRFRlFfECQQCyEcwqbm4hSSMKIhrrbD2HgkPFo8IBVk+eMnfyW1ZajhZjo8XmzaHSNtGYrTvqGUG5oi/4qY6kgezu716yp6HA";
    public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL51jaxQhxW9PnWpW+nz6yJ76tp9eGFXmfGnuxMK+Pmx/qavdsewXOLBfI2OSCR39TzxwMYvCmUrnrt0fVSa7mblbNos2FnMM9ijnx8bsAAhm+i7BKhuaHMunJKH69L+D753zH3P1YIh0ly5DnAr3WPqHydp384qBvb8NS9Tay0HAgMBAAECgYB82PIVknP6fCMFXg8yPQJViIVa1ASlSpdPIXQv93FdvKABA+QI4kMBIXRUFoCT506KtK55OzzFNOLIXoQJgcXj69z0l6pmjJJgXMaBW/9rOzelot13CiGatrIrGngEZO+bCBTud/jQA598zjZ1g182tT+FLDL7GIftW2hC8GqtAQJBAN+XrYsyfL+uSmLdAVEz1vzziU1naGr10Msm1jMnnO/JYdB+84j7FSHxsQ4YOgsmeN5YVsJcVfc/CReOxknns38CQQDaEHnVPDt+Z7sqT7bN0UKh0/CrqkDTiIjhz1lJyIIoqVRoeJjJn1wlEKBV5R9gkTJutQTVU19XFtblMEnOy6p5AkEAw170rEmMSa0QoHw+d2bVtydR1QnDapqqO6kOx5oYfkm4J4eWYx4J5CQdMpSmuzF9scL85E3sa+NvnV8LEm7cHwJALtXzFPWG4bNt47yTSslzQka/Hl/G5Kginj1mtA44xnr4AihEyKlNpThY95nqj1cgOd7vVtI9W/sv1LH2aFAeIQJBAIqXbMc6xGVfuiFAJKtg+AFNMBP0UOEgMEoKo4RPFp21nBhFgL9/WYM4ZjyHUdr45rCySAqQovw4DCHLfQZC23I=";
    // 支付宝公钥
    // public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSZb6ZyWzn9VE4yL3s3wJk2ms/sKq47qg7hH5nag9Nm3EEraZktt3fS5Zjx8bppUhmQ0oC4SttSAim08APHbXm+dRVDSF8qxljpUdrT/hoMPiJF5XJLC3AOmvsK+gU8GtJxlAhrfvuugB5tkoT/xrOaR/pmOAJ3LxNMHbpEkX8eQIDAQAB";
    public static final String RSA_PUBLIC ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+dY2sUIcVvT51qVvp8+sie+rafXhhV5nxp7sTCvj5sf6mr3bHsFziwXyNjkgkd/U88cDGLwplK567dH1Umu5m5WzaLNhZzDPYo58fG7AAIZvouwSobmhzLpySh+vS/g++d8x9z9WCIdJcuQ5wK91j6h8nad/OKgb2/DUvU2stBwIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    /*
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                           // finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo("您购买的商品", "该测试商品的详细描述", adapter.getTotalPrice()+"");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
