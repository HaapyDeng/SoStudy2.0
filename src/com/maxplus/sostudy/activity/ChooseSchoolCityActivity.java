package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
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

public class ChooseSchoolCityActivity extends Activity {
    private String[] cityList = new String[]{};
    private int[] cityIdList = new int[]{};
    private int provinceid;
    private String city = "";
    private TextView ok;
    private int cityid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school_city);
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        provinceid = bundle.getInt("provinceid");
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnGetCityApi();
        Log.d("url", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams p = new RequestParams();
        p.put("provinceid", provinceid);
        client.get(url, p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (response.length() == 0) {
                    Toast.makeText(ChooseSchoolCityActivity.this, R.string.no_school, Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent();
                    intent2.setClass(ChooseSchoolCityActivity.this, ChooseSchoolActivity.class);
                    startActivity(intent2);
                    finish();
                }
                cityList = new String[response.length()];
                cityIdList = new int[response.length()];
                JSONArray arr = response;
                for (int i = 0; i < response.length(); i++) {
                    JSONObject temp = null;
                    try {
                        temp = (JSONObject) arr.get(i);
                        cityList[i] = temp.getString("city");
                        cityIdList[i] = temp.getInt("cityid");
                        Log.d("PLANETS==>>>", temp.getString("city"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                initToWheel(cityList, cityIdList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void initToWheel(final String[] cityList, final int[] cityIdList) {
        final WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(cityList));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + cityIdList[selectedIndex - 1] + ", item: " + item);
                city = item;
                cityid = cityIdList[selectedIndex - 1];
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (city.length() == 0 || cityid == 0) {
                    city = cityList[0].toString();
                    cityid = cityIdList[0];
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("city", "" + city);//添加要返回给页面1的数据
                bundle.putInt("cityid", cityid);
                intent.putExtras(bundle);
                setResult(4, intent);//返回页面1
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (city.length() == 0 || cityid == 0) {
                city = cityList[0].toString();
                cityid = cityIdList[0];
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("city", "" + city);//添加要返回给页面1的数据
            bundle.putInt("cityid", cityid);
            intent.putExtras(bundle);
            setResult(4, intent);//返回页面1
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //        实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (city.length() == 0 || cityid == 0) {
            city = cityList[0].toString();
            cityid = cityIdList[0];
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("city", "" + city);//添加要返回给页面1的数据
        bundle.putInt("cityid", cityid);
        intent.putExtras(bundle);
        setResult(4, intent);//返回页面1
        finish();
        return true;
    }
}
