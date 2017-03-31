package com.maxplus.sostudy.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.activity.DoingExerciseActivity;
import com.maxplus.sostudy.activity.LookAnswerActivity;
import com.maxplus.sostudy.entity.SubjectBean;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.ReplaceCharacter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends PagerAdapter {
    private String id, quesn, content, type, back, thetotal, therightv;
    private ArrayList<String> answer = new ArrayList<>();
    private String ckAnswer = "";
    private ArrayList<String> sucess = new ArrayList<>();
    private JSONArray alternative;
    private String option, text;
    DoingExerciseActivity mContext;
    // 传递过来的页面view的集合
    List<View> viewItems;
    // 每个item的页面view
    View convertView;
    // 传递过来的所有数据
    List<SubjectBean> dataItems;


    public SubjectAdapter(DoingExerciseActivity context, List<View> viewItems, List<SubjectBean> dataItems) {
        mContext = context;
        this.viewItems = viewItems;
        this.dataItems = dataItems;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Log.d("===>>>>", "instantiateItem is run");
        final ViewHolder holder = new ViewHolder();
        convertView = viewItems.get(position);
//        SubjectBean subjectBean = new SubjectBean();
        content = dataItems.get(position).getContent();
        alternative = dataItems.get(position).getAlternative();
        holder.rgLayout = (RadioGroup) convertView.findViewById(R.id.rg_rb);
        holder.ckLayout = (LinearLayout) convertView.findViewById(R.id.ck_ll);
        holder.webview = (WebView) convertView.findViewById(R.id.webview);
        holder.webview.getSettings().setJavaScriptEnabled(true);
        holder.webview.getSettings().setBlockNetworkImage(false);
        holder.webview.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //让缩放显示的最值百分比
        //        holder.webview.setInitialScale(50);
        Log.d("content==>>", ReplaceCharacter.deleteChar(content));
        holder.webview.loadDataWithBaseURL(null, ReplaceCharacter.deleteChar(content), "text/html", "utf-8", null);
        //添加选项内容
        holder.webviewA = (WebView) convertView.findViewById(R.id.webview_a);
        holder.webviewA.getSettings().setJavaScriptEnabled(true);
        holder.webviewA.getSettings().setBlockNetworkImage(false);
        holder.webviewA.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webviewA.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webviewA.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        holder.webviewB = (WebView) convertView.findViewById(R.id.webview_b);
        holder.webviewB.getSettings().setJavaScriptEnabled(true);
        holder.webviewB.getSettings().setBlockNetworkImage(false);
        holder.webviewB.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webviewB.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webviewB.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        holder.webviewC = (WebView) convertView.findViewById(R.id.webview_c);
        holder.webviewC.getSettings().setJavaScriptEnabled(true);
        holder.webviewC.getSettings().setBlockNetworkImage(false);
        holder.webviewC.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webviewC.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webviewC.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        holder.webviewD = (WebView) convertView.findViewById(R.id.webview_d);
        holder.webviewD.getSettings().setJavaScriptEnabled(true);
        holder.webviewD.getSettings().setBlockNetworkImage(false);
        holder.webviewD.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webviewD.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        holder.webviewE = (WebView) convertView.findViewById(R.id.webview_e);
        holder.webviewE.getSettings().setJavaScriptEnabled(true);
        holder.webviewE.getSettings().setBlockNetworkImage(false);
        holder.webviewE.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webviewE.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        for (int i = 0; i < alternative.length(); i++) {
            try {
                JSONObject temp = (JSONObject) alternative.get(i);
                option = temp.getString("option");
                text = temp.getString("text");
                if (i == 0) {
                    holder.webviewA.loadDataWithBaseURL(null, option + ". " + text, "text/html", "utf-8", null);
                } else if (i == 1) {
                    holder.webviewB.loadDataWithBaseURL(null, option + ". " + text, "text/html", "utf-8", null);
                } else if (i == 2) {
                    holder.webviewC.loadDataWithBaseURL(null, option + ". " + text, "text/html", "utf-8", null);
                } else if (i == 3) {
                    holder.webviewD.loadDataWithBaseURL(null, option + ". " + text, "text/html", "utf-8", null);
                } else if (i == 4) {
                    holder.webviewE.setVisibility(View.VISIBLE);
                    holder.webviewE.loadDataWithBaseURL(null, text + ". " + option, "text/html", "utf-8", null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("alternative===>>", alternative.toString() + "\n" + option);
        /**
         判断题目是单选还是多选
         type =3为单选
         type = 4为多选
         */
        SubjectBean subjectBean = new SubjectBean();
        type = dataItems.get(position).getType();
        if (type.equals("3")) {
            holder.ckLayout.setVisibility(View.GONE);
            //单选操作
            holder.a = (RadioButton) convertView.findViewById(R.id.choose_A);
            holder.b = (RadioButton) convertView.findViewById(R.id.choose_B);
            holder.c = (RadioButton) convertView.findViewById(R.id.choose_C);
            holder.d = (RadioButton) convertView.findViewById(R.id.choose_D);
            if (alternative.length() <= 3) {
                holder.d.setVisibility(View.GONE);
            }
            holder.a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.a.isChecked() == true) {
                        Log.d("answer==>>", "A");
                        Log.d("postion==>>>", String.valueOf(position));
                        Log.d("answer.size()==>>>", String.valueOf(answer.size()));
                        Log.d("dataItems.size()==>>>", String.valueOf(dataItems.size()));
                        if (answer.size() >= (dataItems.size())) {

                            answer.set(position, "A");
                        } else {
                            answer.add(position, "A");
                        }
                    }
                }
            });
            holder.b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.b.isChecked() == true) {
                        Log.d("answer==>>", "B");
                        Log.d("postion==>>>", String.valueOf(position));
                        Log.d("answer.size()==>>>", String.valueOf(answer.size()));
                        Log.d("dataItems.size()==>>>", String.valueOf(dataItems.size()));
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, "B");
                        } else {
                            answer.add(position, "B");
                        }
                    }
                }
            });
            holder.c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.c.isChecked() == true) {
                        Log.d("answer==>>", "C");
                        Log.d("postion==>>>", String.valueOf(position));
                        Log.d("answer.size()==>>>", String.valueOf(answer.size()));
                        Log.d("dataItems.size()==>>>", String.valueOf(dataItems.size()));
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, "C");
                        } else {
                            answer.add(position, "C");
                        }
                    }

                }
            });
            holder.d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                        if (holder.d.isChecked() == true) {
                                                            Log.d("answer==>>", "D");
                                                            Log.d("postion==>>>", String.valueOf(position));
                                                            Log.d("answer.size()==>>>", String.valueOf(answer.size()));
                                                            Log.d("dataItems.size()==>>>", String.valueOf(dataItems.size()));
                                                            if (answer.size() >= (dataItems.size())) {
                                                                answer.set(position, "D");
                                                            } else {
                                                                answer.add(position, "D");
                                                            }
                                                        }
                                                    }
                                                }

            );
        } else if (type.equals("4")) {
            holder.rgLayout.setVisibility(View.GONE);
            holder.ckLayout.setVisibility(View.VISIBLE);

            //多选操作
            holder.ck_a = (CheckBox) convertView.findViewById(R.id.ck_A);
            holder.ck_b = (CheckBox) convertView.findViewById(R.id.ck_B);
            holder.ck_c = (CheckBox) convertView.findViewById(R.id.ck_C);
            holder.ck_d = (CheckBox) convertView.findViewById(R.id.ck_D);
            if (alternative.length() <= 3) {
                holder.ck_d.setVisibility(View.GONE);
            }
            holder.ck_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.ck_a.isChecked()) {
                        ckAnswer = "A" + ckAnswer;
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, ckAnswer);
                        } else {
                            answer.add(position, ckAnswer);
                        }
                    } else {
                        ckAnswer = ckAnswer.replace("A", "");
                        answer.set(position, ckAnswer);
                    }
                }
            });
            holder.ck_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.ck_b.isChecked()) {
                        if (ckAnswer.contains("A") && !ckAnswer.contains("C") && !ckAnswer.contains("D")) {
                            ckAnswer = ckAnswer + "B";
                        } else if (ckAnswer.contains("A") && (ckAnswer.contains("C") || ckAnswer.contains("D"))) {
                            StringBuilder sb = new StringBuilder(ckAnswer);
                            ckAnswer = sb.insert(1, "B").toString();
                        } else {
                            ckAnswer = "B" + ckAnswer;
                        }
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, ckAnswer);
                        } else {
                            answer.add(position, ckAnswer);
                        }
                    } else {
                        ckAnswer = ckAnswer.replace("B", "");
                        answer.set(position, ckAnswer);
                    }
                }
            });
            holder.ck_c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.ck_c.isChecked()) {
                        if ((ckAnswer.contains("B") || ckAnswer.contains("A")) && !ckAnswer.contains("D")) {
                            ckAnswer = ckAnswer + "C";
                        } else if (ckAnswer.contains("A") && ckAnswer.contains("B") && ckAnswer.contains("D")) {
                            StringBuilder sb = new StringBuilder(ckAnswer);
                            ckAnswer = sb.insert(2, "C").toString();
                        } else if (ckAnswer.contains("A") && !ckAnswer.contains("B") && ckAnswer.contains("D")) {
                            StringBuilder sb = new StringBuilder(ckAnswer);
                            ckAnswer = sb.insert(1, "C").toString();
                        } else if (!ckAnswer.contains("A") && ckAnswer.contains("B") && ckAnswer.contains("D")) {
                            StringBuilder sb = new StringBuilder(ckAnswer);
                            ckAnswer = sb.insert(1, "C").toString();
                        } else if (!ckAnswer.contains("A") && !ckAnswer.contains("B")) {
                            ckAnswer = "C" + ckAnswer;
                        }
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, ckAnswer);
                        } else {
                            answer.add(position, ckAnswer);
                        }
                    } else {
                        ckAnswer = ckAnswer.replace("C", "");
                        answer.set(position, ckAnswer);
                    }
                }
            });
            holder.ck_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.ck_d.isChecked()) {
                        ckAnswer = ckAnswer + "D";
                        if (answer.size() >= (dataItems.size())) {
                            answer.set(position, ckAnswer);
                        } else {
                            answer.add(position, ckAnswer);
                        }
                    } else {
                        ckAnswer = ckAnswer.replace("D", "");
                        answer.set(position, ckAnswer);
                    }
                }
            });
        }


        holder.bottom_layout = (LinearLayout) convertView.findViewById(R.id.bottom_layout);
        holder.upLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_upLayout);
        holder.nextLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_nextLayout);
        holder.nextText = (TextView) convertView.findViewById(R.id.menu_bottom_nextTV);
        holder.nextImage = (ImageView) convertView.findViewById(R.id.menu_bottom_nextIV);
        if (position == (viewItems.size() - 1))

        {
            holder.nextText.setText(R.string.commit_answer);
            holder.nextImage.setVisibility(View.GONE);
        }

        holder.upLayout.setOnClickListener(new

                LinearOnClickListener((position - 1),

                false, position, holder));
        holder.nextLayout.setOnClickListener(new

                LinearOnClickListener((position + 1),

                true, position, holder));
        Log.d("postion is===>>>", String.valueOf(position));
        container.addView(viewItems.get(position));
        return viewItems.get(position);
    }

    /**
     * @author 设置上一步和下一步按钮监听
     */
    class LinearOnClickListener implements View.OnClickListener {
        private int mPosition;
        private int mPosition1;
        private boolean mIsNext;
        private ViewHolder viewHolder;

        public LinearOnClickListener(int position, boolean mIsNext, int position1, ViewHolder viewHolder) {
            mPosition = position;
            mPosition1 = position1;
            this.viewHolder = viewHolder;
            this.mIsNext = mIsNext;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_prepare_test_upLayout:
                    if (mPosition == -1) {
                        Toast.makeText(mContext, R.string.first_question, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mContext.setCurrentView(mPosition);
                    }
                    break;
                case R.id.activity_prepare_test_nextLayout:
                    Log.d("answer.toString==>>>", answer.toString());
                    for (int i = 0; i < answer.size(); i++) {
                        if (answer.get(i).equals("")) {
                            answer.remove(i);
                        }
                    }
                    if (answer.size() < mPosition) {
                        Toast.makeText(mContext, R.string.no_choose_answer, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mPosition == (viewItems.size())) {
                        Log.d("mPosition==>>>>", "" + mPosition);
                        Toast.makeText(mContext, R.string.submint_answer, Toast.LENGTH_SHORT).show();
                        Log.d("List<String> answer==>>>", answer.toString());
                        if (sucess.size() == 0) {
                            for (int i = 0; i < viewItems.size(); i++) {
                                sucess.add(dataItems.get(i).getSuccess());
                            }
                        }
                        Log.d("sucess==>>>", String.valueOf(sucess));
                        Log.d("answer+id===:::>>>", answer.get(mPosition - 1) + "+" + dataItems.get(mPosition - 1).getId());
                        doSaveAnswer(answer.get(mPosition - 1), Integer.parseInt(dataItems.get(mPosition - 1).getId()));
                        ArrayList bundlelist = new ArrayList();
                        bundlelist.add(answer);
                        ArrayList suceessList = new ArrayList();
                        suceessList.add(sucess);
                        Intent intent = new Intent(mContext, LookAnswerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("answer", bundlelist);
                        bundle.putParcelableArrayList("sucess", suceessList);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    } else {
                        Log.d("mPosition==>>>>", "" + mPosition);
                        Log.d("answer+id===:::>>>", answer.get(mPosition - 1) + "+" + dataItems.get(mPosition - 1).getId());
                        doSaveAnswer(answer.get(mPosition - 1), Integer.parseInt(dataItems.get(mPosition - 1).getId()));
                        mContext.setCurrentView(mPosition);
                    }
                    break;
            }
        }
    }

    //保存答案
    private void doSaveAnswer(String answer, int courseId) {
        if (!NetworkUtils.checkNetWork(mContext)) {
            Toast.makeText(mContext, R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
            return;
        }
        String url = NetworkUtils.returnUrl() + NetworkUtils.returnSaveAnswer();
        int setdate = 0;
        SharedPreferences mySharedPreferences = mContext.getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        String token = mySharedPreferences.getString("token", "");
        RequestParams rep = new RequestParams();
        rep.put("setdate", setdate);
        rep.put("answer", answer);
        rep.put("questions", courseId);
        rep.put("token", token);
        Log.d("ll==<>>>", url + "+" + setdate + "+" + answer + "+" + courseId);
        Log.d("ll:>>>>", token);
//        rep.put("atime", 0);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, rep, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.d("code==>>>", "" + response.getInt("code"));
                    if (response.getInt("code") == 0) {
//                        Toast.makeText(mContext, "baocunchenggong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, R.string.save_fail, Toast.LENGTH_LONG).show();
                        return;
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

    @Override
    public int getCount() {
        if (viewItems == null)
            return 0;
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    public class ViewHolder {
        WebView webview, webviewA, webviewB, webviewC, webviewD, webviewE;
        RadioButton a, b, c, d;
        RadioGroup rgLayout;
        CheckBox ck_a, ck_b, ck_c, ck_d;
        LinearLayout bottom_layout, upLayout, nextLayout, ckLayout;
        TextView nextText;
        ImageView nextImage;
    }


}
