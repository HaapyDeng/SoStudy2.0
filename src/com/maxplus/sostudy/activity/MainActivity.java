package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

import com.maxplus.sostudy.R;

import com.maxplus.sostudy.application.MyApplication;
import com.maxplus.sostudy.controller.MainController;
import com.maxplus.sostudy.chatting.utils.FileHelper;
import com.maxplus.sostudy.chatting.utils.SharePreferenceManager;
import com.maxplus.sostudy.view.MainView;

public class MainActivity extends FragmentActivity {
    private MainController mMainController;
    private MainView mMainView;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainView = (MainView) findViewById(R.id.main_view);
        mMainView.initModule();
        mMainController = new MainController(mMainView, this);

        mMainView.setOnClickListener(mMainController);
        mMainView.setOnPageChangeListener(mMainController);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        //第一次登录需要设置昵称
        boolean flag = SharePreferenceManager.getCachedFixProfileFlag();
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
            Intent intent = new Intent();
            if (null != SharePreferenceManager.getCachedUsername()) {
                intent.putExtra("userName", SharePreferenceManager.getCachedUsername());
                intent.putExtra("avatarFilePath", SharePreferenceManager.getCachedAvatarPath());
                intent.setClass(this, LoginActivity.class);
            } else {
                intent.setClass(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            MyApplication.setPicturePath(myInfo.getAppKey());
            if (TextUtils.isEmpty(myInfo.getNickname()) && flag) {
                Intent intent = new Intent();
                intent.setClass(this, FixProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }
        mMainController.sortConvList();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public FragmentManager getSupportFragmentManger() {
        // TODO Auto-generated method stub
        return getSupportFragmentManager();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == MyApplication.REQUEST_CODE_TAKE_PHOTO) {
            String path = mMainController.getPhotoPath();
            if (path != null) {
                File file = new File(path);
                if (file.isFile()) {
                    mUri = Uri.fromFile(file);
                    //拍照后直接进行裁剪
//                mMainController.cropRawPhoto(mUri);
                    Intent intent = new Intent();
                    intent.putExtra("filePath", mUri.getPath());
                    intent.setClass(this, CropImageActivity.class);
                    startActivityForResult(intent, MyApplication.REQUEST_CODE_CROP_PICTURE);
                }
            }
        } else if (requestCode == MyApplication.REQUEST_CODE_SELECT_PICTURE) {
            if (data != null) {
                Uri selectedImg = data.getData();
                if (selectedImg != null) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver()
                            .query(selectedImg, filePathColumn, null, null, null);
                    if (null == cursor) {
                        String path = selectedImg.getPath();
                        File file = new File(path);
                        if (file.isFile()) {
                            copyAndCrop(file);
                            return;
                        } else {
                            Toast.makeText(this, this.getString(R.string.picture_not_found),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (!cursor.moveToFirst()) {
                        Toast.makeText(this, this.getString(R.string.picture_not_found),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);
                    if (path != null) {
                        File file = new File(path);
                        if (!file.isFile()) {
                            Toast.makeText(this, this.getString(R.string.picture_not_found),
                                    Toast.LENGTH_SHORT).show();
                            cursor.close();
                        } else {
                            //如果是选择本地图片进行头像设置，复制到临时文件，并进行裁剪
                            copyAndCrop(file);
                            cursor.close();
                        }
                    }
                }
            }
        } else if (requestCode == MyApplication.REQUEST_CODE_CROP_PICTURE) {
//            mMainController.uploadUserAvatar(mUri.getPath());
            String path = data.getStringExtra("filePath");
            if (path != null) {
                mMainController.uploadUserAvatar(path);
            }
        } else if (resultCode == MyApplication.RESULT_CODE_ME_INFO) {
            String newName = data.getStringExtra("newName");
            if (!TextUtils.isEmpty(newName)) {
                mMainController.refreshNickname(newName);
            }
        }
    }

    /**
     * 复制后裁剪文件
     *
     * @param file 要复制的文件
     */
    private void copyAndCrop(final File file) {
        FileHelper.getInstance().copyAndCrop(file, this, new FileHelper.CopyFileCallback() {
            @Override
            public void copyCallback(Uri uri) {
                mUri = uri;
//                mMainController.cropRawPhoto(mUri);
                Intent intent = new Intent();
                intent.putExtra("filePath", mUri.getPath());
                intent.setClass(MainActivity.this, CropImageActivity.class);
                startActivityForResult(intent, MyApplication.REQUEST_CODE_CROP_PICTURE);
            }
        });
    }
//点击返回键弹出确定窗口 选择退出

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Button cancel, commit;
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
//具体的操作代码
            final Dialog dialog = new Dialog(MainActivity.this, R.style.jmui_default_dialog_style);
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_logout, null);
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
                    //IM和app的退出
                    JMessageClient.logout();
                    finish();
                    dialog.cancel();
                }
            });
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
