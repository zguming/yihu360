package cn.net.dingwei.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/29.
 */

public class LogUtil {
    private static String TAG = "123";
    public static void e(String msg) {
        Log.e(TAG,msg);
    }

    public static void d(String msg) {
        Log.d(TAG,msg);
    }

    public static void i(String msg) {
        Log.i(TAG,msg);
    }

    public static void v(String msg) {
        Log.v(TAG,msg);
    }

    public static void w(String msg) {
        Log.w(TAG,msg);
    }
}
