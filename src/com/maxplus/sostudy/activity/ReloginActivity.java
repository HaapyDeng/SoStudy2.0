package com.maxplus.sostudy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.controller.ReloginController;
import com.maxplus.sostudy.chatting.utils.BitmapLoader;
import com.maxplus.sostudy.chatting.utils.SharePreferenceManager;
import com.maxplus.sostudy.view.ReloginView;

public class ReloginActivity extends BaseActivity {

    private ReloginView mReloginView;
    private ReloginController mReloginController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_login);
        mReloginView = (ReloginView) findViewById(R.id.relogin_view);
        mReloginView.initModule();
        fillContent();
        mReloginView.setListeners(mReloginController);
        mReloginView.setListener(mReloginController);
    }

    private void fillContent() {
        String userName = SharePreferenceManager.getCachedUsername();
        String userAvatarPath = SharePreferenceManager.getCachedAvatarPath();
        Bitmap bitmap = BitmapLoader.getBitmapFromFile(userAvatarPath, mAvatarSize, mAvatarSize);
        if (bitmap != null) {
            mReloginView.showAvatar(bitmap);
        }
        mReloginView.setUserName(userName);
        mReloginController = new ReloginController(mReloginView, this, userName);
        SharePreferenceManager.setCachedUsername(userName);
        SharePreferenceManager.setCachedAvatarPath(userAvatarPath);
    }


    public void startRelogin() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void startSwitchUser() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.putExtra("fromSwitch", true);
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }


    public void startRegisterActivity() {
        Intent intent = new Intent();
        intent.setClass(this, StudentRegistFragment.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
