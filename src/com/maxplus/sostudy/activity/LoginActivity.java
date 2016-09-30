package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginActivity extends Activity implements OnClickListener {
    private String userName, password;
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
                intent.setClass(this, StudentRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forg_password:
                Intent intent2 = new Intent();
                intent2.setClass(this, ForgetPassword.class);
                startActivity(intent2);
                break;
            case R.id.btn_login:
                doLoginPost();

                break;
        }

    }

    public void doLoginPost() {
        final String url = "http://aookie.6655.la/api/login";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", "18008013983");
        params.put("password", "12345678");
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    Toast.makeText(LoginActivity.this, json.getString("userID")+json.getString("userType")+statusCode, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void initView() {
        edt_userName = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        userName = edt_userName.toString();
        password = edt_password.toString();
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
