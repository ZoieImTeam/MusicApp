package com.example.administrator.musictest;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.musictest.musicui.home.LocalFragment;
import com.example.administrator.musictest.viewpag.ButtonFragment;

import org.srr.dev.base.BaseActivity;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/2
 */
public class MainActivity extends BaseActivity {


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
        supportFragmentManager.beginTransaction().add(R.id.flyt_base_view, LocalFragment.newInstance()).commit();
    }


    @Override
    public void initData() {
    }

    @Override
    public void refreshData() {

    }
}
