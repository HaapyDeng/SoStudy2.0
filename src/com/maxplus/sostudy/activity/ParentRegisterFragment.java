package com.maxplus.sostudy.activity;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.tools.RadioButtonAlertDialog;


public class ParentRegisterFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mRoot;
    // TODO: Rename and change types of parameters
    private TextView pchoose_school, pchoose_grade, pchoose_class, pget_code;
    private EditText pinput_phone, pinput_phone_code, pinput_password, pinput_name, pinput_user;
    private CheckBox pshow_num;
    private Button pcommit;
    private String pschool, pgrade, pclass, puser, pname, pphone, pcode, ppassword;

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
        pget_code = (TextView) mRoot.findViewById(R.id.tv_pgetCode);
        pget_code.setOnClickListener(this);
        pinput_password = (EditText) mRoot.findViewById(R.id.et_pinput_new_password);
        pshow_num = (CheckBox) mRoot.findViewById(R.id.pshow_password);
        pshow_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pshow_num.isChecked()) {
                    pinput_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pinput_password.setBackgroundResource(R.drawable.visible);
                } else {
                    pinput_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pinput_password.setBackgroundResource(R.drawable.unvisible);
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
            case R.id.tv_pgetCode:
                break;
            case R.id.btn_pcommit:
                break;
        }
    }

    //跳转并返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                pchoose_class.setText(bundle.getString("sclass") + "班");
            }
        } else if (requestCode == 2) {
            if (resultCode == 2) {
                Bundle bundle = data.getExtras();
                pchoose_school.setText(bundle.getString("school"));
            }
        }
    }
}


