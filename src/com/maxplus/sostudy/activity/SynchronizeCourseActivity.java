package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizeCourseActivity extends Activity {
    private String token;
    private String[] courses = new String[]{};
    private String[] coursesid = new String[]{};
    private String courseid, course;
    private GridView grid_course;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_course);
        backButton = (ImageButton) findViewById(R.id.back_Button);
        grid_course = (GridView) findViewById(R.id.gridView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initDate();
    }

    private void initDate() {
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnSyncList();
        Log.d("url==>>>", url);
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
        rp.put("token", token);
        Log.d("token===>>>", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object = response;
                Log.d("response===>>>", response.toString());
                int code;
                try {
                    code = object.getInt("code");
                    Log.d("code===>>>>", "" + code);
                    if (code == 1000) {
                        Toast.makeText(SynchronizeCourseActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                        return;
                    } else if (code == 0) {
                        JSONArray dataJsonArray = object.getJSONArray("data");
                        courses = new String[dataJsonArray.length()];
                        coursesid = new String[dataJsonArray.length()];
                        Log.d("dataLength==>>>>", "" + dataJsonArray.length());
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.getJSONObject(i);
                            course = jsonObjectSon.getString("coursename");
                            courses[i] = course;
                            courseid = jsonObjectSon.getString("id");
                            coursesid[i] = courseid;
                            Log.d("course==>>>", course + ";" + courseid);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(SynchronizeCourseActivity.this, getList(courses, coursesid),
                                R.layout.grid_course_my, new String[]{"course"},
                                new int[]{R.id.tv_title});
                        grid_course.setAdapter(adapter);
                        grid_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                courseid = coursesid[position];
                                Intent intent = new Intent(SynchronizeCourseActivity.this, QuizListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("course", courseid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public List<Map<String, Object>> getList(String[] courses, String[] coursesid) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (int i = 0; i < courses.length; i++) {
            map = new HashMap<String, Object>();
            map.put("course", courses[i]);
            map.put("courseid", coursesid[i]);
            list.add(map);
        }
        Log.d("this is getList", list.size() + "");
        return list;
    }
}
