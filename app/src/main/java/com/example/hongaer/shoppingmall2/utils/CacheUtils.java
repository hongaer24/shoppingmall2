package com.example.hongaer.shoppingmall2.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 得到保存的string类型数据
 */

public class CacheUtils {

    public static final String HONG = "hong";

    public static String getString(Context mContext, String key) {
        SharedPreferences sp=mContext.getSharedPreferences("HONG", Context.MODE_PRIVATE);

        return sp.getString(key,"");
}
    //保存string类型的数据value
    public static void saveString(Context context, String key,String value) {
        SharedPreferences sp=context.getSharedPreferences("HONG",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

}
