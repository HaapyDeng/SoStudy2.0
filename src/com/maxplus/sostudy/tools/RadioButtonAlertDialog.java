package com.maxplus.sostudy.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;

/**
 * Created by djz on 2016/10/18 0018.
 */
public class RadioButtonAlertDialog extends Dialog implements View.OnClickListener {
    private RadioButton ck, ck_1, ck_2, ck_3, ck_4, ck_5, ck_6, ck_7, ck_8, ck_9, ck_10, ck_11, ck_12;
    String grade, ck1, ck2, ck3, ck4, ck5, ck6, ck7, ck8, ck9, ck10, ck11, ck12;
    private TextView negativeButton, positiveButton;

    public RadioButtonAlertDialog(Context context) {
        super(context);
        setAlertDialog();
    }

    private void setAlertDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialogchoose, null);
        ck_1 = (RadioButton) mView.findViewById(R.id.ck_1);
        ck_1.setOnClickListener(this);
        ck1 = ck_1.getText().toString();
        ck_2 = (RadioButton) mView.findViewById(R.id.ck_2);
        ck_2.setOnClickListener(this);
        ck2 = ck_2.getText().toString();
        ck_3 = (RadioButton) mView.findViewById(R.id.ck_3);
        ck_3.setOnClickListener(this);
        ck3 = ck_3.getText().toString();
        ck_4 = (RadioButton) mView.findViewById(R.id.ck_4);
        ck_4.setOnClickListener(this);
        ck4 = ck_4.getText().toString();
        ck_5 = (RadioButton) mView.findViewById(R.id.ck_5);
        ck_5.setOnClickListener(this);
        ck5 = ck_5.getText().toString();
        ck_6 = (RadioButton) mView.findViewById(R.id.ck_6);
        ck_6.setOnClickListener(this);
        ck6 = ck_6.getText().toString();
        ck_7 = (RadioButton) mView.findViewById(R.id.ck_7);
        ck_7.setOnClickListener(this);
        ck7 = ck_7.getText().toString();
        ck_8 = (RadioButton) mView.findViewById(R.id.ck_8);
        ck_8.setOnClickListener(this);
        ck8 = ck_8.getText().toString();
        ck_9 = (RadioButton) mView.findViewById(R.id.ck_9);
        ck_9.setOnClickListener(this);
        ck9 = ck_9.getText().toString();
        ck_10 = (RadioButton) mView.findViewById(R.id.ck_10);
        ck_10.setOnClickListener(this);
        ck10 = ck_10.getText().toString();
        ck_11 = (RadioButton) mView.findViewById(R.id.ck_11);
        ck_11.setOnClickListener(this);
        ck11 = ck_11.getText().toString();
        ck_12 = (RadioButton) mView.findViewById(R.id.ck_12);
        ck_12.setOnClickListener(this);
        ck12 = ck_12.getText().toString();
        negativeButton = (TextView) mView.findViewById(R.id.negativeButton);

        positiveButton = (TextView) mView.findViewById(R.id.positiveButton);

        super.setContentView(mView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ck_1:
//                grade = ck1;
//                break;
//            case R.id.ck_2:
//                grade = ck2;
//                break;
//            case R.id.ck_3:
//                grade = ck3;
//                break;
//            case R.id.ck_4:
//                grade = ck4;
//                break;
//            case R.id.ck_5:
//                grade = ck5;
//                break;
//            case R.id.ck_6:
//                grade = ck6;
//                break;
//            case R.id.ck_7:
//                grade = ck7;
//                break;
//            case R.id.ck_8:
//                grade = ck8;
//                break;
//            case R.id.ck_9:
//                grade = ck9;
//                break;
//            case R.id.ck_10:
//                grade = ck10;
//                break;
//            case R.id.ck_11:
//                grade = ck11;
//                break;
//            case R.id.ck_12:
//                grade = ck12;
//                break;
            case R.id.ck_1:
                ck = ck_1;
                break;
            case R.id.ck_2:
                ck = ck_2;
                break;
            case R.id.ck_3:
                ck = ck_3;
                break;
            case R.id.ck_4:
                ck = ck_4;
                break;
            case R.id.ck_5:
                ck = ck_5;
                break;
            case R.id.ck_6:
                ck = ck_6;
                break;
            case R.id.ck_7:
                ck = ck_7;
                break;
            case R.id.ck_8:
                ck = ck_8;
                break;
            case R.id.ck_9:
                ck = ck_9;
                break;
            case R.id.ck_10:
                ck = ck_10;
                break;
            case R.id.ck_11:
                ck = ck_11;
                break;
            case R.id.ck_12:
                ck = ck_12;
                break;
        }
    }

    public View retunGrade() {
        return ck;
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        positiveButton.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);
    }


}
