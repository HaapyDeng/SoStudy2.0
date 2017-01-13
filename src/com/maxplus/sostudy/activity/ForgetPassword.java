package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends Activity implements OnClickListener {
    private String newPassword, verifyCode;
    private String phoneNum, getRaelCode;
    EditText et_phoneNum, et_newPassword, et_verifyCode;
    TextView tv_getCode;
    Button btn_commit;
    ImageButton back_btn;
    CheckBox showPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {

        tv_getCode = (TextView) findViewById(R.id.tv_getCode);
        tv_getCode.setOnClickListener(this);

        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        back_btn = (ImageButton) findViewById(R.id.back_Button);
        back_btn.setOnClickListener(this);
        showPassword = (CheckBox) findViewById(R.id.sshow_password);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        //点击密码可见或隐藏设置
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (showPassword.isChecked()) {
                    et_newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.visible);
                } else {
                    et_newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.unvisible);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_Button:
                finish();
                break;
            case R.id.tv_getCode:
                et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
                phoneNum = et_phoneNum.getText().toString().trim();
                Log.d("Num===>>>>>", phoneNum);
                Log.d("isMobileNO==>>>", String.valueOf(NetworkUtils.isMobileNO(phoneNum)));
                Log.d("isEmail==>>>", String.valueOf(NetworkUtils.isEmail(phoneNum)));
                if (NetworkUtils.isMobileNO(phoneNum) || NetworkUtils.isEmail(phoneNum)) {

                    if (!NetworkUtils.checkNetWork(this)) {
                        Toast.makeText(this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String urlM = NetworkUtils.returnUrl();
                    String url = "";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    if (NetworkUtils.isMobileNO(phoneNum)) {
                        url = urlM + NetworkUtils.returnPhoneCodeApi();
                        Log.d("url=====>>>", "" + url);
                        params.put("phone", phoneNum);
                    } else {
                        url = urlM + NetworkUtils.returnEmailCodeApi();
                        Log.d("url=====>>>", "" + url);
                        params.put("email", phoneNum);
                    }
                    params.put("forget", 1);
                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            int status = 111;

                            try {
                                status = response.getInt("status");
                                Log.d("status==>>>>>", "" + status);
                                Log.d("response===>>>>>>", "" + response);
                                if (status == 1) {
                                    getRaelCode = response.getString("code");
                                    Log.d("code===>>>>>>", "" + getRaelCode);
                                    timer.start();
                                } else if (status == 0) {
                                    Toast.makeText(ForgetPassword.this, response.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgetPassword.this, R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Toast.makeText(ForgetPassword.this, R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ForgetPassword.this, R.string.input_right_phone_email_number, Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.btn_commit:
                et_verifyCode = (EditText) findViewById(R.id.et_verifyCode);
                verifyCode = et_verifyCode.getText().toString();
                et_newPassword = (EditText) findViewById(R.id.et_newPassword);
                newPassword = et_newPassword.getText().toString();
                if (verifyCode.length() == 0) {
                    Toast.makeText(ForgetPassword.this, R.string.input_verifyCode, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (newPassword.length() < 8) {
                    Toast.makeText(ForgetPassword.this, R.string.input_new_password, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!verifyCode.equals(getRaelCode)) {
                    Toast.makeText(ForgetPassword.this, R.string.input_error_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!NetworkUtils.checkNetWork(this)) {
                    Toast.makeText(this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                    break;
                }
                String urlM = NetworkUtils.returnUrl();
                String url = urlM + NetworkUtils.returnForgPasswordApi();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("newPassword", newPassword);
                if (NetworkUtils.isMobileNO(phoneNum)) {
                    params.put("username", phoneNum);
                } else {
                    params.put("email", phoneNum);
                }
                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        int status = 111;

                        try {
                            status = response.getInt("status");
                            Log.d("status==>>>>>", "" + status);
                            Log.d("response===>>>>>>", "" + response);
                            if (status == 1) {
                                Toast.makeText(ForgetPassword.this, R.string.fix_password_success, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(ForgetPassword.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (status == 0) {
                                Toast.makeText(ForgetPassword.this, response.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgetPassword.this, R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("statusCode==>>>>", "" + statusCode + "," + headers + "," + throwable + "," + errorResponse.toString());
                        try {
                            Toast.makeText(ForgetPassword.this, errorResponse.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
                break;
        }


    }

    /**
     * 倒计时功能
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if ((millisUntilFinished / 1000) < 60) {
                tv_getCode.setEnabled(false);
            }
            tv_getCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tv_getCode.setEnabled(true);
            tv_getCode.setText("获取验证码");
        }
    };
}
