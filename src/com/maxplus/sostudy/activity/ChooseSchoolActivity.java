package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maxplus.sostudy.R;

public class ChooseSchoolActivity extends Activity implements View.OnClickListener {
    private String province, city, quxian, type, school;
    private int provinceid,cityid;
    private TextView tv_provnice, tv_city, tv_quxian, tv_type, tv_school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        initView();
    }

    private void initView() {
        tv_provnice = (TextView) findViewById(R.id.tv_choose_pcity);
        tv_provnice.setOnClickListener(this);
        tv_city = (TextView) findViewById(R.id.tv_choose_city);
        tv_city.setOnClickListener(this);
        tv_quxian = (TextView) findViewById(R.id.tv_choose_qx);
        tv_quxian.setOnClickListener(this);
        tv_type = (TextView) findViewById(R.id.choose_school_type);
        tv_type.setOnClickListener(this);
        tv_school = (TextView) findViewById(R.id.choose_school);
        tv_school.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_pcity:
                Intent intent = new Intent();
                intent.setClass(ChooseSchoolActivity.this, ChooseProvinceActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.tv_choose_city:
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("provinceid", provinceid);
                intent1.putExtras(bundle);
                intent1.setClass(ChooseSchoolActivity.this, ChooseSchoolCityActivity.class);
                startActivityForResult(intent1, 4);
                break;
            case R.id.tv_choose_qx:
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("cityid",cityid);
                intent2.putExtras(bundle2);
                intent2.setClass(ChooseSchoolActivity.this,ChooseSchoolQuXianActivity.class);
                startActivityForResult(intent2,5);
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
                tv_provnice.setText(province);
            }
        }
        if (requestCode==4){
            if (resultCode==4){
                Bundle bundle1 = data.getExtras();
                city = bundle1.getString("city");
                cityid = bundle1.getInt("cityid");
                Log.d("city==>>>>>",city+""+cityid);
                tv_city.setText(city);
            }
        }
        if (requestCode==5){
            if (resultCode==5){
                Bundle bundle2 = data.getExtras();
                quxian = bundle2.getString("quxian");
                Log.d("quxian===>>>",quxian+"+"+bundle2.getInt("quxianId"));
                tv_quxian.setText(quxian);
            }
        }
    }
}
