package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.squareup.picasso.OkHttpDownloader;

import org.apache.http.Header;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends Activity {
    boolean isFirstIn = false;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;

    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    private String userName, password;
    private String service = "moodle_mobile_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }
    }

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goLogin();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }


    private void goLogin() {
        SharedPreferences sp = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        userName = sp.getString("USER_NAME", "");
        password = sp.getString("PASSWORD", "");
        if (!NetworkUtils.checkNetWork(SplashActivity.this)) {
            Toast.makeText(SplashActivity.this, R.string.isNotNetWork,
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
        if (sp.getBoolean("ISCHECK", false) && (userName != "")
                && (password != "")) {
            // 记住用户名和密码自动登录
            doLoginPost(userName,password);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

    }

    private void doLoginPost(String uerName,String password) {
        final String url = "http://aookie.6655.la/api/login";
        AsyncHttpClient client =new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",uerName);
        params.put("password",password);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Toast.makeText(SplashActivity.this,"成功+i="+i,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SplashActivity.this,"失败",Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
            }
        });
    }
}
