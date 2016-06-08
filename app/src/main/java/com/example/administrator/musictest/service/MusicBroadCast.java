package com.example.administrator.musictest.service;

import android.content.Context;
import android.content.IntentFilter;

import com.example.administrator.musictest.Constant.Constants;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public class MusicBroadCast extends NotifyReciverBroad {

    private Context mContext;
    private ChangeViewImp impl;

    public MusicBroadCast(Context context)
    {
        this.mContext=context;
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constants.UPDATA_PLAY);
        filter.addAction(Constants.UPDATA_PAUSE);
        filter.addAction(Constants.NOTIFY_NEXT);
        filter.addAction(Constants.NOTIFY_PREV);
        mContext.registerReceiver(this,filter);
    }
    public void setImpl(ChangeViewImp impl) {
        this.impl = impl;
    }

    @Override
    protected void notifyPause() {
        impl.pauseplay();
    }

    @Override
    protected void notifityPlay() {
        impl.startplay();
    }

    @Override
    protected void notifyNext() {
        impl.nextMusic();
    }

    @Override
    protected void notifyPrevios() {
        impl.preMusic();
    }
}
