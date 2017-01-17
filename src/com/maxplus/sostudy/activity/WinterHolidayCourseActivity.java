package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WinterHolidayCourseActivity extends Activity {
    private String token;
    private int winterCourse = 2;
    private String[] courses = new String[]{};
    private String[] coursesid = new String[]{};
    private String courseid, course;
    private GridView grid_course;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter_holiday_course);
        initDate();
        grid_course = (GridView) findViewById(R.id.gridView);
        adapter = new MyCourseGridAdapter();
        grid_course.setAdapter(adapter);
        grid_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
    }

    private void initDate() {
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnHSCourseList();
        Log.d("url===>>>", url);
        SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
        token = sp.getString("token", "");
        if (token.length() == 0) {
            Toast.makeText(this, R.string.outoftime, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (!NetworkUtils.checkNetWork(this)) {
            Toast.makeText(this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams rp = new RequestParams();
        rp.put("type", winterCourse);
        rp.put("tock", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object = response;
                int code;
                try {
                    code = object.getInt("code");
                    Log.d("code===>>>>", "" + code);
                    if (code == 1000) {
                        Toast.makeText(WinterHolidayCourseActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();
                    } else if (code == 0) {
                        JSONArray dataJsonArray = object.getJSONArray("data");
                        courses = new String[dataJsonArray.length()];
                        coursesid = new String[dataJsonArray.length()];
                        Log.d("dataLength==>>>>", "" + dataJsonArray.length());
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.opt(i);
                            course = jsonObjectSon.getString("fullname");
                            courses[i] = course;
                            courseid = jsonObjectSon.getString("id");
                            coursesid[i] = courseid;
                            Log.d("course==>>>", course + ";" + courseid);
                        }
                    } else {
                        Toast.makeText(WinterHolidayCourseActivity.this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                        return;
                    }
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

    //自定义寒假作业课程列表适配器
    public class MyCourseGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return courses.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(WinterHolidayCourseActivity.this, R.layout.grid_course_my, null);
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            title.setText(courses[position]);
            return view;
        }
    }
}
