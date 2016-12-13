package com.maxplus.sostudy.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.WheelView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ChooseCityActivity extends Activity {
    private String[] cityList = new String[]{};
    private TextView ok;
    private int[] cityidList = new int[]{};
    private String city = "";
    private int cityid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        initData();
    }

    private void initData() {
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnGetProvinceApi();
        Log.d("url==>>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    cityList = new String[response.length()];
                    cityidList = new int[response.length()];
                    JSONArray arr = response;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject temp = (JSONObject) arr.get(i);
                        cityList[i] = temp.getString("province");
                        cityidList[i] = temp.getInt("provinceid");
                        Log.d("PLANETS==>>>", temp.getString("province"));
                    }
                    Log.d("citylist==>>", cityList.toString());
                    initToWheel(cityList, cityidList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initToWheel(String[] cityList, final int[] cityidList) {
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(cityList));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + cityidList[selectedIndex-1] + ", item: " + item);
                city = item;
                cityid = cityidList[selectedIndex-1];
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (cityid==0){
                    city = "北京";
                    cityid =110000;
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("city", "" + city);//添加要返回给页面1的数据
                bundle.putInt("cityid", cityid);
                intent.putExtras(bundle);
                setResult(3, intent);//返回页面1
                finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("city", "" + city);//添加要返回给页面1的数据
            intent.putExtras(bundle);
            setResult(3, intent);//返回页面1
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
        bundle.putString("city", "" + city);//添加要返回给页面1的数据
        intent.putExtras(bundle);
        setResult(3, intent);//返回页面1
        finish();
        return true;
    }


}
