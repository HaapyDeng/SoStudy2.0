package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.WheelView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ChooseSchoolQuXianActivity extends Activity {
    private int cityid;
    private String[] quxianList = new String[]{};
    private int[] quxianIdList = new int[]{};
    private String quxian = "";
    private int quxianId;
    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school_qu_xian);
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cityid = bundle.getInt("cityid");
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnGetDistrict();
        Log.d("url==>>>>", url);
        RequestParams param = new RequestParams();
        param.put("cityid", cityid);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                quxianList = new String[response.length()];
                quxianIdList = new int[response.length()];
                JSONArray arr = response;
                for (int i = 0; i < response.length(); i++) {
                    JSONObject temp = null;
                    try {
                        temp = (JSONObject) arr.get(i);
                        quxianList[i] = temp.getString("district");
                        quxianIdList[i] = temp.getInt("districtid");
                        Log.d("PLANETS==>>>", temp.getString("district"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                initToWheel(quxianList, quxianIdList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initToWheel(final String[] quxianList, final int[] quxianIdList) {
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(quxianList));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + quxianIdList[selectedIndex - 1] + ", item: " + item);
                quxian = item;
                quxianId = quxianIdList[selectedIndex - 1];
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (quxian.length() == 0 || quxianId == 0) {
                    quxian = quxianList[0].toString();
                    quxianId = quxianIdList[0];

                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("quxian", "" + quxian);//添加要返回给页面1的数据
                bundle.putInt("quxianid", quxianId);
                intent.putExtras(bundle);
                setResult(5, intent);//返回页面1
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (quxian.length() == 0 || quxianId == 0) {
                quxian = quxianList[0].toString();
                quxianId = quxianIdList[0];

            }

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("quxian", "" + quxian);//添加要返回给页面1的数据
            bundle.putInt("quxianid", quxianId);
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
        if (quxian.length() == 0 || quxianId == 0) {
            quxian = quxianList[0].toString();
            quxianId = quxianIdList[0];

        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("quxian", "" + quxian);//添加要返回给页面1的数据
        bundle.putInt("quxianid", quxianId);
        intent.putExtras(bundle);
        setResult(5, intent);//返回页面1
        finish();
        return true;
    }
}
