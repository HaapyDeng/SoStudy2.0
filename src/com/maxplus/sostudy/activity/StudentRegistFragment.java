package com.maxplus.sostudy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.NetworkUtils;
import com.maxplus.sostudy.tools.RadioButtonAlertDialog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

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
    private String school, schoolName, grade, sclass, getRaelCode = "";
    ;

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

        getCode = (TextView) mRootView.findViewById(R.id.tv_sgetCode);
        getCode.setOnClickListener(this);
        et_password = (EditText) mRootView.findViewById(R.id.et_sinput_new_password);
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
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), ChooseSchoolActivity.class);
                startActivityForResult(intent1, 2);
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
                et_email = (EditText) mRootView.findViewById(R.id.et_sinput_email);
                email = et_email.getText().toString().trim();
                if (email.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_email, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.isEmail(email) == true) {
                    if (NetworkUtils.checkNetWork(getActivity()) == false) {
                        Toast.makeText(getActivity(), R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String urlM = NetworkUtils.returnUrl();
                    final String url = urlM + NetworkUtils.returnEmailCodeApi();
                    Log.d("url=====>>>", "" + url);
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("email", email);
                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            int status = 111;

                            try {
                                status = response.getInt("status");
                                Log.d("status==>>>>>", "" + status);
                                Log.d("response===>>>>>>", "" + response);
                                if (status == 1) {
                                    getRaelCode = response.getString("code");
                                    Log.d("code===>>>>>>", "" + getRaelCode);
                                    timer.start();
                                } else if (status == 0) {
                                    Toast.makeText(getActivity(), response.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Toast.makeText(getActivity(), R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.pl_right_email, Toast.LENGTH_SHORT).show();
                }

                break;

            //提交注册
            case R.id.btn_scommit:
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
                if (school == null || school == "") {
                    Toast.makeText(getActivity(), R.string.choose_school, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (grade == null || grade == "") {
                    Toast.makeText(getActivity(), R.string.choose_grade, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (sclass == null || sclass == "") {
                    Toast.makeText(getActivity(), R.string.choose_class, Toast.LENGTH_SHORT).show();
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
                if (email.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_email, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (emailCode.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_email_verify_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (emailCode.equals(getRaelCode) == false) {
                    Toast.makeText(getActivity(), R.string.input_error_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (password.length() < 8) {
                    Toast.makeText(getActivity(), R.string.input_new_password, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.checkNetWork(getActivity()) == false) {
                    Toast.makeText(getActivity(), R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                }
                doRegister(schoolName, grade, sclass, userName, realName, email, password);

                break;

        }
    }

    private void doRegister(String schoolName, String gradename, String classname, String username, String realname, String email, String password) {
        int i = 1;
        String registUrl = NetworkUtils.returnUrl() + NetworkUtils.returnRegistApi();
        Log.d("registUrl==>>>", registUrl);
        AsyncHttpClient registClient = new AsyncHttpClient();
        RequestParams rparams = new RequestParams();
        rparams.put("usertype", i);
        rparams.put("schoolname", schoolName);
        rparams.put("gradename", gradename);
        rparams.put("classname", classname);
        rparams.put("username", username);
        rparams.put("realname", realname);
        rparams.put("email", email);
        rparams.put("password", password);
        Log.d("userinfo==>>>", "" + i + "," + schoolName + "," + gradename + "," + classname
                + "," + username + "," + realname + "," + email + "," + password);
        registClient.post(registUrl, rparams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int json = (int) response.get("status");
                    if (json == 1) {
                        Toast.makeText(getActivity(), R.string.regist_success, Toast.LENGTH_LONG).show();
                        Log.d("response==>>>>", response.toString());
//                        SharedPreferences sp = getSharedPreferences()  ;
                        Intent log = new Intent();
                        log.setClass(getActivity(), LoginActivity.class);
                        startActivity(log);
                    } else {
                        String errorInfo = (String) response.get("errorInfo");
                        Toast.makeText(getActivity(), errorInfo, Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    /**
     * 倒计时功能
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if ((millisUntilFinished / 1000) < 60) {
                getCode.setEnabled(false);
            }
            getCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText("获取验证码");
        }
    };

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
                sclass = bundle.getString("sclass");
                choose_class.setText(bundle.getString("sclass") + "班");
            }
        } else if (requestCode == 2) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                school = bundle.getString("school");
                schoolName = bundle.getString("schoolname");
                Log.d("schoolName==>>>>", schoolName);
                choose_school.setText(bundle.getString("school"));
            }
        }
    }
}

