package com.example.administrator.musictest.app;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.activeandroid.ActiveAndroid;
import com.example.administrator.musictest.R;

import org.srr.dev.base.BaseApplication;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */
public class MusicApp extends BaseApplication {
    Context mContext;
    protected NotificationCompat.Builder mBuilder;
    static final int mNotificationCompatNo = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initBar();
        ActiveAndroid.initialize(mContext);
    }

    private void initBar() {
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("测试测试测试")
                .setContentText("内容填充测试");
        mBuilder.setAutoCancel(true);//设置后点击消失
        mBuilder.setOngoing(true);//滑动不消失
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationCompatNo, mBuilder.build());
    }
}
