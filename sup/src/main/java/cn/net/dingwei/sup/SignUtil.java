package cn.net.dingwei.sup;

import android.content.Context;

/**
 * Created by cd on 2017/5/4.
 */

public class SignUtil {
    public static void init(Context context) {

        //命题库--cn.net.tmjy.bailiangang.futures
        //医护360--cn.net.tmjy.mtiku.yihu360
        //西藏--cn.net.tmjy.mtiku.xizhang
        if("cn.net.tmjy.mtiku.yihu360".equals(context.getPackageName())) {
            Reading_QuestionsSup.flag = true;
        }else {
            Reading_QuestionsSup.flag = false;
        }
    }
}
