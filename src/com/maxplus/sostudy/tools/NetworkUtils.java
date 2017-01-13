package com.maxplus.sostudy.tools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {
    /**
     * 检测当的网络状态
     *
     * @param context
     *            Context
     * @return true 表示网络可用
     */
    /**
     * 网络不可用
     */
    public static final int NONETWORK = 0;
    /**
     * 是wifi连接
     */
    public static final int WIFI = 1;
    /**
     * 不是wifi连接
     */
    public static final int NOWIFI = 2;

    /**
     * 检验网络连接 并判断是否是wifi连接
     *
     * @param context
     * @return <li>没有网络：Network.NONETWORK;</li> <li>wifi 连接：Network.WIFI;</li>
     * <li>mobile 连接：Network.NOWIFI</li>
     */
    public static int checkNetWorkType(Context context) {

        if (!checkNetWork(context)) {
            return NetworkUtils.NONETWORK;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting())
            return NetworkUtils.WIFI;
        else
            return NetworkUtils.NOWIFI;
    }

    /**
     * 检测网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        // 1.获得连接设备管理器
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isAvailable()) {
            return false;
        }
        return true;
    }


    //判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "\\w+@(\\w+.)+[a-z]{2,3}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //判断是否全是数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //服务器端主路径
    public static String returnUrl() {
        String url = "http://101.201.197.73/api";
        return url;
    }

    //登录接口
    public static String returnLoginApi() {
        return "/api/login";
    }

    //忘记密码接口
    public static String returnForgPasswordApi() {
        return "/api/forget";
    }

    //注册接口
    public static String returnRegistApi() {
        return "/api/register";
    }

    //手机验证码接口
    public static String returnPhoneCodeApi() {
        return "/api/sms";
    }

    //邮箱验证码接口
    public static String returnEmailCodeApi() {
        return "/api/mail";
    }

    //省市区对应的学校接口
    public static String returnSchoolApi() {
        return "/common/getschool";
    }

    //验证孩子姓名接口
    public static String returnChildUidApi() {
        return "/api/check-child";
    }

    //验证孩子姓名接口
    public static String returnUserInfoApi() {
        return "/my/userinfo";
    }

    //上传头像接口
    public static String returnUploadActorApi() {
        return "/api/my/uploadimage";
    }

    //获取省份列表
    public static String returnGetProvinceApi() {
        return "/common/getprovince";
    }

    //获取省市下面城市列表
    public static String returnGetCityApi() {
        return "/common/getcity";
    }

    //获取区县列表
    public static String returnGetDistrict() {
        return "/common/getdistrict";
    }

    //获取学校名称
    public static String returnGetSchoolname() {
        return "/common/getschool";
    }
}
