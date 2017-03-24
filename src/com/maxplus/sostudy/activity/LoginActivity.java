package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.chatting.utils.DialogCreator;
import com.maxplus.sostudy.tools.ClearEditText;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


public class LoginActivity extends Activity implements OnClickListener {
    public String userName, password, token;
    private ClearEditText edt_userName, edt_password;
    private TextView tv_forg_password, tv_regist, tv_error;
    private Button btn_login;
    private Dialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
        userName = sp.getString("username", "");
        password = sp.getString("password", "");
        if ((userName.length() != 0) && (password.length() != 0)) {
            getShared(userName, password);
            btn_login = (Button) findViewById(R.id.btn_login);
            btn_login.setBackgroundResource(R.drawable.btn_y_shape);
        }
        initView();
    }

    private void getShared(String username, String password) {
        edt_userName = (ClearEditText) findViewById(R.id.edt_username);
        edt_userName.setText(username);
        edt_password = (ClearEditText) findViewById(R.id.edt_password);
        edt_password.setText(password);
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
                intent2.setClass(this, ForgetPasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_login:

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
        final String url = NetworkUtils.returnUrl() + NetworkUtils.returnLoginApi();
        Log.d("url==??>>>>>>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
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
        btn_login.setBackgroundResource(R.drawable.btn_y_shape);
        if (!NetworkUtils.checkNetWork(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("username", userName);
        params.put("password", password);
        mLoadingDialog = DialogCreator.createLoadingDialog(LoginActivity.this,
                LoginActivity.this.getString(R.string.logining));
        mLoadingDialog.show();
        Log.d("LoginParams==>>>", url + "," + userName + "" + password);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    Log.d("status=====>", json.toString());
                    if (json.getInt("status") == 1) {
                        token = json.getString("tock");
                        Log.d("token==>>>>", token);
                        SharedPreferences mySharedPreferences = getSharedPreferences("user",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor edit = mySharedPreferences.edit();
                        edit.putString("token", token);
                        edit.putString("username", userName);
                        edit.putString("password", password);
                        edit.commit();
                        startJmlogin(userName, password);
                    } else if (json.getInt("status") == 0) {
                        Log.d("errorInfo==>>>>>", json.getString("errorInfo"));
                        mLoadingDialog.dismiss();
                        tv_error = (TextView) findViewById(R.id.tv_error);
                        tv_error.setText(json.getString("errorInfo"));
//                        Toast.makeText(LoginActivity.this, json.getString("errorInfo"), Toast.LENGTH_LONG).show();
                    } else {
                        mLoadingDialog.dismiss();
//                        Toast.makeText(LoginActivity.this, R.string.login_failed_toast, Toast.LENGTH_LONG).show();
                        tv_error.setText(R.string.login_failed_toast);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("errorCode", "" + responseString);
                mLoadingDialog.dismiss();
                Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startJmlogin(final String userName, final String password) {

        JMessageClient.login(userName, password, new BasicCallback() {

            @Override
            public void gotResult(int i, String s) {
                Log.d("JMessageClient is ", i + "+" + s);
                if (i == 0) {
                    setAlias(userName);
                    Intent intent3 = new Intent();
                    intent3.setClass(LoginActivity.this, MainActivity.class);
                    mLoadingDialog.dismiss();
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
                                        mLoadingDialog.dismiss();
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
        edt_userName = (ClearEditText) findViewById(R.id.edt_username);
        edt_userName.addTextChangedListener(new textListener());
        edt_password = (ClearEditText) findViewById(R.id.edt_password);
        edt_password.addTextChangedListener(new textListener());
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


    private class textListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if ((edt_userName.getText().toString().trim().length() != 0) && (edt_password.getText().toString().trim().length() != 0)) {
                btn_login.setBackgroundResource(R.drawable.btn_y_shape);
            } else {
                btn_login.setBackgroundResource(R.drawable.btn_n_shape);
            }
        }
    }
}
