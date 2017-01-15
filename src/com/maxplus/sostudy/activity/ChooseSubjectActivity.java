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

public class ChooseSubjectActivity extends Activity {
    private static final String[] PLANETS = new String[]{"语文", "数学", "英语", "物理", "生物", "化学",
            "思想品德", "历史", "地理"};
    private TextView ok;
    private String subject = PLANETS[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(PLANETS));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + selectedIndex + ", item: " + item);
                subject = item;
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("ssubject", subject);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                setResult(5, intent);//返回页面1
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("ssubject", subject);//添加要返回给页面1的数据
            intent.putExtras(bundle);
            setResult(5, intent);//返回页面1
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //        实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("ssubject", subject);//添加要返回给页面1的数据
        intent.putExtras(bundle);
        setResult(5, intent);//返回页面1
        finish();
        return true;
    }
}
