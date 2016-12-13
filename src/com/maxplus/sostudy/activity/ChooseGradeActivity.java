package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.WheelView;

import java.util.Arrays;

public class ChooseGradeActivity extends Activity {
    private static final String[] PLANETS = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级",
            "七年级", "八年级", "九年级", "十年级", "十一年级", "十二年级"};
    private TextView ok;
    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_grade);
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(PLANETS));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + selectedIndex + ", item: " + item);
                grade = item;
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("sgrade", "" + grade);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                setResult(5, intent);//返回页面1
                finish();
            }
        });

    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        finish();
//        return true;
//    }

}
