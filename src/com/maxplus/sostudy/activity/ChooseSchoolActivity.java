package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;

public class ChooseSchoolActivity extends Activity implements View.OnClickListener {
    private String province = "", city = "", quxian = "", type = "", school = "";
    private int provinceid, cityid, quxianid, schoolid;
    private TextView tv_provnice, tv_city, tv_quxian, tv_type, tv_school;
    private Button btn_confirm;
    private int typeid;
    private ImageButton btn_back;

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
        btn_confirm = (Button) findViewById(R.id.choose_school_confirm);
        btn_confirm.setOnClickListener(this);
        btn_back = (ImageButton) findViewById(R.id.bback_Button);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_pcity:
                if (city.length() != 0 || quxian.length() != 0 || school.length() != 0) {
                    tv_city.setText("");
                    tv_quxian.setText("");
                    tv_school.setText("");
                    city = "";
                    quxian = "";
                    school = "";
                }
                Intent intent = new Intent();
                intent.setClass(ChooseSchoolActivity.this, ChooseProvinceActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.tv_choose_city:
                if (province.length() == 0) {
                    Toast.makeText(this, R.string.choose_province_f, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (quxian.length() != 0 || school.length() != 0) {
                    quxian = "";
                    tv_quxian.setText("");
                    school = "";
                    tv_school.setText("");
                }
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("provinceid", provinceid);
                intent1.putExtras(bundle);
                intent1.setClass(ChooseSchoolActivity.this, ChooseSchoolCityActivity.class);
                startActivityForResult(intent1, 4);
                break;
            case R.id.tv_choose_qx:
                if (province.length() == 0) {
                    Toast.makeText(this, R.string.choose_province_f, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (city.length() == 0) {
                    Toast.makeText(this, R.string.choose_city_f, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (school.length() != 0) {
                    tv_school.setText("");
                }
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("cityid", cityid);
                intent2.putExtras(bundle2);
                intent2.setClass(ChooseSchoolActivity.this, ChooseSchoolQuXianActivity.class);
                startActivityForResult(intent2, 5);
                break;
            case R.id.choose_school_type:
                if (school.length() != 0) {
                    school = "";
                    tv_school.setText("");
                }
                Intent intent3 = new Intent();
                intent3.setClass(ChooseSchoolActivity.this, ChooseSchoolTypeActivity.class);
                startActivityForResult(intent3, 6);
                break;
            case R.id.choose_school:
                if (province.length() == 0) {
                    Toast.makeText(this, R.string.choose_province_f, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (city.length() == 0) {
                    Toast.makeText(this, R.string.choose_city_f, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (quxian.length() == 0) {
                    Toast.makeText(this, R.string.choose_quxian_f, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (type.length() == 0) {
                    Toast.makeText(this, R.string.choose_type_f, Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent4 = new Intent();
                Bundle bundle4 = new Bundle();
                bundle4.putInt("schooltype", typeid);
                String quxianid1 = "" + quxianid;
                Log.d("quxianid==>>", quxianid1);
                bundle4.putString("districtid", quxianid1);
                intent4.putExtras(bundle4);
                intent4.setClass(ChooseSchoolActivity.this, ChooseSchoolNameActivity.class);
                startActivityForResult(intent4, 7);
                break;
            case R.id.choose_school_confirm:
                if (school.length() == 0) {
                    Toast.makeText(this, R.string.choose_school_f, Toast.LENGTH_SHORT).show();
                    break;
                }

                Intent intent5 = new Intent();
                Bundle bundle5 = new Bundle();
                bundle5.putString("schoolname", "" + school);//添加要返回给页面1的数据
                bundle5.putInt("schoolid", schoolid);
                intent5.putExtras(bundle5);
                setResult(2, intent5);//返回页面1
                finish();
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
        if (requestCode == 4) {
            if (resultCode == 4) {
                Bundle bundle1 = data.getExtras();
                city = bundle1.getString("city");
                cityid = bundle1.getInt("cityid");
                Log.d("city==>>>>>", city + "" + cityid);
                tv_city.setText(city);
            }
        }
        if (requestCode == 5) {
            if (resultCode == 5) {
                Bundle bundle2 = data.getExtras();
                quxian = bundle2.getString("quxian");
                Log.d("quxian===>>>", quxian + "+" + bundle2.getInt("quxianid"));
                quxianid = bundle2.getInt("quxianid");
                tv_quxian.setText(quxian);
            }
        }
        if (requestCode == 6) {
            if (resultCode == 6) {
                Bundle bundle2 = data.getExtras();
                type = bundle2.getString("type");
                Log.d("quxian===>>>", type + "+" + bundle2.getInt("typeid"));
                typeid = bundle2.getInt("typeid");
                tv_type.setText(type);
            }
        }
        if (requestCode == 7) {
            if (resultCode == 7) {
                Bundle bundle3 = data.getExtras();
                school = bundle3.getString("schoolName");
                schoolid = bundle3.getInt("schoolId");
                Log.d("school==>>>>", school + "+" + schoolid);
                tv_school.setText(school);
            }
        }
    }
}
