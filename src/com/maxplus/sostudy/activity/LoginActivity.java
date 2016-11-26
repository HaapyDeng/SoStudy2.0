package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

import cn.jpush.android.JPushConstants;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


public class LoginActivity extends Activity implements OnClickListener {
    public String userName, password, token;
    EditText edt_userName, edt_password;
    TextView tv_forg_password, tv_regist;
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forg_password:
                Intent intent2 = new Intent();
                intent2.setClass(this, ForgetPassword.class);
                startActivity(intent2);
                break;
            case R.id.btn_login:
//                userName = edt_userName.getText().toString();
//                password = edt_password.getText().toString();
//                JMessageClient.login(userName, password, new BasicCallback() {
//                    @Override
//                    public void gotResult(int i, String s) {
//                        if (i == 0) {
//                            Intent intent3 = new Intent();
//                            intent3.setClass(LoginActivity.this, MainActivity.class);
//                            startActivity(intent3);
//                            finish();
//                        } else {
//                            JMessageClient.register(userName, password, new BasicCallback() {
//                                @Override
//                                public void gotResult(int i, String s) {
//                                    if (i == 0) {
//                                        JMessageClient.login(userName, password, new BasicCallback() {
//                                            @Override
//                                            public void gotResult(int i, String s) {
//                                                Intent intent3 = new Intent();
//                                                intent3.setClass(LoginActivity.this, MainActivity.class);
//                                                startActivity(intent3);
//                                                finish();
//
//                                            }
//                                        });
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//                JMessageClient.register(userName, password, new BasicCallback() {
//                    @Override
//                    public void gotResult(int i, String s) {
//                        if (i == 0) {
//                            JMessageClient.login(userName, password, new BasicCallback() {
//                                @Override
//                                public void gotResult(int i, String s) {
//
//                                }
//                            });
//                        }
//                    }
//                });
//                register("12345678", "12345678" );

                doLoginPost();

                break;
        }

    }


    public void setAlias(String alias) {
        JPushInterface.setAlias(LoginActivity.this, alias, new TagAliasCallback() {

            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
                    Log.d("setAlias", "setAlias==>>" + s);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.network_little_hint, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }


    public void doLoginPost() {
        final String url = NetworkUtils.returnUrl() + "/api/login";
        Log.d("url==??>>>>>>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        userName = edt_userName.getText().toString();
        password = edt_password.getText().toString();

        if (userName.length() == 0) {
            Toast.makeText(LoginActivity.this, R.string.userName_not_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(LoginActivity.this, R.string.password_not_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!NetworkUtils.checkNetWork(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("username", userName);
        params.put("password", password);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    Log.d("status=====>", "" + json.getInt("status"));
                    if (json.getInt("status") == 1) {
                        token = json.getString("tock");
                        Log.d("token==>>>>", token);
                        SharedPreferences mySharedPreferences = getSharedPreferences("token",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor edit = mySharedPreferences.edit();
                        edit.putString("token", token);
                        edit.commit();
                        startJmlogin(userName, password);
                    } else if (json.getInt("status") == 0) {
                        Log.d("errorInfo==>>>>>", json.getString("errorInfo"));
                        Toast.makeText(LoginActivity.this, json.getString("errorInfo"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.login_failed_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("errorCode", "" + responseString);
                Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startJmlogin(final String userName, final String password) {

        JMessageClient.login(userName, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    setAlias(userName);
                    Intent intent3 = new Intent();
                    intent3.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent3);
                    finish();
                } else {
                    JMessageClient.register(userName, password, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                setAlias(userName);
                                JMessageClient.login(userName, password, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Intent intent3 = new Intent();
                                        intent3.setClass(LoginActivity.this, MainActivity.class);
                                        startActivity(intent3);
                                        finish();

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void initView() {
        edt_userName = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_forg_password = (TextView) findViewById(R.id.forg_password);
        tv_forg_password.setOnClickListener(this);
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        tv_regist.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
