package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.chatting.utils.DialogCreator;

import cn.jpush.android.JPushConfig;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

public class SetActivity extends Activity implements View.OnClickListener {
    private ImageButton back_Button;
    private TextView tv_clear1, tv_clear2, tv_cahe, tv_loginOut;
    private String cahe;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initViews();
    }

    private void initViews() {
        back_Button = (ImageButton) findViewById(R.id.back_Button);
        back_Button.setOnClickListener(this);
        tv_clear1 = (TextView) findViewById(R.id.tv_clear1);
        tv_clear1.setOnClickListener(this);
        tv_clear2 = (TextView) findViewById(R.id.tv_clear2);
        tv_clear2.setOnClickListener(this);
        tv_cahe = (TextView) findViewById(R.id.tv_cahe);
        tv_loginOut = (TextView) findViewById(R.id.tv_loginOut);
        tv_loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_Button:
                finish();
                break;
            case R.id.tv_clear1:
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.jmui_cancel_btn:
                                mDialog.cancel();
                                break;
                            case R.id.jmui_commit_btn:
                                Conversation conv = null;
//                                conv.getAllMessage();
//                                Log.d("conv===>>>>>>", "" + conv);
                                if (conv != null) {
                                    conv.deleteAllMessage();
                                }
                                mDialog.cancel();
                                break;
                        }
                    }
                };
                int mWidth;
                DisplayMetrics dm = new DisplayMetrics();
                mWidth = dm.widthPixels;
                Log.d("mWidth==???>>>>>>", "" + mWidth);
                mDialog = DialogCreator.createDeleteMessageDialog(SetActivity.this, listener);
                mDialog.getWindow().setLayout((int) (0.8 * 720), WindowManager.LayoutParams.WRAP_CONTENT);
                mDialog.show();
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        Conversation conv = null;
//                        conv.getAllMessage();
//                        if (conv != null) {
//                            conv.deleteAllMessage();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dialog.dismiss();
//                    }
//                });
                break;
            case R.id.tv_clear2:
                break;
            case R.id.tv_loginOut:
                break;
        }

    }
}
