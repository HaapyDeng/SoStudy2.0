package com.maxplus.sostudy.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.activity.DoingExerciseActivity;
import com.maxplus.sostudy.entity.SubjectBean;

import java.util.List;

public class SubjectAdapter extends PagerAdapter {
    private int success;
    private String id, quesn, content, alternative, type, back, thetotal, therightv;
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
        holder.webview.getSettings().setDefaultTextEncodingName("UTF -8");
        holder.webview.loadDataWithBaseURL(null, content + alternative, "text/html", "utf-8", null);
        holder.a = (RadioButton) convertView.findViewById(R.id.choose_A);
        holder.b = (RadioButton) convertView.findViewById(R.id.choose_B);
        holder.c = (RadioButton) convertView.findViewById(R.id.choose_C);
        holder.d = (RadioButton) convertView.findViewById(R.id.choose_D);
        holder.a.setOnClickListener(new ChooseAnswerClickListener());
        holder.b.setOnClickListener(new ChooseAnswerClickListener());
        holder.c.setOnClickListener(new ChooseAnswerClickListener());
        holder.d.setOnClickListener(new ChooseAnswerClickListener());
        holder.bottom_layout = (LinearLayout) convertView.findViewById(R.id.bottom_layout);
        holder.upLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_upLayout);
        holder.nextLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_nextLayout);
        holder.nextText = (TextView) convertView.findViewById(R.id.menu_bottom_nextTV);
        holder.nextImage = (ImageView) convertView.findViewById(R.id.menu_bottom_nextIV);
        if (position == (viewItems.size() - 1)) {
            holder.nextText.setText("提交");
        }
        holder.upLayout.setOnClickListener(new LinearOnClickListener((position - 1), false, position, holder));
        holder.nextLayout.setOnClickListener(new LinearOnClickListener((position + 1), true, position, holder));
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
                    if (mPosition == (viewItems.size())) {
                        Toast.makeText(mContext, R.string.submint_answer, Toast.LENGTH_SHORT).show();
                        return;
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

    private class ChooseAnswerClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.choose_A:
                    break;
                case R.id.choose_B:
                    break;
                case R.id.choose_C:
                    break;
                case R.id.choose_D:
                    break;
                default:
                    break;
            }
        }
    }
}
