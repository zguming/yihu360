package cn.net.dingwei.business;

import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import cn.net.tmjy.mtiku.futures.BuildConfig;

/**
 * 登录
 */
public class LoginBusiness {

    private static final String TAG = "LoginBusiness";

    private LoginBusiness(){}



    /**
     * 登录imsdk
     *
     * @param identify 用户id
     * @param userSig 用户签名
     * @param callBack 登录后回调
     */
    public static void loginIm(String identify, String userSig, TIMCallBack callBack){

        if ("teacher".equals(BuildConfig.FLAVOR)) {
            Constant.ACCOUNT_TYPE = 12913;//"腾讯云注册的appid"  1254402451
            Constant.SDK_APPID = 1400031302;
        }else if ("yihu360".equals(BuildConfig.FLAVOR)){
            Constant.ACCOUNT_TYPE = 19002;//"腾讯云注册的appid"  1254402451
            Constant.SDK_APPID = 1400048644;
        }else if ("xizhang".equals(BuildConfig.FLAVOR)){
            Constant.ACCOUNT_TYPE = 19011;//"腾讯云注册的appid"  1254402451
            Constant.SDK_APPID = 1400049353;
        }

        if (identify == null || userSig == null) return;
        TIMUser user = new TIMUser();
        user.setAccountType(String.valueOf(Constant.ACCOUNT_TYPE));
        user.setAppIdAt3rd(String.valueOf(Constant.SDK_APPID));
        user.setIdentifier(identify);
        //发起登录请求
        TIMManager.getInstance().login(
                Constant.SDK_APPID,
                user,
                userSig,                    //用户帐号签名，由私钥加密获得，具体请参考文档
                callBack);
    }

    /**
     * 登出imsdk
     *
     * @param callBack 登出后回调
     */
    public static void logout(TIMCallBack callBack){
        TIMManager.getInstance().logout(callBack);
    }
}
