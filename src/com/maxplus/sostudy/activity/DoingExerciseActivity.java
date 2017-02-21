package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.ReplaceCharacter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoingExerciseActivity extends Activity {

    private String courseId;
    private ImageButton backButton;
    private String token;
    private String id, quesn, content, alternative;
    private int success;
    private WebView webView;

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
        getData();
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
                    if (total == 0) {
                        Toast.makeText(DoingExerciseActivity.this, R.string.no_course, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    if (code == 0) {
                        JSONArray dataJsonArray = object.getJSONArray("subject");
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObjectSon = (JSONObject) dataJsonArray.getJSONObject(i);
                            id = jsonObjectSon.getString("id");
                            alternative = ReplaceCharacter.Replace(ReplaceCharacter.addChar(jsonObjectSon.getString("alternative")));
                            content = ReplaceCharacter.Replace(jsonObjectSon.getString("content"));
                            quesn = jsonObjectSon.getString("quesn");
                            success = jsonObjectSon.getInt("success");
                            Log.d("content==>>>", content);
                            Log.d("alternative==>>", alternative);
                            webView = (WebView) findViewById(R.id.webview);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setDefaultTextEncodingName("UTF -8");
                            webView.loadDataWithBaseURL(null, content + alternative, "text/html", "utf-8", null);
                        }
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
}
