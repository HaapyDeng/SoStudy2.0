package com.maxplus.sostudy.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;
import com.maxplus.sostudy.application.MyApplication;
import com.maxplus.sostudy.chatting.utils.BitmapLoader;
import com.maxplus.sostudy.chatting.utils.DialogCreator;
import com.maxplus.sostudy.chatting.utils.FileHelper;
import com.maxplus.sostudy.chatting.utils.HandleResponseCode;
import com.maxplus.sostudy.chatting.utils.SharePreferenceManager;
import com.maxplus.sostudy.controller.MeController;
import com.maxplus.sostudy.view.MeView;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class MeFragment extends BaseFragment {

    private static final String TAG = MeFragment.class.getSimpleName();

    private View mRootView;
    private MeView mMeView;
    private MeController mMeController;
    private Context mContext;
    private String mPath, oldNickname;
    private boolean mIsShowAvatar = false;
    private boolean mIsGetAvatar = false;
    private ImageView nick_name;
    private TextView tv_nickname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_me,
                (ViewGroup) getActivity().findViewById(R.id.main_view), false);
        mMeView = (MeView) mRootView.findViewById(R.id.me_view);
        mMeView.initModule(mDensity, mWidth);
        mMeController = new MeController(mMeView, this, mWidth);
        mMeView.setListeners(mMeController);
        tv_nickname = (TextView) mRootView.findViewById(R.id.nick_name_tv);
        nick_name = (ImageView) mRootView.findViewById(R.id.iv_edit_nickname);
        nick_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.iv_edit_nickname:
                        final Dialog dialog = new Dialog(mContext, R.style.jmui_default_dialog_style);
                        final View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.dialog_set_nickname, null);
                        dialog.setContentView(view);
                        dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        final EditText et_nickname = (EditText) view.findViewById(R.id.et_nickname);
                        final Button cancel = (Button) view.findViewById(R.id.nickname_cancel_btn);
                        final Button commit = (Button) view.findViewById(R.id.nickname_commit_btn);

                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v1) {
                                switch (v1.getId()) {
                                    case R.id.nickname_cancel_btn:
                                        dialog.cancel();
                                        break;
                                    case R.id.nickname_commit_btn:
                                        final String nickname = et_nickname.getText().toString();
                                        if (nickname.length() == 0) {
                                            dialog.cancel();
                                        } else {
                                            oldNickname = tv_nickname.getText().toString();
                                            if (!oldNickname.equals(nickname)) {
                                                UserInfo myUserInfo = JMessageClient.getMyInfo();
                                                myUserInfo.setNickname(nickname);
                                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, myUserInfo, new BasicCallback() {
                                                    @Override
                                                    public void gotResult(final int status, final String desc) {
                                                        dialog.dismiss();
                                                        if (status == 0) {
                                                            tv_nickname.setText(nickname);
                                                            Toast.makeText(getActivity(), getResources().getString(R.string.modify_success_toast),
                                                                    Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                        } else {
                                                            HandleResponseCode.onHandle(getActivity(), status, false);
                                                        }
                                                    }
                                                });
                                            }
                                            dialog.cancel();
                                        }
                                }
                            }
                        };
                        cancel.setOnClickListener(listener);
                        commit.setOnClickListener(listener);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        if (!mIsShowAvatar) {
            UserInfo myInfo = JMessageClient.getMyInfo();
            if (myInfo != null) {
                if (!TextUtils.isEmpty(myInfo.getAvatar())) {
                    myInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int status, String desc, Bitmap bitmap) {
                            if (status == 0) {
                                mMeView.showPhoto(bitmap);
                                mIsShowAvatar = true;
                            } else {
                                HandleResponseCode.onHandle(mContext, status, false);
                            }
                        }
                    });
                }
                mMeView.showNickName(myInfo.getNickname());
                //用户由于某种原因导致登出,跳转到重新登录界面
            } else {
                Intent intent = new Intent();
                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
            }
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //退出登录
    public void Logout() {
        // TODO Auto-generated method stub
        final Intent intent = new Intent();
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            intent.putExtra("userName", info.getUserName());
            File file = info.getAvatarFile();
            if (file != null && file.isFile()) {
                intent.putExtra("avatarFilePath", file.getAbsolutePath());
            } else {
                String path = FileHelper.getUserAvatarPath(info.getUserName());
                file = new File(path);
                if (file.exists()) {
                    intent.putExtra("avatarFilePath", file.getAbsolutePath());
                }
            }
            SharePreferenceManager.setCachedUsername(info.getUserName());
            SharePreferenceManager.setCachedAvatarPath(file.getAbsolutePath());
            JMessageClient.logout();
            intent.setClass(mContext, LoginActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "user info is null!");
        }
    }

    public void StartSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), SetActivity.class);
        startActivity(intent);
    }

    public void startMeInfoActivity() {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), MeInfoActivity.class);
        startActivityForResult(intent, MyApplication.REQUEST_CODE_ME_INFO);
    }

    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    //照相
    public void takePhoto() {
        if (FileHelper.isSdCardExist()) {
            mPath = FileHelper.createAvatarPath(JMessageClient.getMyInfo().getUserName());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
            try {
                getActivity().startActivityForResult(intent, MyApplication.REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                Toast.makeText(this.getActivity(), mContext.getString(R.string.camera_not_prepared), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.getActivity(), mContext.getString(R.string.jmui_sdcard_not_exist_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPhotoPath() {
        return mPath;
    }

    //选择本地图片
    public void selectImageFromLocal() {
        if (FileHelper.isSdCardExist()) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            getActivity().startActivityForResult(intent, MyApplication.REQUEST_CODE_SELECT_PICTURE);
        } else {
            Toast.makeText(this.getActivity(), mContext.getString(R.string.jmui_sdcard_not_exist_toast),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void loadUserAvatar(String path) {
        if (null != mMeView) {
            mIsGetAvatar = true;
            mMeView.showPhoto(path);
        }
    }

    //预览头像
    public void startBrowserAvatar() {
        final UserInfo myInfo = JMessageClient.getMyInfo();
        //如果本地保存了图片，直接加载，否则下载
        if (mIsGetAvatar) {
            String path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            File file = new File(path);
            if (file.exists()) {
                Intent intent = new Intent();
                intent.putExtra("browserAvatar", true);
                intent.putExtra("avatarPath", path);
                intent.setClass(mContext, BrowserViewPagerActivity.class);
                startActivity(intent);
            } else if (!TextUtils.isEmpty(myInfo.getAvatar())) {
                getBigAvatar(myInfo);
            }
        } else if (!TextUtils.isEmpty(myInfo.getAvatar())) {
            getBigAvatar(myInfo);
        }
    }

    private void getBigAvatar(final UserInfo myInfo) {
        final Dialog dialog = DialogCreator.createLoadingDialog(mContext,
                mContext.getString(R.string.jmui_loading));
        dialog.show();
        myInfo.getBigAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int status, String desc, Bitmap bitmap) {
                if (status == 0) {
                    mIsGetAvatar = true;
                    String path = BitmapLoader.saveBitmapToLocal(bitmap, mContext, myInfo.getUserName());
                    Intent intent = new Intent();
                    intent.putExtra("browserAvatar", true);
                    intent.putExtra("avatarPath", path);
                    intent.setClass(mContext, BrowserViewPagerActivity.class);
                    startActivity(intent);
                } else {
                    HandleResponseCode.onHandle(mContext, status, false);
                }
                dialog.dismiss();
            }
        });
    }

    public void refreshNickname(String newName) {
        mMeView.showNickName(newName);
    }
}
