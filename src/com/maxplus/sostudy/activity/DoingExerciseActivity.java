package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.adapter.SubjectAdapter;
import com.maxplus.sostudy.chatting.utils.DialogCreator;
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
    VoteSubmitViewPager viewPager;
    private String[] success;
    private WebView webView;
    private JSONArray[] alternative;
    private String[] id, quesn, content, type, back, thetotal, therightv;
    List<SubjectBean> subjectItems = new ArrayList<SubjectBean>();
    List<View> viewItems = new ArrayList<View>();
    private TextView no_question;

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
        courseId = bundle.getString("id");
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
        final Dialog mLoadingDialog = DialogCreator.createLoadingDialog(DoingExerciseActivity.this,
                null);
        mLoadingDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject object = response;
                Log.d("QuizList===response===>>>", response.toString());
                int code, total;
                try {
                    code = object.getInt("code");
                    JSONArray dataJsonArray = object.getJSONArray("subject");
                    total = dataJsonArray.length();
                    id = new String[total];
                    alternative = new JSONArray[total];
                    content = new String[total];
                    quesn = new String[total];
                    success = new String[total];
                    type = new String[total];
                    if (total == 0) {
                        mLoadingDialog.dismiss();
                        no_question = (TextView) findViewById(R.id.no_question);
                        no_question.setVisibility(View.VISIBLE);
                        return;
                    }
                    if (code == 0) {
                        Log.d("code==0", "start!!!");
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.getJSONObject(i);
                            id[i] = jsonObjectSon.getString("id");
//                            alternative[i] = ReplaceCharacter.Replace(ReplaceCharacter.addChar(jsonObjectSon.getString("alternative")));
//                            alternative[i] = jsonObjectSon.getJSONArray("alternative");
//                            alternative[i] = (JSONArray) ReplaceCharacter.deleteChar(jsonObjectSon.getString("alternative"));
                            String va = ReplaceCharacter.deleteChar(jsonObjectSon.getString("alternative").toString());
                            Log.d("va==>>>", va);
                            JSONArray value = new JSONArray(va);
                            Log.d("value==>>>", value.toString());
                            alternative[i] = value;
//                            content[i] = ReplaceCharacter.Replace(jsonObjectSon.getString("content"));
                            content[i] = jsonObjectSon.getString("content");
//                            quesn[i] = jsonObjectSon.getString("quesn");
                            success[i] = jsonObjectSon.getString("success");
                            type[i] = jsonObjectSon.getString("type");
                            Log.d("All info is==>>>", i + "--->" + id[i] + ":" + content[i] + ":" + va + ":" + quesn[i] + ":"
                                    + success[i] + ":" + type[i]);
                            Log.d("alternative==>>", alternative[i].toString());
                            SubjectBean subject = new SubjectBean();
                            subject.setId(id[i]);//题目id
                            subject.setAlternative(alternative[i]);//题目选项内容
                            subject.setContent(content[i]);//题目标题
//                            subject.setQuesn(quesn[i]);//题目排序
                            subject.setSuccess(success[i]);//题目正确答案
                            subject.setType(type[i]);//题目类型
                            subject.setPapersid(courseId);//试卷id
//                            subject.save();//保存数据到litepal数据库
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
                        mLoadingDialog.dismiss();
                        Log.d("All info is==>>>", ">>>>>>" + id.length + ":" + content.length + ":" + alternative.length + ":"
                                + quesn.length + ":" + success.length + ":" + type.length);
                    } else {
                        mLoadingDialog.dismiss();
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
     * @param index 根据索引值切换页面
     */
    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

}
