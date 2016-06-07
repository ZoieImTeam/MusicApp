package com.example.administrator.musictest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.example.administrator.musictest.app.MusicApp;
import com.example.administrator.musictest.musicui.home.BaseBottomFrag;
import com.example.administrator.musictest.musicui.home.LocalFragment;
import com.example.administrator.musictest.viewpag.ButtonFragment;

import org.srr.dev.base.BaseActivity;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/2
 */
public class MainActivity extends BaseActivity {

    private View mVw_local;
    private View mVw__library;
    private View mVw_ringing;
    private View mVw_vibration;

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
        supportFragmentManager.beginTransaction().add(R.id.flyt_base_view2, BaseBottomFrag.newInstance()).commit();
        RelativeLayout rlyt_local = (RelativeLayout) findViewById(R.id.rlyt_local);
        rlyt_local.setOnClickListener(this);
        RelativeLayout rlyt_library = (RelativeLayout) findViewById(R.id.rlyt_library);
        rlyt_library.setOnClickListener(this);
        RelativeLayout rlyt_ringing = (RelativeLayout) findViewById(R.id.rlyt_ringing);
        rlyt_ringing.setOnClickListener(this);
        RelativeLayout rlyt_vibration = (RelativeLayout) findViewById(R.id.rlyt_vibration);
        rlyt_vibration.setOnClickListener(this);
        mVw_local = findViewById(R.id.vw_local);
        mVw__library = findViewById(R.id.vw__library);
        mVw_ringing = findViewById(R.id.vw_ringing);
        mVw_vibration = findViewById(R.id.vw_vibration);
        setViewVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    private void setViewVisibility(int LocalVisibility, int LibraryVisibility, int RingingVisibility, int VibrationVisibility) {
        mVw_local.setVisibility(LocalVisibility);
        mVw__library.setVisibility(LibraryVisibility);
        mVw_ringing.setVisibility(RingingVisibility);
        mVw_vibration.setVisibility(VibrationVisibility);
    }

    @Override
    public void initData() {
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO  本地
            case R.id.rlyt_local:
                setViewVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
                break;

            //TODO  乐库
            case R.id.rlyt_library:
                setViewVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE);
                break;

            //TODO  彩铃
            case R.id.rlyt_ringing:
                setViewVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE);
                break;

            //TODO  振铃
            case R.id.rlyt_vibration:
                setViewVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE);
                break;
        }
        super.onClick(v);
    }
}
