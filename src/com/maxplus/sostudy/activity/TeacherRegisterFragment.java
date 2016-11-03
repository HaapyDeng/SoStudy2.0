package com.maxplus.sostudy.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherRegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherRegisterFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tchoose_school, tchoose_grade, tchoose_subject, tgetCode;
    EditText ett_userName, ett_realName, ett_phone, ett_phoneCode, ett_password;
    CheckBox ck_1, ck_2, ck_3, ck_4, ck_5, ck_6, ck_7, ck_8, ck_9, ck_10, ck_11, ck_12;
    RadioButton rb_1, rb_2, rb_3, rb_4, rb_5, rb_6, rb_7, rb_8, rb_9;
    com.maxplus.sostudy.tools.FlowRadioGroup rg_b;
    String userName, realName, phone, phoneCode, password;
    Button commit;
    CheckBox showPassword;
    List<CheckBox> checkBox = new ArrayList<CheckBox>();
    private View mRootView;
    private String tschool = "", tgrade = "", tsubject = "";


    public TeacherRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherRegisterFragment newInstance(String param1, String param2) {
        TeacherRegisterFragment fragment = new TeacherRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_teacher_register,
                (ViewGroup) getActivity().findViewById(R.id.reg_teacher), false);
        initViews();
    }

    private void initViews() {
        tchoose_school = (TextView) mRootView.findViewById(R.id.tvt_choose_school);
        tchoose_school.setOnClickListener(this);
        tchoose_grade = (TextView) mRootView.findViewById(R.id.tvt_choose_grade);
        tchoose_grade.setOnClickListener(this);
        tchoose_subject = (TextView) mRootView.findViewById(R.id.tvt_choose_subject);
        tchoose_subject.setOnClickListener(this);
        tgetCode = (TextView) mRootView.findViewById(R.id.tvt_getCode);
        tgetCode.setOnClickListener(this);
        showPassword = (CheckBox) mRootView.findViewById(R.id.showPassword);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (showPassword.isChecked()) {
                    ett_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.visible);
                } else {
                    ett_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.unvisible);
                }

            }
        });
        commit = (Button) mRootView.findViewById(R.id.btn_commit);
        commit.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //选择学校
            case R.id.tvt_choose_school:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), ChooseSchoolActivity.class);
                startActivityForResult(intent1, 2);
                break;
            //选择年级
            case R.id.tvt_choose_grade:
                if (checkBox != null) {
                    checkBox.clear();
                }
                LayoutInflater inflaterDl = LayoutInflater.from(getActivity());
                RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.dialog_teacherchoosegrade, null);
                final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.show();
                dialog.getWindow().setContentView(layout);
                ck_1 = (CheckBox) layout.findViewById(R.id.ck_1);
                ck_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_1.isChecked() == true) {
                            checkBox.add(ck_1);
                        }
                    }
                });
                ck_2 = (CheckBox) layout.findViewById(R.id.ck_2);
                ck_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.d("ck_2>>>>>>>>", "" + ck_2.isChecked());
                        if (b == true) {
                            Log.d("ck_2>>>>>>>>", "check!!!!!!");
                            checkBox.add(ck_2);

                        } else {
                            checkBox.remove(ck_2);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_2.setChecked(false);
                        }
                    }
                });
                ck_3 = (CheckBox) layout.findViewById(R.id.ck_3);
                ck_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_3.isChecked() == true) {
                            checkBox.add(ck_3);
                        } else {
                            checkBox.remove(ck_3);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_3.setChecked(false);
                        }
                    }
                });
                ck_4 = (CheckBox) layout.findViewById(R.id.ck_4);
                ck_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_4.isChecked() == true) {
                            checkBox.add(ck_4);
                        } else {
                            checkBox.remove(ck_4);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_4.setChecked(false);
                        }
                    }
                });
                ck_5 = (CheckBox) layout.findViewById(R.id.ck_5);
                ck_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_5.isChecked() == true) {
                            checkBox.add(ck_5);
                        } else {
                            checkBox.remove(ck_5);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_5.setChecked(false);
                        }
                    }
                });
                ck_6 = (CheckBox) layout.findViewById(R.id.ck_6);
                ck_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_6.isChecked() == true) {
                            checkBox.add(ck_6);
                        } else {
                            checkBox.remove(ck_6);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_6.setChecked(false);
                        }
                    }
                });
                ck_7 = (CheckBox) layout.findViewById(R.id.ck_7);
                ck_7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_7.isChecked() == true) {
                            checkBox.add(ck_7);
                        } else {
                            checkBox.remove(ck_7);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_7.setChecked(false);
                        }
                    }
                });
                ck_8 = (CheckBox) layout.findViewById(R.id.ck_8);
                ck_8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_8.isChecked() == true) {
                            checkBox.add(ck_8);
                        } else {
                            checkBox.remove(ck_8);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_8.setChecked(false);
                        }
                    }
                });
                ck_9 = (CheckBox) layout.findViewById(R.id.ck_9);
                ck_9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_9.isChecked() == true) {
                            checkBox.add(ck_9);
                        } else {
                            checkBox.remove(ck_9);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_9.setChecked(false);
                        }
                    }
                });
                ck_10 = (CheckBox) layout.findViewById(R.id.ck_10);
                ck_10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_10.isChecked() == true) {
                            checkBox.add(ck_10);
                        } else {
                            checkBox.remove(ck_10);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_10.setChecked(false);
                        }
                    }
                });
                ck_11 = (CheckBox) layout.findViewById(R.id.ck_11);
                ck_11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_11.isChecked() == true) {
                            checkBox.add(ck_11);
                        } else {
                            checkBox.remove(ck_11);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_11.setChecked(false);
                        }
                    }
                });
                ck_12 = (CheckBox) layout.findViewById(R.id.ck_12);
                ck_12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (ck_12.isChecked() == true) {
                            checkBox.add(ck_12);
                        } else {
                            checkBox.remove(ck_12);
                            Log.d("checkBox>>>>>>", "" + checkBox);
                            ck_12.setChecked(false);
                        }
                    }
                });
                TextView tv_cancel = (TextView) layout.findViewById(R.id.negativeButton);
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                TextView tv_ok = (TextView) layout.findViewById(R.id.positiveButton);
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!checkBox.isEmpty()) {

                            tgrade = "";
                            for (CheckBox cbx : checkBox) {
                                if (cbx.isChecked()) {
                                    tgrade = tgrade + cbx.getText().toString() + ",";
                                    Log.d("tgrade>>>>>", "" + tgrade);
                                }
                            }
                            if (tgrade != null) {
                                tchoose_grade.setText(tgrade);
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }

                });
                break;
            //选择学科
            case R.id.tvt_choose_subject:
                LayoutInflater inflater2 = LayoutInflater.from(getActivity());
                RelativeLayout layout2 = (RelativeLayout) inflater2.
                        inflate(R.layout.dialog_teacherchoosesubject, null);
                final Dialog dialog2 = new AlertDialog.Builder(getActivity()).create();
                dialog2.show();
                dialog2.getWindow().setContentView(layout2);
                rb_1 = (RadioButton) layout2.findViewById(R.id.rb_1);
                rb_1.setOnClickListener(new MyOnClieckListener());
                rb_2 = (RadioButton) layout2.findViewById(R.id.rb_2);
                rb_2.setOnClickListener(new MyOnClieckListener());
                rb_3 = (RadioButton) layout2.findViewById(R.id.rb_3);
                rb_3.setOnClickListener(new MyOnClieckListener());
                rb_4 = (RadioButton) layout2.findViewById(R.id.rb_4);
                rb_4.setOnClickListener(new MyOnClieckListener());
                rb_5 = (RadioButton) layout2.findViewById(R.id.rb_5);
                rb_5.setOnClickListener(new MyOnClieckListener());
                rb_6 = (RadioButton) layout2.findViewById(R.id.rb_6);
                rb_6.setOnClickListener(new MyOnClieckListener());
                rb_7 = (RadioButton) layout2.findViewById(R.id.rb_7);
                rb_7.setOnClickListener(new MyOnClieckListener());
                rb_8 = (RadioButton) layout2.findViewById(R.id.rb_8);
                rb_8.setOnClickListener(new MyOnClieckListener());
                rb_9 = (RadioButton) layout2.findViewById(R.id.rb_9);
                rb_9.setOnClickListener(new MyOnClieckListener());

                TextView cancel_sub = (TextView) layout2.findViewById(R.id.sub_negativeButton);
                cancel_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });
                TextView ok_sub = (TextView) layout2.findViewById(R.id.sub_positiveButton);
                ok_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tsubject != "") {
                            tchoose_subject.setText(tsubject);
                            dialog2.dismiss();
                        } else {
                            dialog2.dismiss();
                        }
                    }
                });
                break;
//            //输入用户名
//            case R.id.ett_input_user:
//                break;
//            //输入姓名
//            case R.id.ett_input_name:
//                break;
//            //输入手机号
//            case R.id.ett_input_phone:
//                break;
            //获取手机验证码
            case R.id.tvt_getCode:
                ett_phone = (EditText) mRootView.findViewById(R.id.ett_input_phone);
                phone = ett_phone.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_phone_number, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.isMobileNO(phone) == true) {
                    timer.start();
                } else {
                    Toast.makeText(getActivity(), R.string.pl_right_phone, Toast.LENGTH_SHORT).show();
                }

                break;
//            //输入手机验证码
//            case R.id.ett_input_phone_code:
//                break;
//            //输入密码
//            case R.id.ett_input_new_password:
//                break;
            //提交注册
            case R.id.btn_commit:
                ett_userName = (EditText) mRootView.findViewById(R.id.ett_input_user);
                userName = ett_userName.getText().toString().trim();
                ett_realName = (EditText) mRootView.findViewById(R.id.ett_input_name);
                realName = ett_realName.getText().toString().trim();
                ett_phone = (EditText) mRootView.findViewById(R.id.ett_input_phone);
                phone = ett_phone.getText().toString().trim();
                ett_phoneCode = (EditText) mRootView.findViewById(R.id.ett_input_phone_code);
                phoneCode = ett_phoneCode.getText().toString().trim();
                ett_password = (EditText) mRootView.findViewById(R.id.ett_input_new_password);
                password = ett_password.getText().toString().trim();
                if (tschool == null || tschool == "") {
                    Toast.makeText(getActivity(), R.string.choose_school, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tgrade == null || tgrade == "") {
                    Toast.makeText(getActivity(), R.string.choose_grade, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tsubject == null || tsubject == "") {
                    Toast.makeText(getActivity(), R.string.choose_subject, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (userName.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_user_name, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (realName.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_name, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (phone.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_phone_number, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (phoneCode.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_phone_verify_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (password.length() < 8) {
                    Toast.makeText(getActivity(), R.string.input_new_password, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.checkNetWork(getActivity()) == false) {
                    Toast.makeText(getActivity(), R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                    break;
                }
                break;

        }

    }

    /**
     * 倒计时功能
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if ((millisUntilFinished / 1000) < 60) {
                tgetCode.setEnabled(false);
            }
            tgetCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tgetCode.setEnabled(true);
            tgetCode.setText("获取验证码");
        }
    };

    //选择学科的点击事件实现
    private class MyOnClieckListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rb_1:
                    tsubject = rb_1.getText().toString();
                    break;
                case R.id.rb_2:
                    tsubject = rb_2.getText().toString();
                    break;
                case R.id.rb_3:
                    tsubject = rb_3.getText().toString();
                    break;
                case R.id.rb_4:
                    tsubject = rb_4.getText().toString();
                    break;
                case R.id.rb_5:
                    tsubject = rb_5.getText().toString();
                    break;
                case R.id.rb_6:
                    tsubject = rb_6.getText().toString();
                    break;
                case R.id.rb_7:
                    tsubject = rb_7.getText().toString();
                    break;
                case R.id.rb_8:
                    tsubject = rb_8.getText().toString();
                    break;
                case R.id.rb_9:
                    tsubject = rb_9.getText().toString();
                    break;

            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //跳转并返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                tschool = bundle.getString("school");
                tchoose_school.setText(bundle.getString("school"));
            }
        }
    }
}
