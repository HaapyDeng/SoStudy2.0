package com.maxplus.sostudy.activity;

import android.app.Activity;

import com.maxplus.sostudy.tools.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;

public class StudentRegisterActivity extends Activity implements View.OnClickListener {

    private TextView choose_school, choose_grade, choose_class, getCode;
    EditText et_userName, et_realName, et_email, et_emailCode, et_password;
    String userName, realName, email, emailCode, password;
    Button commit;
    ImageView showPassword;
    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_student);
        initView();
    }

    private void initView() {
        choose_school = (TextView) findViewById(R.id.tv_schoose_school);
        choose_school.setOnClickListener(this);
        choose_grade = (TextView) findViewById(R.id.tv_schoose_grade);
        choose_grade.setOnClickListener(this);
        choose_class = (TextView) findViewById(R.id.tv_schoose_class);
        choose_class.setOnClickListener(this);
        et_userName = (EditText) findViewById(R.id.et_sinput_user);
        userName = et_userName.getText().toString().trim();
        et_realName = (EditText) findViewById(R.id.et_sinput_name);
        realName = et_realName.getText().toString().trim();
        et_email = (EditText) findViewById(R.id.et_sinput_email);
        email = et_email.getText().toString().trim();
        et_emailCode = (EditText) findViewById(R.id.et_sinput_email_code);
        emailCode = et_emailCode.getText().toString().trim();
        et_password = (EditText) findViewById(R.id.et_sinput_new_password);
        password = et_password.getText().toString().trim();
        getCode = (TextView) findViewById(R.id.tv_sgetCode);
        getCode.setOnClickListener(this);
        commit = (Button) findViewById(R.id.btn_scommit);
        commit.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_schoose_school:
                break;
            case R.id.tv_schoose_grade:
                final AlertDialog dialog = new AlertDialog(StudentRegisterActivity.this);

                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final RadioButton radioButton;
                        radioButton = (RadioButton) dialog.retunGrade();
                        if (radioButton == null) {
                            dialog.dismiss();
                        } else {
                            grade = radioButton.getText().toString();
                            choose_grade.setText(grade);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setOnNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
            case R.id.tv_schoose_class:
                break;
            case R.id.tv_sgetCode:
                break;
            case R.id.btn_scommit:
                break;

        }

    }

}
