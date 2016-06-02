package com.example.administrator.musictest;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.musictest.viewpag.ButtonFragment;

import org.srr.dev.base.BaseActivity;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/2
 */
public class MainActivity extends BaseActivity {

    protected NotificationCompat.Builder mBuilder;
    static final int mNotificationCompatNo=1;

    @Override
    protected void initGetIntent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.flyt_base_view, ButtonFragment.newInstance()).commit();
        mBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("测试测试测试")
                .setContentText("内容填充测试");
        mBuilder.setAutoCancel(true);//设置后点击消失
        mBuilder.setOngoing(true);//滑动不消失
        NotificationManager mNotifyMgr= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationCompatNo, mBuilder.build());

    }

    @Override
    public void initData() {

    }

    @Override
    public void refreshData() {

    }
}
