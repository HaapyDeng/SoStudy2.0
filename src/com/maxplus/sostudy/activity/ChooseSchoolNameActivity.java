package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.WheelView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ChooseSchoolNameActivity extends Activity {
    private String districtid;
    private String[] schoolList = new String[]{};
    private int[] schoolIdList = new int[]{};
    private int schooltype;
    private String schoolName = "";
    private int schoolid = 0;
    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school_name);
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        districtid = bundle.getString("districtid");
        schooltype = bundle.getInt("schooltype");
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnGetSchoolname();
        Log.d("url==>>>", url);
        RequestParams param = new RequestParams();
        param.put("districtid", districtid);
        param.put("schooltype", schooltype);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                schoolList = new String[response.length()];
                schoolIdList = new int[response.length()];
                JSONArray arr = response;
                for (int i = 0; i < response.length(); i++) {
                    JSONObject temp = null;
                    try {
                        temp = (JSONObject) arr.get(i);
                        schoolList[i] = temp.getString("school");
                        schoolIdList[i] = temp.getInt("schoolid");
                        Log.d("PLANETS==>>>", temp.getString("school"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                initToWheel(schoolList, schoolIdList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initToWheel(final String[] schoolList, final int[] schoolIdList) {
        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(schoolList));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("item==>>>", "selectedIndex: " + schoolIdList[selectedIndex - 1] + ", item: " + item);
                schoolName = item;
                schoolid = schoolIdList[selectedIndex - 1];
            }
        });
        ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (schoolName.length() == 0 || schoolid == 0) {
                    schoolName = schoolList[0].toString();
                    schoolid = schoolIdList[0];

                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("schoolName", "" + schoolName);//添加要返回给页面1的数据
                bundle.putInt("schoolid", schoolid);
                intent.putExtras(bundle);
                setResult(7, intent);//返回页面1
                finish();
            }
        });
    }
}
