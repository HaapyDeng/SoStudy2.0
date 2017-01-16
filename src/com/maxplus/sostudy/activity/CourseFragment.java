package com.maxplus.sostudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.controller.ContactsController;
import com.maxplus.sostudy.view.ContactsView;

public class CourseFragment extends BaseFragment implements View.OnClickListener {
    private View mRootView;
    private ImageView winter_holiday, summer_holiday, everyday, synchronize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_course,
                (ViewGroup) getActivity().findViewById(R.id.main_view), false);
        initViews();
    }

    private void initViews() {
        winter_holiday = (ImageView) mRootView.findViewById(R.id.im_hanjia);
        winter_holiday.setOnClickListener(this);
        summer_holiday = (ImageView) mRootView.findViewById(R.id.im_shujia);
        summer_holiday.setOnClickListener(this);
        everyday = (ImageView) mRootView.findViewById(R.id.im_richang);
        everyday.setOnClickListener(this);
        synchronize = (ImageView) mRootView.findViewById(R.id.im_tongbu);
        synchronize.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_hanjia:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), WinterHolidayCourseActivity.class);
                startActivity(intent1);
                break;
            case R.id.im_shujia:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), SummerHolidayCourseActivity.class);
                startActivity(intent2);
                break;
            case R.id.im_richang:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), EverydayCourseActivity.class);
                startActivity(intent3);
                break;
            case R.id.im_tongbu:
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), SynchronizeCourseActivity.class);
                startActivity(intent4);
                break;
        }
    }
}