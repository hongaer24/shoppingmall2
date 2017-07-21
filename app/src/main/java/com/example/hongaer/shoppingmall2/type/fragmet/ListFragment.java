package com.example.hongaer.shoppingmall2.type.fragmet;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.type.adapter.TypeLeftAdapter;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

import static android.content.ContentValues.TAG;
import static com.example.hongaer.shoppingmall2.R.id.lv_left;

/**
 * Created by hongaer on 2017/7/15.
 */

public class ListFragment extends BaseFragment {
    @BindView(lv_left)
    ListView lvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;
    Unbinder unbinder;
    private TextView textView;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};
    private TypeLeftAdapter leftAdapter;
    private String[] urls = new String[]{Constans.SKIRT_URL,Constans.JACKET_URL,Constans.PANTS_URL,Constans.OVERCOAT_URL,
           Constans.ACCESSORY_URL,Constans.BAG_URL,Constans.DRESS_UP_URL,Constans.HOME_PRODUCTS_URL,Constans.STATIONERY_URL,
           Constans.DIGIT_URL,Constans.GAME_URL};
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        leftAdapter=new TypeLeftAdapter(mContext,titles);
        lvLeft.setAdapter(leftAdapter);
        initListener();
    }
    private void initListener() {
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.设置点击的位置
                leftAdapter.changeSelected(position);
                //2.在TypeLeftAdapter getView根据位置中高亮显示代码
                //3.刷新适配器
                leftAdapter.notifyDataSetChanged();
                getDataFromNet(urls[position]);
            }
        });
          getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        Log.e("TAG", "url======" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功==" + response);
                        if (response != null) {
                            //解析数据
                           // processData(response);
                        }
                    }

                });

    }


}
