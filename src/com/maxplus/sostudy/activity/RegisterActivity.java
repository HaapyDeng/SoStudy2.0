package com.maxplus.sostudy.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.FragAdapter;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private ImageButton btn_back;
    private RadioGroup mradioGroup;
    private RadioButton tv_regstudent, tv_regteacher, tv_regparent;
    private ImageView iv_regstudent, iv_regteacher, iv_regparent;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;    //下方的可横向拖动的控件
//    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_student,layout_teacher,layout_parent)

    private List<View> mViews = new ArrayList<View>();
    private LocalActivityManager manager;
    private Intent intentStudent, intentTeacher, intentParent;
    private FragAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniController();//添加上方控制按钮
        iniListener();//添加监听

        mViewPager = (ViewPager) findViewById(R.id.pager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new StudentRegistFragment());
        fragmentList.add(new TeacherRegisterFragment());
        fragmentList.add(new ParentRegisterFragment());
        adapter = new FragAdapter(getSupportFragmentManager(), fragmentList);
//        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setAdapter(adapter);
        tv_regstudent.setChecked(true);
        mViewPager.setCurrentItem(0);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
    }

    /**
     * 获得当前被选中的RadioButton距离左侧的距离
     */
    private float getCurrentCheckedRadioLeft() {
        // TODO Auto-generated method stub
        if (tv_regstudent.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rdo1));
            return getResources().getDimension(R.dimen.tv_student);
        } else if (tv_regteacher.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rdo2));
            return getResources().getDimension(R.dimen.tv_teacher);
        } else if (tv_regparent.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rdo3));
            return getResources().getDimension(R.dimen.tv_parent);
        }

        return 0f;
    }

    private void iniVariable() {
        mViews = new ArrayList<View>();
        mViews.add(getLayoutInflater().inflate(R.layout.fragment_regist_student, null));
        mViews.add(getLayoutInflater().inflate(R.layout.fragment_teacher_register, null));
        mViews.add(getLayoutInflater().inflate(R.layout.fragment_parent_register, null));
//        mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器

    }

    private void iniListener() {
        mradioGroup.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    private void iniController() {
        btn_back = (ImageButton) findViewById(R.id.back_Button);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mradioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        tv_regstudent = (RadioButton) findViewById(R.id.tv_regstudent);
        tv_regteacher = (RadioButton) findViewById(R.id.tv_regteacher);
        tv_regparent = (RadioButton) findViewById(R.id.tv_regparent);
        iv_regstudent = (ImageView) findViewById(R.id.iv_regstudent);
        iv_regteacher = (ImageView) findViewById(R.id.iv_regteacher);
        iv_regparent = (ImageView) findViewById(R.id.iv_regparent);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        mViewPager = (ViewPager) findViewById(R.id.pager);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        if (checkedId == R.id.tv_regstudent) {
            tv_regstudent.setBackgroundColor(getResources().getColor(R.color.get_verify_code));
            iv_regstudent.setBackgroundColor(getResources().getColor(R.color.get_verify_code));
            tv_regstudent.setTextColor(getResources().getColor(R.color.white));

            tv_regteacher.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regteacher.setBackgroundColor(getResources().getColor(R.color.reg_teacher));
            tv_regteacher.setTextColor(getResources().getColor(R.color.gray));

            tv_regparent.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regparent.setBackgroundColor(getResources().getColor(R.color.reg_parent));
            tv_regparent.setTextColor(getResources().getColor(R.color.gray));
            mViewPager.setCurrentItem(0);

        } else if (checkedId == R.id.tv_regteacher) {
            tv_regstudent.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regstudent.setBackgroundColor(getResources().getColor(R.color.get_verify_code));
            tv_regstudent.setTextColor(getResources().getColor(R.color.gray));

            tv_regteacher.setBackgroundColor(getResources().getColor(R.color.reg_teacher));
            iv_regteacher.setBackgroundColor(getResources().getColor(R.color.reg_teacher));
            tv_regteacher.setTextColor(getResources().getColor(R.color.white));

            tv_regparent.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regparent.setBackgroundColor(getResources().getColor(R.color.reg_parent));
            tv_regparent.setTextColor(getResources().getColor(R.color.gray));
            mViewPager.setCurrentItem(1);
        } else if (checkedId == R.id.tv_regparent) {
            tv_regstudent.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regstudent.setBackgroundColor(getResources().getColor(R.color.get_verify_code));
            tv_regstudent.setTextColor(getResources().getColor(R.color.gray));

            tv_regteacher.setBackgroundColor(getResources().getColor(R.color.white));
            iv_regteacher.setBackgroundColor(getResources().getColor(R.color.reg_teacher));
            tv_regteacher.setTextColor(getResources().getColor(R.color.gray));

            tv_regparent.setBackgroundColor(getResources().getColor(R.color.reg_parent));
            iv_regparent.setBackgroundColor(getResources().getColor(R.color.reg_parent));
            tv_regparent.setTextColor(getResources().getColor(R.color.white));
            mViewPager.setCurrentItem(2);
        }
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
        mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft
                - (int) getResources().getDimension(R.dimen.tv_teacher), 0);
    }

    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
//            if (position == 0) {
//                mViewPager.setCurrentItem(1);
//            } else if (position == 1) {
//                tv_regstudent.performClick();
//            } else if (position == 2) {
//                tv_regteacher.performClick();
//            } else if (position == 3) {
//                tv_regparent.performClick();
//            } else if (position == 4) {
//                mViewPager.setCurrentItem(3);
//            }
            if (position == 0) {
                mViewPager.setCurrentItem(0);
                tv_regstudent.performClick();
            } else if (position == 1) {
                tv_regteacher.performClick();
            } else if (position == 2) {
                tv_regparent.performClick();
            } else if (position == 3) {
                mViewPager.setCurrentItem(2);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        private final List<Fragment> fl;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fl = fragmentList;
        }


        @Override
        public int getCount() {
            return fl.size();
        }


        @Override
        public Fragment getItem(int i) {
            return fl.get(i);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
