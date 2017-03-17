package com.maxplus.sostudy.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.activity.DoingExerciseActivity;
import com.maxplus.sostudy.activity.LookAnswerActivity;
import com.maxplus.sostudy.entity.SubjectBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends PagerAdapter {
    private String id, quesn, content, alternative, type, back, thetotal, therightv;
    private ArrayList<String> answer = new ArrayList<>();
    private ArrayList<String> sucess = new ArrayList<>();
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
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.d("===>>>>", "instantiateItem is run");
        final ViewHolder holder = new ViewHolder();
        convertView = viewItems.get(position);
//        SubjectBean subjectBean = new SubjectBean();
        content = dataItems.get(position).getContent();
        alternative = dataItems.get(position).getAlternative();
        holder.webview = (WebView) convertView.findViewById(R.id.webview);
        holder.webview.getSettings().setJavaScriptEnabled(true);
        holder.webview.getSettings().setBlockNetworkImage(false);
        holder.webview.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //让缩放显示的最值百分比
//        holder.webview.setInitialScale(50);
        holder.webview.loadDataWithBaseURL(null, "jiushahsahhsdsffds<img src='http://img0.imgtn.bdimg.com/it/u=2620947518,865405288&fm=23&gp=0.jpg' class='imgSmall'>efddfdgfdgdfg<img src='http://img0.imgtn.bdimg.com/it/u=2620947518,865405288&fm=23&gp=0.jpg' class='imgSmall'>efddfd", "text/html", "utf-8", null);
//        holder.webview.loadUrl("http://img0.imgtn.bdimg.com/it/u=2620947518,865405288&fm=23&gp=0.jpg");
        holder.a = (RadioButton) convertView.findViewById(R.id.choose_A);
        holder.b = (RadioButton) convertView.findViewById(R.id.choose_B);
        holder.c = (RadioButton) convertView.findViewById(R.id.choose_C);
        holder.d = (RadioButton) convertView.findViewById(R.id.choose_D);
        final SubjectBean subjectBean = new SubjectBean();
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
        holder.bottom_layout = (LinearLayout) convertView.findViewById(R.id.bottom_layout);
        holder.upLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_upLayout);
        holder.nextLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_nextLayout);
        holder.nextText = (TextView) convertView.findViewById(R.id.menu_bottom_nextTV);
        holder.nextImage = (ImageView) convertView.findViewById(R.id.menu_bottom_nextIV);
        if (position == (viewItems.size() - 1))

        {
            holder.nextText.setText("提交");
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
                    if (answer.size() < mPosition) {
                        Toast.makeText(mContext, R.string.no_choose_answer, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mPosition == (viewItems.size())) {
                        Toast.makeText(mContext, R.string.submint_answer, Toast.LENGTH_SHORT).show();
                        Log.d("List<String> answer==>>>", answer.toString());
                        if (sucess.size() == 0) {
                            for (int i = 0; i < viewItems.size(); i++) {
                                sucess.add(dataItems.get(i).getSuccess());
                            }
                        }
                        Log.d("sucess==>>>", String.valueOf(sucess));
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
                        mContext.setCurrentView(mPosition);
                    }
                    break;
            }
        }
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
        WebView webview;
        RadioButton a, b, c, d;
        LinearLayout bottom_layout, upLayout, nextLayout;
        TextView nextText;
        ImageView nextImage;
    }


}
