package com.example.administrator.musictest.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.musictest.entity.MusicEntity;

import java.util.ArrayList;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/6
 */
public class MusicPlayHelper implements ServiceConnection {
    private Activity mContext;
    private ServiceConnection mConnection;
    protected MusicService.MusicBinder mMusicBinder;
    private Intent mIntent;
    private Boolean mIsInit=false;

    public MusicPlayHelper(Activity context) {
        this.mContext = context;
    }

    /**
     * 初始化服务
     */
    public void initPlayerService() {
        mConnection = this;
        mIntent = new Intent(mContext, MusicService.class);
        mContext.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 播放音乐
     */
    public void play() {
        //如果helper类未初始化过
        if (!mIsInit) {
            initPlayerService();
        }
        else if (!mMusicBinder.isStart()) {
            mMusicBinder.playMusic("http://pianke.file.alimmdn.com/upload/20160508/b338fe51953d91a7e8c5e57fc9155b0f.MP3");
        } else {
            pause();
        }
    }

    /**
     * 停止
     */
    private void pause() {
        mMusicBinder.pauseMusic();
    }

    /**
     * 下一曲
     */
    public void nextMusic() {
        mMusicBinder.nextMusic();
    }

    /**
     * 上一曲
     */
    public void lastMusic() {
        mMusicBinder.befoMusic();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d("MusicPlayHelper", "bind");
        mMusicBinder = (MusicService.MusicBinder) service;
        mIsInit=true;
        play();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
