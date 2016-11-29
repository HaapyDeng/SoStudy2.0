package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.chatting.utils.DialogCreator;

import cn.jpush.im.android.api.model.Conversation;

public class SetActivity extends Activity implements View.OnClickListener {
    private ImageButton back_Button;
    private TextView tv_clear1, tv_cahe, tv_loginOut;
    private String cahe;
    private Dialog mDialog, mDialog2;
    private LinearLayout tv_clear2;
    private Button cancel, commit;

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
        tv_clear2 = (LinearLayout) findViewById(R.id.tv_clear2);
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
                final Dialog dialog = new Dialog(SetActivity.this, R.style.jmui_default_dialog_style);
                View v = LayoutInflater.from(SetActivity.this).inflate(R.layout.dialog_clear_cache, null);
//                dialog.setContentView(R.layout.dialog_clear_cache);
                dialog.setContentView(v);
                dialog.getWindow().setLayout((int) (0.8 * 720), WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
                cancel = (Button) v.findViewById(R.id.cache_cancel_btn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                commit = (Button) v.findViewById(R.id.cache_commit_btn);
                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_cahe.setText("0M");
                        dialog.cancel();
                    }
                });
//                commit.setOnClickListener(listener2);
//                View.OnClickListener listener2 = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        switch (view.getId()) {
//                            case R.id.cache_cancel_btn:
//                                dialog.cancel();
//                                break;
//                            case R.id.cache_commit_btn:
//                                tv_cahe.setText("0M");
//                                dialog.cancel();
//                        }
//                    }
//                };


//                View.OnClickListener listener2 = new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        switch (view.getId()) {
//                            case R.id.jmui_cancel_btn:
//                                mDialog2.cancel();
//                                break;
//                            case R.id.jmui_commit_btn:
//                                mDialog2.cancel();
//                                break;
//                        }
//                    }
//                };
//                int mWidth2;
//                DisplayMetrics dm2 = new DisplayMetrics();
//                mWidth2 = dm2.widthPixels;
//                Log.d("mWidth==???>>>>>>", "" + mWidth2);
//                mDialog2 = DialogCreator.createDeleteMessageDialog(SetActivity.this, listener2);
//                mDialog2.getWindow().setLayout((int) (0.8 * 720), WindowManager.LayoutParams.WRAP_CONTENT);
//                mDialog2.show();
                break;
            case R.id.tv_loginOut:
                Intent i = new Intent();
                i.setClass(SetActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }

    }
}
