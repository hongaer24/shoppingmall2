package com.example.hongaer.shoppingmall2.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongaer.shoppingmall2.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.ib_weibo)
    ImageButton ibWeibo;
    @BindView(R.id.ib_qq)
    ImageButton ibQq;
    @BindView(R.id.ib_wechat)
    ImageButton ibWechat;
    private static final String TAG = "LoginActivity";
    private boolean isShowPassword;
    public static final String QQ_APP_ID = "1106221777";
    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private boolean isLogIned = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //首先需要用APP ID 获取到一个Tencent实例
        mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
        //初始化一个IUiListener对象，在IUiListener接口的回调方法中获取到有关授权的某些信息
        // （千万别忘了覆写onActivityResult方法，否则接收不到回调）
        mIUiListener = new BaseUiListener();
        //初始化各控件

    }
    private class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.ib_login_back, R.id.ib_login_visible, R.id.btn_login, R.id.tv_login_register, R.id.tv_login_forget_pwd, R.id.ib_weibo, R.id.ib_qq, R.id.ib_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                  finish();
                break;
            case R.id.ib_login_visible:
                isShowPassword = !isShowPassword;
                if(isShowPassword){
                    //显示
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                    etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //光标定位到末尾
                    etLoginPwd.setSelection(etLoginPwd.length());

                }else{
                    //隐藏
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //光标定位到末尾
                    etLoginPwd.setSelection(etLoginPwd.length());
                }

                break;
            case R.id.btn_login:
                Toast.makeText(this,"登录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_register:
                Toast.makeText(this,"注册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_forget_pwd:
                Toast.makeText(this,"忘记密码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_weibo:
                Toast.makeText(this,"微博",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_qq:
                if (!isLogIned) {
                    isLogIned = true;
                    //调用QQ登录，用IUiListener对象作参数
                    if (!mTencent.isSessionValid()) {
                        mTencent.login(LoginActivity.this, "all",mIUiListener);
                    }
                } else {
                    //登出
                    mTencent.logout(LoginActivity.this);
                    isLogIned = false;
                    Toast.makeText(LoginActivity.this, "登录已注销！", Toast.LENGTH_SHORT).show();
                }
               // mTencent.login(LoginActivity.this,"all", mIUiListener);
                break;
            case R.id.ib_wechat:
                Toast.makeText(this,"微信",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
