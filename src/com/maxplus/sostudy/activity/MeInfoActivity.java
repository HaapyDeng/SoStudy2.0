package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class MeInfoActivity extends BaseActivity {

    private ImageButton back_btn;
    private LinearLayout user_rl;
    private TextView tv_school, tv_grade, tv_classse, tv_userName, tv_name, tv_email,
            tv_password, tv_change, tv_namet, email_phone;
    private String school = "", grade = "", classe = "", userName = "", name = "", email = "", password = "";
    private int usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_student_info);
        initViews();
        getInfo();
    }

    //从服务器获取用户信息
    private void getInfo() {
        String url = NetworkUtils.returnUrl() + "/api/my/userinfo";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        final SharedPreferences mySharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        String token = "";
        token = mySharedPreferences.getString("token", "");
        Log.d("token==>>>", token);
        if (token.length() == 0) {
            Toast.makeText(MeInfoActivity.this, R.string.getinfo_fail, Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.setClass(MeInfoActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        param.put("token", token);
        client.get(url, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        int status = jsonObject.getInt("status");
                        Log.d("status===>>>>>", "" + status);
                        if (status == 100) {
                            JSONObject jsonContent = jsonObject.getJSONObject("content");
                            usertype = jsonContent.getInt("usertype");
                            switch (usertype) {
                                case 1:
                                    school = jsonContent.getString("schoolname");
                                    tv_school.setText(school);
                                    grade = jsonContent.getString("gradename");
                                    tv_grade.setText(grade);
                                    classe = jsonContent.getString("classname");
                                    tv_classse.setText(classe);
                                    userName = mySharedPreferences.getString("username", "");
                                    tv_userName.setText(userName);
//                                    user_rl.setVisibility(View.INVISIBLE);
                                    name = jsonContent.getString("realname");
                                    tv_name.setText(name);
                                    email = jsonContent.getString("email");
                                    tv_email.setText(email);
                                    break;
                                case 2:
                                    school = jsonContent.getString("schoolname");
                                    tv_school.setText(school);
                                    grade = jsonContent.getString("gradename");
                                    tv_grade.setText(grade);
                                    classe = jsonContent.getString("coursename");
                                    tv_classse.setText(classe);
                                    tv_change.setText("学科");
                                    userName = mySharedPreferences.getString("username", "");
                                    tv_userName.setText(userName);
//                                    user_rl.setVisibility(View.INVISIBLE);
                                    name = jsonContent.getString("realname");
                                    tv_name.setText(name);
//                                    email = jsonContent.getString("email");
                                    tv_email.setText(userName);
                                    email_phone.setText("手机号");
                                    break;
                                case 3:
                                    JSONObject jsonChildren = jsonContent.getJSONObject("children");
                                    school = jsonChildren.getString("schoolname");
                                    tv_school.setText(school);
                                    grade = jsonChildren.getString("gradename");
                                    tv_grade.setText(grade);
                                    classe = jsonChildren.getString("classname");
                                    tv_classse.setText(classe);
                                    userName = mySharedPreferences.getString("username", "");
                                    tv_userName.setText(userName);
                                    name = jsonChildren.getString("realname");
                                    tv_name.setText(name);
//                                    email = jsonContent.getString("email");
                                    tv_email.setText(userName);
                                    email_phone.setText("手机号");
                                    break;
                            }
                        } else {
                            Toast.makeText(MeInfoActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.d("response==>>>>", "" + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MeInfoActivity.this, R.string.getinfo_fail, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initViews() {
        back_btn = (ImageButton) findViewById(R.id.back_Button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        user_rl = (LinearLayout)findViewById(R.id.user_rl);
//        tv_namet = (TextView) findViewById(R.id.tv_namet);
        tv_change = (TextView) findViewById(R.id.tv_fix);
        tv_school = (TextView) findViewById(R.id.school_tv);
        tv_grade = (TextView) findViewById(R.id.grade_tv);
        tv_classse = (TextView) findViewById(R.id.classe_tv);
        tv_userName = (TextView) findViewById(R.id.user_tv);
        tv_name = (TextView) findViewById(R.id.realname_tv);
        tv_email = (TextView) findViewById(R.id.email_tv);
        tv_password = (TextView) findViewById(R.id.password);
        email_phone = (TextView) findViewById(R.id.email_phone);

    }

}
