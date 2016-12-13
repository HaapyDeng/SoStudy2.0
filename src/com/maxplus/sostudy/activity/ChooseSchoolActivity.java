package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maxplus.sostudy.R;

public class ChooseSchoolActivity extends Activity implements View.OnClickListener {
    private String province, area, type, school;
    private int provinceid;
    private TextView tv_city, tv_area, tv_type, tv_school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        initView();
    }

    private void initView() {
        tv_city = (TextView) findViewById(R.id.tv_choose_city);
        tv_city.setOnClickListener(this);
        tv_area = (TextView) findViewById(R.id.tv_choose_dq);
        tv_area.setOnClickListener(this);
        tv_type = (TextView) findViewById(R.id.choose_school_type);
        tv_type.setOnClickListener(this);
        tv_school = (TextView) findViewById(R.id.choose_school);
        tv_school.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_city:
                Intent intent = new Intent();
                intent.setClass(ChooseSchoolActivity.this, ChooseProvinceActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.tv_choose_dq:
                break;
            case R.id.choose_school_type:
                break;
            case R.id.choose_school:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == 3) {
                Bundle bundle = data.getExtras();
                province = bundle.getString("province");
                provinceid = bundle.getInt("provinceid");
                Log.d("province==>>>>", province + "" + provinceid);
                tv_city.setText(province);
            }
        }
    }
}
