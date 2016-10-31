package com.maxplus.sostudy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.RadioButtonAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentRegistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentRegistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentRegistFragment extends Fragment implements View.OnClickListener {
    private TextView choose_school, choose_grade, choose_class, getCode;
    EditText et_userName, et_realName, et_email, et_emailCode, et_password;
    String userName, realName, email, emailCode, password;
    Button commit;
    CheckBox showPassword;
    private View mRootView;
    private String grade, sclass;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StudentRegistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentRegistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentRegistFragment newInstance(String param1, String param2) {
        StudentRegistFragment fragment = new StudentRegistFragment();
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
        mRootView = layoutInflater.inflate(R.layout.fragment_regist_student,
                (ViewGroup) getActivity().findViewById(R.id.regist), false);
        initView();
    }

    private void initView() {
        choose_school = (TextView) mRootView.findViewById(R.id.tv_schoose_school);
        choose_school.setOnClickListener(this);
        choose_grade = (TextView) mRootView.findViewById(R.id.tv_schoose_grade);
        choose_grade.setOnClickListener(this);
        choose_class = (TextView) mRootView.findViewById(R.id.tv_schoose_class);
        choose_class.setOnClickListener(this);
        et_userName = (EditText) mRootView.findViewById(R.id.et_sinput_user);
        userName = et_userName.getText().toString().trim();
        et_realName = (EditText) mRootView.findViewById(R.id.et_sinput_name);
        realName = et_realName.getText().toString().trim();
        et_email = (EditText) mRootView.findViewById(R.id.et_sinput_email);
        email = et_email.getText().toString().trim();
        et_emailCode = (EditText) mRootView.findViewById(R.id.et_sinput_email_code);
        emailCode = et_emailCode.getText().toString().trim();
        et_password = (EditText) mRootView.findViewById(R.id.et_sinput_new_password);
        password = et_password.getText().toString().trim();
        getCode = (TextView) mRootView.findViewById(R.id.tv_sgetCode);
        getCode.setOnClickListener(this);
        showPassword = (CheckBox) mRootView.findViewById(R.id.sshow_password);
        //点击密码可见或隐藏设置
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (showPassword.isChecked()) {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.visible);
                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setBackgroundResource(R.drawable.unvisible);
                }
            }
        });
        commit = (Button) mRootView.findViewById(R.id.btn_scommit);
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
            case R.id.tv_schoose_school:
                Intent intent1 =new Intent();
                intent1.setClass(getActivity(),ChooseSchoolActivity.class);
                startActivityForResult(intent1,2);
                break;
            //选择年级
            case R.id.tv_schoose_grade:
                final RadioButtonAlertDialog dialog = new RadioButtonAlertDialog(getActivity());

                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final RadioButton radioButton;
                        radioButton = (RadioButton) dialog.retunGrade();
                        if (radioButton == null) {
                            dialog.dismiss();
                        } else {
                            grade = radioButton.getText().toString();
                            choose_grade.setText(grade);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setOnNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
            //选择班级
            case R.id.tv_schoose_class:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChooseClassActivity.class);
                startActivityForResult(intent, 10);
                break;
            //获取验证码
            case R.id.tv_sgetCode:
                break;

            //提交注册
            case R.id.btn_scommit:
                break;

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
        if (requestCode == 10) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                choose_class.setText(bundle.getString("sclass") + "班");
            }
        }
    }
}

