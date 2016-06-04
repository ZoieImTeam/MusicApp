package com.example.administrator.musictest.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.musictest.Constant.Constants;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/4
 */
abstract class  NotifyReciverBroad extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case Constants.NOTIFY_PLAY:
                notifityPlay();
                break;
            case Constants.NOTIFY_NEXT:
                notifityPrev();
                break;
            case Constants.NOTIFY_PREV:// 上一首
                notifyPrev();
                break;
            default:
                break;
        }
    }

    //控制播放暂停
    protected abstract void notifityPlay();
    //下一首
    protected abstract void notifityPrev();
    //上一首
    protected abstract void notifyPrev();
}
