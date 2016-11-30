package com.maxplus.sostudy.activity;

import android.content.Intent;
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
import android.widget.ImageView;
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
import org.json.JSONException;
import org.json.JSONObject;


public class ParentRegisterFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mRoot;
    // TODO: Rename and change types of parameters
    private TextView pchoose_school, pchoose_grade, pchoose_class, pget_code, tv_pgetuid;
    private EditText pinput_phone, pinput_phone_code, pinput_password, pinput_name, pinput_user;
    private CheckBox pshow_num;
    private Button pcommit;
    private String pschool, pgrade, pclass, puser, pname, pphone, pcode, ppassword, getRaelCode = "", childUid, pschoolName;

    public ParentRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParentRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParentRegisterFragment newInstance(String param1, String param2) {
        ParentRegisterFragment fragment = new ParentRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRoot = inflater.inflate(R.layout.fragment_parent_register,
                (ViewGroup) getActivity().findViewById(R.id.regist), false);
        initViews();
    }

    private void initViews() {
        pchoose_school = (TextView) mRoot.findViewById(R.id.im_pchoose_school);
        pchoose_school.setOnClickListener(this);
        pchoose_grade = (TextView) mRoot.findViewById(R.id.tv_pchoose_grade);
        pchoose_grade.setOnClickListener(this);
        pchoose_class = (TextView) mRoot.findViewById(R.id.tv_pchoose_class);
        pchoose_class.setOnClickListener(this);
        tv_pgetuid = (TextView) mRoot.findViewById(R.id.tv_pgetuid);
        tv_pgetuid.setOnClickListener(this);
        pget_code = (TextView) mRoot.findViewById(R.id.tv_pgetCode);
        pget_code.setOnClickListener(this);
        pinput_password = (EditText) mRoot.findViewById(R.id.et_pinput_new_password);
        pshow_num = (CheckBox) mRoot.findViewById(R.id.pshow_password);
        pshow_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pshow_num.isChecked()) {
                    pinput_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pshow_num.setBackgroundResource(R.drawable.visible);
                } else {
                    pinput_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pshow_num.setBackgroundResource(R.drawable.unvisible);
                }
            }
        });
        pcommit = (Button) mRoot.findViewById(R.id.btn_pcommit);
        pcommit.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mRoot;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_pchoose_school:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), ChooseSchoolActivity.class);
                startActivityForResult(intent1, 2);
                break;
            //选择孩子年级
            case R.id.tv_pchoose_grade:
                final RadioButtonAlertDialog dialog = new RadioButtonAlertDialog(getActivity());

                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final RadioButton radioButton;
                        radioButton = (RadioButton) dialog.retunGrade();
                        if (radioButton == null) {
                            dialog.dismiss();
                        } else {
                            pgrade = radioButton.getText().toString();
                            pchoose_grade.setText(pgrade);
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
            case R.id.tv_pchoose_class:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChooseClassActivity.class);
                Bundle bundle = new Bundle();
                startActivityForResult(intent, 10);
                break;
            //验证孩子是否存在，并返回hildrenid
            case R.id.tv_pgetuid:
                pinput_name = (EditText) mRoot.findViewById(R.id.et_pinput_name);
                pname = pinput_name.getText().toString().trim();
                if (pname.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_child_name, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pschoolName == null || pschoolName == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_school, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pgrade == null || pgrade == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_grade, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pclass == null || pclass == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_class, Toast.LENGTH_SHORT).show();
                    break;
                }
                doVerifyChild(pschoolName, pgrade, pclass, pname);
                break;
            case R.id.tv_pgetCode:
                pinput_phone = (EditText) mRoot.findViewById(R.id.et_pinput_num);
                pphone = pinput_phone.getText().toString().trim();
                if (pphone.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_phone_number, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.isMobileNO(pphone) == true) {
                    if (NetworkUtils.checkNetWork(getActivity()) == false) {
                        Toast.makeText(getActivity(), R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String urlM = NetworkUtils.returnUrl();
                    final String url = urlM + "/api/sms";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("phone", pphone);
                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("status") == 1) {
                                    getRaelCode = response.getString("code");
                                    Log.d("code===>>>>>>", "" + response.getString("code"));
                                    timer.start();
                                } else {
                                    Toast.makeText(getActivity(), R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(getActivity(), R.string.get_code_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.pl_right_phone, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_pcommit:
                pinput_user = (EditText) mRoot.findViewById(R.id.et_pinput_user);
                puser = pinput_user.getText().toString().trim();

                pinput_name = (EditText) mRoot.findViewById(R.id.et_pinput_name);
                pname = pinput_name.getText().toString().trim();

                pinput_phone = (EditText) mRoot.findViewById(R.id.et_pinput_num);
                pphone = pinput_phone.getText().toString().trim();

                pinput_phone_code = (EditText) mRoot.findViewById(R.id.et_pinput_phone_code);
                pcode = pinput_phone_code.getText().toString().trim();

                pinput_password = (EditText) mRoot.findViewById(R.id.et_pinput_new_password);
                ppassword = pinput_password.getText().toString().trim();

                if (pschoolName == null || pschoolName == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_school, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pgrade == null || pgrade == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_grade, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pclass == null || pclass == "") {
                    Toast.makeText(getActivity(), R.string.choose_child_class, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (puser.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_user_name, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pname.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_child_name, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (childUid.length() == 0) {
                    Toast.makeText(getActivity(), R.string.verify_child_again, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pphone.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_phone_number, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pcode.length() == 0) {
                    Toast.makeText(getActivity(), R.string.input_phone_verify_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pcode.equals(getRaelCode) == false) {
                    Toast.makeText(getActivity(), R.string.input_error_code, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (ppassword.length() < 8) {
                    Toast.makeText(getActivity(), R.string.input_new_password, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (NetworkUtils.checkNetWork(getActivity()) == false) {
                    Toast.makeText(getActivity(), R.string.isNotNetWork, Toast.LENGTH_SHORT).show();
                    break;
                }
                doPregist(pschoolName, pgrade, pclass, puser, childUid, pphone, ppassword);
                break;
        }
    }

    private void doPregist(String pschoolName, String pgrade, String pclass, String puser, String childUid, String pphone, String ppassword) {
        String pRegistUrl = NetworkUtils.returnUrl() + "/api/register";
        int type = 3;
        AsyncHttpClient pRegistClient = new AsyncHttpClient();
        RequestParams pRegistParam = new RequestParams();
        pRegistParam.put("usertype", type);
        pRegistParam.put("schoolname", pschoolName);
        pRegistParam.put("gradename", pgrade);
        pRegistParam.put("classname", pclass);
        pRegistParam.put("username", puser);
        pRegistParam.put("childrenid", childUid);
        pRegistParam.put("phone", pphone);
        pRegistParam.put("password", ppassword);
        Log.d("pRegistParam==>>", type + "," + pschoolName + "," + pgrade + "," + pclass + "," + puser + "," + childUid + ","
                + pphone + "," + ppassword);
        pRegistClient.post(pRegistUrl, pRegistParam, new JsonHttpResponseHandler() {
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

    private void doVerifyChild(String pschoolName, String pgrade, String pclass, String pname) {
        String urlVerify = NetworkUtils.returnUrl() + "/api/check-child";
        AsyncHttpClient verifyClient = new AsyncHttpClient();
        RequestParams verifyparam = new RequestParams();
        verifyparam.put("childGradeName", pgrade);
        verifyparam.put("childClassName", pclass);
        verifyparam.put("childSchoolName", pschoolName);
        verifyparam.put("childRealName", pname);
        Log.d("verifyparam==>>", pschoolName + "," + pgrade + "," + pclass + "," + pname);
        verifyClient.post(urlVerify, verifyparam, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.d("verifyChildResponse==>>>", response.toString());
                    int json = (int) response.get("status");
                    if (json == 1) {
                        childUid = response.getString("uid");
                        Toast.makeText(getActivity(), R.string.verify_child_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), response.getString("errorInfo"), Toast.LENGTH_LONG).show();
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
                pget_code.setEnabled(false);
            }
            pget_code.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            pget_code.setEnabled(true);
            pget_code.setText("获取验证码");
        }
    };

    //跳转并返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                pclass = bundle.getString("sclass");
                pchoose_class.setText(bundle.getString("sclass") + "班");
            }
        } else if (requestCode == 2) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                pschool = bundle.getString("school");
                pschoolName = bundle.getString("schoolname");
                Log.d("schoolName==>>>>", pschoolName);
                pchoose_school.setText(bundle.getString("school"));
            }
        }
    }
}


