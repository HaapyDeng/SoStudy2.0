package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.CityPicker;
import com.maxplus.sostudy.tools.CityPicker.OnSelectingListener;
import com.maxplus.sostudy.tools.PickerScrollView;
import com.maxplus.sostudy.tools.Pickers;

import java.util.ArrayList;
import java.util.List;

public class ChooseSchoolActivity extends Activity implements View.OnClickListener {
    private String city, school;
    private TextView choose_city, choose_school;
    private CityPicker cityPicker;
    private OnSelectingListener onSelectingListener;

    private Button confirm, bt_scrollchoose; // 滚动选择器按钮
    private PickerScrollView pckerscrlllview; // 滚动选择器
    private List<Pickers> list; // 滚动选择器数据
    private String[] id;
    private String[] name;
    private ImageButton bback_Button;
    private Button bt_yes; // 确定按钮
    private RelativeLayout picker_rel; // 选择器布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        initView();
        if (city == null) {
            city = cityPicker.getCity_string();
            choose_city.setText(city);
        }
        cityPicker.setOnSelectingListener(new OnSelectingListener() {
            @Override
            public void selected(boolean selected) {
                Log.d("selected====>>>>", "" + selected);
                if (selected == true) {
                    city = cityPicker.getCity_string();
                    choose_city.setText(city);
                } else {
                    choose_city.setText(city);
                }
            }
        });
        //获取对应省市区下面的学校数据
        initData();
    }

    private void initView() {
        bback_Button = (ImageButton)findViewById(R.id.bback_Button);
        bback_Button.setOnClickListener(this);
        choose_city = (TextView) findViewById(R.id.tv_choose_city);
        choose_city.setOnClickListener(this);
        cityPicker = (CityPicker) findViewById(R.id.citypicker);
        choose_school = (TextView) findViewById(R.id.choose_school);
        picker_rel = (RelativeLayout) findViewById(R.id.picker_rel);
        pckerscrlllview = (PickerScrollView) findViewById(R.id.pickerscrlllview);
        pckerscrlllview.setOnSelectListener(pickerListener);
        bt_yes = (Button) findViewById(R.id.picker_yes);
        choose_school.setOnClickListener(this);
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
            case R.id.choose_school:
                picker_rel.setVisibility(View.VISIBLE);
                break;
            case R.id.picker_yes:
                picker_rel.setVisibility(View.GONE);
                choose_school.setText(school);
                break;
            case R.id.confirm:
                if (city == null) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.choose_sssq, Toast.LENGTH_SHORT).show();
                } else if (school == null) {
                    Toast.makeText(ChooseSchoolActivity.this, R.string.choose_sschool, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = this.getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("school", "" + city + school);//添加要返回给页面1的数据
                    intent.putExtras(bundle);
                    this.setResult(2, intent);//返回页面1
                    this.finish();
                    break;
                }
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<Pickers>();
        id = new String[]{"1", "2", "3", "4", "5", "6"};
        name = new String[]{"四川大学", "成都大学", "湖北大学", "天津大学", "北大", "清华"};
        for (int i = 0; i < name.length; i++) {
            list.add(new Pickers(name[i], id[i]));
        }
        school = name[0];
        choose_school.setText(school);
        // 设置数据，默认选择第一条
        pckerscrlllview.setData(list);
        pckerscrlllview.setSelected(0);
    }

    // 滚动选择器选中事件
    PickerScrollView.onSelectListener pickerListener = new PickerScrollView.onSelectListener() {

        @Override
        public void onSelect(Pickers pickers) {
            System.out.println("选择：" + pickers.getShowConetnt());
            school = pickers.getShowConetnt();
        }
    };
}
