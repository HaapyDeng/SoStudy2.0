package com.maxplus.sostudy.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.CityPicker;
import com.maxplus.sostudy.tools.CityPicker.OnSelectingListener;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.PickerScrollView;
import com.maxplus.sostudy.tools.Pickers;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseSchoolActivity extends Activity implements View.OnClickListener {
    private String city, school, schoolType, schoolId;
    private TextView choose_city, choose_school, choose_school_type;
    private CityPicker cityPicker;
    private OnSelectingListener onSelectingListener;
    private int cityid, schoolTypeId = 10, schoolTypeid;
    private String cityIdString = "0";
    private Button confirm, bt_scrollchoose; // 滚动选择器按钮
    private PickerScrollView pckerscrlllview; // 滚动选择器
    private List<Pickers> list, typeList; // 滚动选择器数据
    private String[] id, a, typeId;
    private String[] name = new String[0], typeName;
    private ImageButton bback_Button;
    private Button bt_yes; // 确定按钮
    private RelativeLayout picker_rel; // 选择器布局
    private RadioButton xiaoxue, chuzhong, gaozhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        initView();

        cityPicker.setOnSelectingListener(new OnSelectingListener() {
            @Override
            public void selected(boolean selected) {
                Log.d("selected====>>>>", "" + selected);
                if (selected == true) {
                    city = cityPicker.getCity_string();
                    Log.i("city===>>>", "" + city);
                    cityid = cityPicker.getCity_id();
                    Log.i("cityidshi===>>>", "" + cityid);
                    cityIdString = cityPicker.returnCityString();
                    Log.i("cityIdString===>>>", "" + cityIdString);
                    choose_city.setText(city);

                } else {
                    choose_city.setText(city);
                }
            }
        });

    }

    private void initView() {
        bback_Button = (ImageButton) findViewById(R.id.bback_Button);
        bback_Button.setOnClickListener(this);

        choose_city = (TextView) findViewById(R.id.tv_choose_city);
        choose_city.setOnClickListener(this);

        cityPicker = (CityPicker) findViewById(R.id.citypicker);

        choose_school = (TextView) findViewById(R.id.choose_school);
        choose_school.setOnClickListener(this);

        choose_school_type = (TextView) findViewById(R.id.choose_school_type);
        choose_school_type.setOnClickListener(this);

        picker_rel = (RelativeLayout) findViewById(R.id.picker_rel);

        pckerscrlllview = (PickerScrollView) findViewById(R.id.pickerscrlllview);
        pckerscrlllview.setOnSelectListener(pickerListener);

        bt_yes = (Button) findViewById(R.id.picker_yes);
        bt_yes.setOnClickListener(this);

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bback_Button:
                finish();
                break;
            case R.id.tv_choose_city:
                cityPicker.setVisibility(View.VISIBLE);
                break;
            case R.id.choose_school_type:
                if (cityIdString.length() <= 1) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.choose_ssq, Toast.LENGTH_SHORT).show();
                    return;
                }
                name = null;
                school = "";
                choose_school.setText(R.string.choose_school);
                cityPicker.setVisibility(View.INVISIBLE);
                LayoutInflater inflater2 = LayoutInflater.from(ChooseSchoolActivity.this);
                RelativeLayout layout2 = (RelativeLayout) inflater2.
                        inflate(R.layout.dialog_school_type, null);
                final Dialog dialog2 = new AlertDialog.Builder(ChooseSchoolActivity.this).create();
                dialog2.show();
                dialog2.getWindow().setContentView(layout2);
                xiaoxue = (RadioButton) layout2.findViewById(R.id.xiaoxue);
                xiaoxue.setOnClickListener(new ChooseTypeListner());
                chuzhong = (RadioButton) layout2.findViewById(R.id.chuzhong);
                chuzhong.setOnClickListener(new ChooseTypeListner());
                gaozhong = (RadioButton) layout2.findViewById(R.id.gaozhong);
                gaozhong.setOnClickListener(new ChooseTypeListner());
                TextView cancel_sub = (TextView) layout2.findViewById(R.id.sub_negativeButton);
                cancel_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });
                TextView ok_sub = (TextView) layout2.findViewById(R.id.sub_positiveButton);
                ok_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (schoolType != "") {
                            choose_school_type.setText(schoolType);
                            schoolTypeId = schoolTypeid;
                            dialog2.dismiss();
                        } else {
                            dialog2.dismiss();
                        }
                    }
                });
                break;

            case R.id.choose_school:
                if (cityIdString.length() <= 1) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.f_choose_ssq, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (schoolTypeId == 10) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.f_choose_ssq, Toast.LENGTH_SHORT).show();
                    break;
                }
                Log.d("cityIdString===>>>", cityIdString);
                Log.d("schoolTypeId==>>>", "" + schoolTypeId);
                cityPicker.setVisibility(View.INVISIBLE);
//                获取对应省市区下面的学校数据
                initData(cityIdString, schoolTypeId);
                break;
            case R.id.picker_yes:
                picker_rel.setVisibility(View.GONE);
                choose_school.setText(school);
                break;
            case R.id.confirm:
                if (city == null) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.choose_sssq, Toast.LENGTH_SHORT).show();
                } else if (school.equals("")) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.choose_sschool, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = this.getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("school", "" + city + school);//添加要返回给页面1的数据
                    bundle.putString("schoolname", school);
                    intent.putExtras(bundle);
                    this.setResult(2, intent);//返回页面1
                    this.finish();
                    break;
                }
        }
    }


    /**
     * 初始化数据
     * 从服务器端获取相应城市的学校列表
     */
    private void initData(String cityid, final int typeid) {
        String url = NetworkUtils.returnUrl() + "/common/getschool";
        if (!NetworkUtils.checkNetWork(ChooseSchoolActivity.this)) {
            Toast.makeText(ChooseSchoolActivity.this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("districtid", cityid);
        params.put("schooltype", typeid);
        client.get(url, params, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response==>>>", "" + response.toString());
                if (response.length() == 0) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.school_null, Toast.LENGTH_SHORT).show();
                    school = "";
                    return;
                }
                picker_rel.setVisibility(View.VISIBLE);
                list = new ArrayList<Pickers>();
                String schoolname;
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    JSONObject jsonObject;
                    name = new String[response.length()];
                    id = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        schoolname = jsonObject.getString("school");
                        id[i] = jsonObject.getString("schoolid");
                        name[i] = schoolname;
                        Log.d("schoolname++++===>>", schoolname);
//                        name.add(schoolname);
                    }
                    Log.d("id===>>>>", id.toString());
                    Log.d("name00000===>>>>", name.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                id = new String[]{"1", "2", "3", "4", "5", "6"};
//                name = new String[]{"四川大学", "成都大学", "湖北大学", "天津大学", "北大", "清华"};
                for (int i = 0; i < name.length; i++) {
                    list.add(new Pickers(name[i], id[i]));
                }
                school = name[0];
//        choose_school.setText(school);
                // 设置数据，默认选择第一条
                pckerscrlllview.setData(list);
                pckerscrlllview.setSelected(0);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ChooseSchoolActivity.this, errorResponse.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    // 滚动选择器选中事件
    PickerScrollView.onSelectListener pickerListener = new PickerScrollView.onSelectListener() {

        @Override
        public void onSelect(Pickers pickers) {
            System.out.println("选择：" + pickers.getShowConetnt() + "ID：" + pickers.getShowId());
            school = pickers.getShowConetnt();
            schoolId = pickers.getShowId();
        }
    };

    private class ChooseTypeListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.xiaoxue:
                    schoolType = xiaoxue.getText().toString();
                    schoolTypeid = 0;
                    break;
                case R.id.chuzhong:
                    schoolType = chuzhong.getText().toString();
                    schoolTypeid = 1;
                    break;
                case R.id.gaozhong:
                    schoolType = gaozhong.getText().toString();
                    schoolTypeid = 2;
                    break;
            }
        }
    }
}
