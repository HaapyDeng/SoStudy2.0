package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
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
import com.maxplus.sostudy.chatting.utils.DialogCreator;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseVersionActivity extends Activity {
    private ImageButton backButton;
    private GridView grid_course;
    private String token, id;
    private int versionCourse = 0;
    private String[] courses = new String[]{};
    private String[] coursesid = new String[]{};
    private String courseid, course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_version);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("course");
        versionCourse = bundle.getInt("version");
        Log.d("courseid===>>>", id);
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

    private void initDate() {
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnCourseVsionList();
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
        rp.put("type", versionCourse);
        rp.put("token", token);
        rp.put("sub", id);
        Log.d("type+token+sub===>>>", versionCourse + "+" + token + "+" + id);
        final Dialog mLoadingDialog = DialogCreator.createLoadingDialog(CourseVersionActivity.this,
                null);
        mLoadingDialog.show();
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
                    if (code == 0) {
                        JSONArray dataJsonArray = object.optJSONArray("data");
                        Log.d("dataJsonArray==》》", dataJsonArray.toString());
                        courses = new String[dataJsonArray.length()];
                        coursesid = new String[dataJsonArray.length()];
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.getJSONObject(i);
                            course = jsonObjectSon.getString("coursename");
                            courses[i] = course;
                            courseid = jsonObjectSon.getString("id");
                            coursesid[i] = courseid;
                            Log.d("course==>>>", course + ";" + courseid);
                        }
                        mLoadingDialog.dismiss();
                        SimpleAdapter adapter = new SimpleAdapter(CourseVersionActivity.this, getList(courses, coursesid),
                                R.layout.grid_course_my, new String[]{"course"},
                                new int[]{R.id.tv_title});
                        grid_course.setAdapter(adapter);
                        grid_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                courseid = coursesid[position];
                                Intent intent = new Intent(CourseVersionActivity.this, QuizListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("course", courseid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    } else {
                        mLoadingDialog.dismiss();
//                        Toast.makeText(WinterHolidayCourseActivity.this, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                        Toast.makeText(CourseVersionActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    mLoadingDialog.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        initDate();
        super.onResume();
    }
}
