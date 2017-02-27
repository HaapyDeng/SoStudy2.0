package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.adapter.SubjectAdapter;
import com.maxplus.sostudy.entity.SubjectBean;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.ReplaceCharacter;
import com.maxplus.sostudy.tools.ViewPagerScroller;
import com.maxplus.sostudy.view.VoteSubmitViewPager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DoingExerciseActivity extends Activity {

    SubjectAdapter pagerAdapter;
    private String courseId;
    private ImageButton backButton;
    private String token;
    //    private String id, quesn, content, alternative, type;
    VoteSubmitViewPager viewPager;
    private int[] success;
    private WebView webView;
    private String[] id, quesn, content, alternative, type, back, thetotal, therightv;
    List<SubjectBean> subjectItems = new ArrayList<SubjectBean>();
    List<View> viewItems = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_exercise);
        backButton = (ImageButton) findViewById(R.id.back_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        courseId = bundle.getString("courseId");
        Log.d("courseid===>>>", courseId);
        initViews();
        getData();
    }

    private void initViews() {
        viewPager = (VoteSubmitViewPager) findViewById(R.id.vote_submit_viewpager);
        initViewPagerScroll();
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    private void getData() {
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
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnQuestions();
        Log.d("url==>>>", url);
        RequestParams rp = new RequestParams();
        rp.put("token", token);
        rp.put("id", courseId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object = response;
                Log.d("QuizList,response===>>>", response.toString());
                int code, total;
                try {
                    code = object.getInt("code");
                    total = object.getInt("total");
                    id = new String[total];
                    alternative = new String[total];
                    content = new String[total];
                    quesn = new String[total];
                    success = new int[total];
                    type = new String[total];
                    if (total == 0) {
                        Toast.makeText(DoingExerciseActivity.this, R.string.no_course, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    if (code == 0) {
                        JSONArray dataJsonArray = object.getJSONArray("subject");
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.getJSONObject(i);
                            id[i] = jsonObjectSon.getString("id");
                            alternative[i] = ReplaceCharacter.Replace(ReplaceCharacter.addChar(jsonObjectSon.getString("alternative")));
                            content[i] = ReplaceCharacter.Replace(jsonObjectSon.getString("content"));
                            quesn[i] = jsonObjectSon.getString("quesn");
                            success[i] = jsonObjectSon.getInt("success");
                            type[i] = jsonObjectSon.getString("type");
//                            back[i] = jsonObjectSon.getString("back");
//                            thetotal[i] = jsonObjectSon.getString("thetotal");
//                            therightv[i] = jsonObjectSon.getString("therightv");
                            Log.d("All info is==>>>", i + "--->" + id[i] + ":" + content[i] + ":" + alternative[i] + ":" + quesn[i] + ":" + success[i] + ":" + type[i]);
                            Log.d("alternative==>>", alternative[i]);
//                            webView = (WebView) findViewById(R.id.webview);
//                            webView.getSettings().setJavaScriptEnabled(true);
//                            webView.getSettings().setDefaultTextEncodingName("UTF -8");
//                            webView.loadDataWithBaseURL(null, content[i] + alternative[i], "text/html", "utf-8", null);
                            SubjectBean subject = new SubjectBean();
                            subject.setId(id[i]);
                            subject.setAlternative(alternative[i]);
                            subject.setContent(content[i]);
                            subject.setQuesn(quesn[i]);
                            subject.setSuccess(success[i]);
                            subject.setType(type[i]);
//                            subject.setBack(back[i]);
//                            subject.setThetotal(thetotal[i]);
//                            subject.setTherightv(therightv[i]);
                            subjectItems.add(subject);
                        }
                        for (int i = 0; i < subjectItems.size(); i++) {
                            viewItems.add(getLayoutInflater().inflate(R.layout.vote_submit_viewpager_item, null));
                        }
                        pagerAdapter = new SubjectAdapter(
                                DoingExerciseActivity.this, viewItems,
                                subjectItems);
                        viewPager.setAdapter(pagerAdapter);
                        viewPager.getParent()
                                .requestDisallowInterceptTouchEvent(false);
                        Log.d("All info is==>>>", ">>>>>>" + id.length + ":" + content.length + ":" + alternative.length + ":"
                                + quesn.length + ":" + success.length + ":" + type.length);
                    } else {
                        Toast.makeText(DoingExerciseActivity.this, object.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
     * @param index
     *            根据索引值切换页面
     */
    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

}
