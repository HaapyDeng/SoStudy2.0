package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.maxplus.sostudy.application.MyApplication;
import com.maxplus.sostudy.chatting.utils.DialogCreator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

public class SetActivity extends Activity implements View.OnClickListener {
    private ImageButton back_Button;
    private TextView tv_clear1, tv_cahe, tv_loginOut;
    private Dialog mDialog;
    private LinearLayout tv_clear2;
    private Button cancel, commit;
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_ID = "groupId";

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
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.jmui_cancel_btn:
                                mDialog.cancel();
                                break;
                            case R.id.jmui_commit_btn:
                                String tageId;
                                long groupId;
                                List<Conversation> conversation = getConversation();
                                System.out.println(conversation);
                                for (int i = 0; i < conversation.size(); i++) {
                                    if (conversation.get(i).getType().toString().equals("single")) {
                                        System.out.println("==>>>11111????" + conversation.get(i).getType().equals("single"));
                                        UserInfo userInfo = (UserInfo) conversation.get(i).getTargetInfo();
                                        tageId = userInfo.getUserName();
                                        JMessageClient.deleteSingleConversation(tageId);
                                        System.out.println("==11>>>" + tageId);
                                    } else {
                                        System.out.println("==>>>22222????" + conversation.get(i).getType().equals("single"));
                                        GroupInfo groupInfo = (GroupInfo) conversation.get(i).getTargetInfo();
                                        groupId = groupInfo.getGroupID();
                                        JMessageClient.deleteGroupConversation(groupId);
                                        System.out.println("==22>>>" + groupId);
                                    }
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
                break;
            case R.id.tv_clear2:
                final Dialog dialog = new Dialog(SetActivity.this, R.style.jmui_default_dialog_style);
                View v = LayoutInflater.from(SetActivity.this).inflate(R.layout.dialog_clear_cache, null);
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
                break;
            case R.id.tv_loginOut:
                SharedPreferences mySharedPreferences = getSharedPreferences("user",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = mySharedPreferences.edit();
                edit.putString("token", "");
                edit.commit();
                JMessageClient.logout();
                Intent i = new Intent();
                i.setClass(SetActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }

    }

    private List<Conversation> getConversation() {
        List<Conversation> conversation;
        conversation = JMessageClient.getConversationList();
        Log.d("conversation==???.>>>", "" + conversation);
        return conversation;
    }

}
