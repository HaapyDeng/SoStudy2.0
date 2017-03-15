package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.adapter.MyBaseExpandableListAdapter;
import com.maxplus.sostudy.entity.Group;
import com.maxplus.sostudy.entity.Item;
import com.maxplus.sostudy.tools.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizListActivity extends Activity {
    private String token;
    private String courseid;
    private ListView lv;
    //    private Myadapter adapter;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    List<Map<String, String>> sequenceData = new ArrayList<Map<String, String>>();
    private ImageButton backButton;

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private ExpandableListView exlist_text;
    private MyBaseExpandableListAdapter myAdapter = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        mContext = QuizListActivity.this;
        backButton = (ImageButton) findViewById(R.id.back_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        courseid = bundle.getString("course");
        Log.d("courseid===>>>", courseid);
        getDate();

    }

    private void getDate() {
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
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnQuizList();
        Log.d("url==>>", url);
        RequestParams rp = new RequestParams();
        rp.put("token", token);
        rp.put("id", courseid);
        rp.put("type", "holiday");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object = response;
                Log.d("QuizList,response===>>>", response.toString());
                int code;
                try {
                    code = object.getInt("code");
                    if (code == 1000) {
                        Toast.makeText(QuizListActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();
                    } else if (code == 0) {
                        gData = new ArrayList<Group>();
                        iData = new ArrayList<ArrayList<Item>>();
                        lData = new ArrayList<Item>();
                        JSONArray dataJsonArray = object.getJSONArray("data");
                        Log.d("dataJsonArray==>>", dataJsonArray.toString());
                        Log.d("dataJsonArraylength==>>", "" + dataJsonArray.length());
                        String id, name, sid, sname;
                        for (int i = 0; i < 2; i++) {
                            JSONObject jsonObject = (JSONObject) dataJsonArray.getJSONObject(i);
                            Log.d("jsonObject==..", jsonObject.toString());
                            id = jsonObject.getString("id");
                            name = jsonObject.getString("name");
                            if (jsonObject.has("sequence") == true) {
                                JSONArray sequence = jsonObject.getJSONArray("sequence");
                                for (int j = 0; j < sequence.length(); j++) {
                                    JSONObject jsonsequence = (JSONObject) sequence.getJSONObject(j);
                                    sid = jsonsequence.getString("id");
                                    sname = jsonsequence.getString("name");
                                    lData.add(new Item(sname, sid));
                                    Log.d("lData==>>>", lData.toString());
                                }
                                iData.add(lData);
                                Log.d("iData==>>>", iData.toString());
                            }
                            gData.add(new Group(name, id));
                            Log.d("gData==>>>", gData.toString());
                        }
                        exlist_text = (ExpandableListView) findViewById(R.id.lv_list);
                        exlist_text.setGroupIndicator(null);
                        myAdapter = new MyBaseExpandableListAdapter(gData, iData, mContext);
                        exlist_text.setAdapter(myAdapter);
                        //控制没有子集的项目列表不展开
                        exlist_text.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                                Log.d("groupPosition==>>", "" + groupPosition);
                                if (1 == 1) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        });
                        //为列表设置点击事件
                        exlist_text.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiId(), Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
//                        Log.d("data==>", data.toString());
//                        adapter = new Myadapter();
//                        lv = (ListView) findViewById(R.id.lv_list);
//                        lv.setAdapter(adapter);
                    } else {
                        Toast.makeText(QuizListActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();
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
/**
 class Myadapter extends BaseAdapter {

 private LayoutInflater inflater = LayoutInflater.from(QuizListActivity.this);

 @Override public int getCount() {
 return data.size();
 }

 @Override public Object getItem(int i) {
 return i;
 }

 @Override public long getItemId(int i) {
 return i;
 }

 private class ViewHolder {
 private TextView tvname;
 }

 @Override public View getView(int i, View view, ViewGroup viewGroup) {
 ViewHolder viewHolder = null;
 if (view == null) {
 view = inflater.inflate(R.layout.quiz_list, null);
 viewHolder = new ViewHolder();
 viewHolder.tvname = (TextView) view.findViewById(R.id.name);
 view.setTag(viewHolder);
 } else {
 viewHolder = (ViewHolder) view.getTag();
 }
 final String courseName = data.get(i).get("name");
 final String courseId = data.get(i).get("id");
 Log.d("a is:", courseName);
 viewHolder.tvname.setText(courseName);
 viewHolder.tvname.setOnClickListener(new View.OnClickListener() {
 @Override public void onClick(View view) {
 Log.d("courseName+courseid==>>", courseName + "+" + courseId);
 Intent intent = new Intent(QuizListActivity.this, DoingExerciseActivity.class);
 Bundle bundle = new Bundle();
 bundle.putString("courseId", courseId);
 intent.putExtras(bundle);
 startActivity(intent);
 }
 });
 return view;
 }
 }
 */
}
