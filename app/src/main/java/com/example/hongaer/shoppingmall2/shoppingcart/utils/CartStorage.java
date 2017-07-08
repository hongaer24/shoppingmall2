package com.example.hongaer.shoppingmall2.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongaer on 2017/7/6.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
  private static CartStorage instance;
    private final   Context context;
  public SparseArray<GoodsBean> sparseArray;

    public CartStorage(Context context){
              this.context=context;
           //把之前的数据读取出来放到sparseArray内存
                sparseArray=new SparseArray<>(100);

            listToSparseArray();
    }
    public static CartStorage getInstance() {
        if (instance ==null) {
            instance = new  CartStorage(MyApplication.getContex());
        }
        return  instance;
    }
     //从本地中读取数据加入SparseArray
    private void listToSparseArray() {

        List<GoodsBean> goodsBeanList=getAllData();
        //把list数据转换成sparseArray
        if(goodsBeanList!=null) {
            for (int i = 0; i < goodsBeanList.size(); i++) {

                 GoodsBean goodsBean = goodsBeanList.get(i);
                Log.i("6666","空指针的地方=="+goodsBeanList.get(i));
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }
    //获取本地所有的数据
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList=new ArrayList<>();
         //1.从本地从获取
         String json= CacheUtils.getString(context,JSON_CART);
        Log.i("777","空指针的地方=="+json);
         //用gson转换为列表
         //判断不为空时执行
        if (!TextUtils.isEmpty(json)) {
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {}.getType());
            Log.i("666","空指针的地方=="+goodsBeanList);
        }
          return goodsBeanList;

    }

    public void addData(GoodsBean goodsBean) {
        //1.添加到内存中 SparseArray
        Log.i("110110","空指针的地方=="+goodsBean);
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null) {
           // 内存中有了这条数据
            tempData.setNumber(tempData.getNumber() + 1);
        } else {
            tempData = goodsBean;
            tempData.setNumber(1);

        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()), tempData);

        //2.同步到本地
            saveLocal();
        }
    //删除数据
    public void deleteData(GoodsBean  goodsBean) {
        //1.从内存中删除
      sparseArray.delete(Integer.parseInt( goodsBean.getProduct_id()));
        //2.把内存数据保存到本地
        saveLocal();
    }
    //修改数据
    public void updataData(GoodsBean goodsBean) {

      sparseArray.put(Integer.parseInt( goodsBean.getProduct_id()), goodsBean);
       saveLocal();
    }
    private void saveLocal() {
        //把 parseArray 转换成 list
        List<GoodsBean> goodsBeanList = sparsesToList();
         //把转换成 String
        String json = new Gson().toJson(goodsBeanList);
        Log.i("999","空指针的地方=="+json);
          // 保存
        CacheUtils.saveString(context, JSON_CART, json);

    }

    private List<GoodsBean> sparsesToList() {
              List<GoodsBean> goodsBeanList=new ArrayList<>();
             if(sparseArray!=null&&sparseArray.size()>0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsBean goodsBean = sparseArray.valueAt(i);
                goodsBeanList.add(goodsBean);
            }
        }
            return goodsBeanList;
    }


}
