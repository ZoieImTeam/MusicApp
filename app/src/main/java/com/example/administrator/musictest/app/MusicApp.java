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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initBar() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.item_nitification);
        mRemoteViews.setImageViewResource(R.id.notifi_circle, R.mipmap.ic_launcher);
        mRemoteViews.setTextViewText(R.id.notifi_singer,"王八蛋");
        mRemoteViews.setTextViewText(R.id.notifi_music_name,"全英雄联盟最骚的骚猪");


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        mBuilder.setContent(mRemoteViews)
                .setContentIntent(pendingIntent)
//                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
//                .setContentTitle("测试")
//                .setContentText("测试")
                .setSmallIcon(R.mipmap.minilyric_pause_button_press);
        Notification notify = mBuilder.build();
        notify.contentView=mRemoteViews;
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(mNotificationCompatNo, mBuilder.build());

    }
}
