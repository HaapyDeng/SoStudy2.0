package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.WheelView;

import java.util.Arrays;

public class ChooseSchoolTypeActivity extends Activity {
    private String[] typeList = new String[]{"小学", "初中", "高中"};
    private int[] typeidList = new int[]{0, 1, 2};
    private String type = "";
    private int typeid = 10;
    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school_type);
        initToWheel(typeList, typeidList);
    }

    private void initToWheel(final String[] typeList, final int[] typeidList) {
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(typeList));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + typeList[selectedIndex - 1] + ", item: " + item);
                type = item;
                typeid = typeidList[selectedIndex - 1];
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (type.length() == 0 || typeid == 10) {
                    type = typeList[0].toString();
                    typeid = typeidList[0];

                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("type", "" + type);//添加要返回给页面1的数据
                bundle.putInt("typeid", typeid);
                intent.putExtras(bundle);
                setResult(6, intent);//返回页面1
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (type.length() == 0 || typeid == 10) {
                type = typeList[0].toString();
                typeid = typeidList[0];

            }

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("type", "" + type);//添加要返回给页面1的数据
            bundle.putInt("typeid", typeid);
            intent.putExtras(bundle);
            setResult(6, intent);//返回页面1
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //        实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (type.length() == 0 || typeid == 10) {
            type = typeList[0].toString();
            typeid = typeidList[0];

        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("type", "" + type);//添加要返回给页面1的数据
        bundle.putInt("typeid", typeid);
        intent.putExtras(bundle);
        setResult(6, intent);//返回页面1
        finish();
        return true;
    }
}


