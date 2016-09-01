package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.maxplus.sostudy.R;

public class ForgetPassword extends Activity implements OnClickListener {
    private String newPassword, verifyCode;
    private String phoneNum;
    EditText et_phoneNum, et_newPassword, et_verifyCode;
    TextView tv_getCode;
    Button btn_commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
        phoneNum = et_phoneNum.toString().trim();
        tv_getCode = (TextView) findViewById(R.id.tv_getCode);
        tv_getCode.setOnClickListener(this);
        et_verifyCode = (EditText) findViewById(R.id.et_verifyCode);
        verifyCode = et_verifyCode.toString();
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        newPassword = et_newPassword.toString();
        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getCode:
                break;
            case R.id.btn_commit:
                break;
        }

    }
}
