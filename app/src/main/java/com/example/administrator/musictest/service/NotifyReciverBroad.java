package com.example.administrator.musictest.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.musictest.Constant.Constants;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/4
 */
public abstract class  NotifyReciverBroad extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Constants.UPDATA_PLAY:
                notifityPlay();
                break;
            case Constants.NOTIFY_NEXT:
                notifyNext();
                break;
            case Constants.NOTIFY_PREV:// 上一首
                notifyPrevios();
                break;
            case Constants.UPDATA_PAUSE:
                notifyPause();
                break;
            default:
                break;
        }
    }

    //暂停
    protected abstract void notifyPause();
    //控制播放
    protected abstract void notifityPlay();
    //下一首
    protected abstract void notifyNext();
    //上一首
    protected abstract void notifyPrevios();
}
