package com.example.administrator.musictest.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.example.administrator.musictest.Constant.Constants;
import com.example.administrator.musictest.MainActivity;
import com.example.administrator.musictest.R;
import com.example.administrator.musictest.entity.MusicEntity;

import org.srr.dev.util.IML;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */
public class MusicService extends Service {


    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private int mPlaymodel;
    private ArrayList<MusicEntity> mMusicList = new ArrayList();
    private boolean isPrepared;
    private Notification notification;
    private NotificationManager mManager;
    private MusicBinder binder;
    private MusicEntity mMusicEntity;

    @Override
    public IBinder onBind(Intent intent) {
        binder = new MusicBinder();
        return binder;
    }


    public void playMusic(String MusicURL) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(MusicURL);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    isPrepared = true;
                    Intent intent = new Intent(Constants.REFRESH_UI);
                    sendBroadcast(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 如果模式不为单曲循环就自动下一曲
                if (mPlaymodel != Constants.MODEL_SINGEL) {
                    // 下一首
                    musicNextIndex();
                }
                playMusic(mMusicEntity.getUrl());
            }
        });
        NotityReciver receiver = new NotityReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.NOTIFY_PLAY);
        filter.addAction(Constants.NOTIFY_NEXT);
        filter.addAction(Constants.NOTIFY_PREV);
        registerReceiver(receiver, filter);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotify() {
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.item_nitification);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        builder.setTicker("MusicPlayer")
                .setOngoing(true)
                .setSmallIcon(R.drawable.bg_dialog).setContent(views)
                .setContentIntent(pendingIntent);
        notification = builder.build();
        notification.bigContentView = views;
        initNotify(notification);

        //播放
        Intent playintent = new Intent(Constants.NOTIFY_PLAY);
        PendingIntent PlayPending = PendingIntent.getBroadcast(MusicService.this, 0, playintent, 0);
        notification.bigContentView.setOnClickPendingIntent(R.id.notifi_start, PlayPending);
        //下一首
        Intent nextintent = new Intent(Constants.NOTIFY_NEXT);
        PendingIntent NextPending = PendingIntent.getBroadcast(MusicService.this, 0, nextintent, 0);
        notification.bigContentView.setOnClickPendingIntent(R.id.notifi_next, NextPending);
        //上一首
        Intent preintent = new Intent(Constants.NOTIFY_PREV);
        PendingIntent PreIntent = PendingIntent.getBroadcast(MusicService.this, 0, preintent, 0);
        notification.bigContentView.setOnClickPendingIntent(R.id.notifi_last, PreIntent);
        mManager.notify(Constants.NOTIFY_ID, notification);
    }

    @SuppressLint("NewApi")
    //初始化通知
    private void initNotify(Notification notification) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_nitification, null);
        if (isPrepared) {
            //通过position获得实体类对象
            mMusicEntity = mMusicList.get(mPosition);
            if (mMusicEntity.getImag() != null) {
                ImageView i = (ImageView) v.findViewById(R.id.notifi_circle);
                IML.load(i, mMusicEntity.getImag());
                //  notification.bigContentView.setImageViewBitmap(R.id.notifi_circle , musicEntity.getImag());
            } else {
                notification.bigContentView.setImageViewResource(R.id.notifi_circle, R.mipmap.ic_launcher);
            }
            notification.bigContentView.setTextViewText(R.id.notifi_music_name,
                    mMusicEntity.getName() + "-" + mMusicEntity.getSingerName());
            notification.bigContentView.setImageViewResource(R.id.notifi_start, R.mipmap.minilyric_pause_button_press);
            mManager.notify(Constants.NOTIFY_ID, notification);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("MusicService", "Start Service");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MusicService", "stop Service");
    }

    // 播放下一首
    public void musicNextIndex() {
        switch (mPlaymodel) {
            case Constants.MODEL_ORDER:
                if (mPosition < mMusicList.size() - 1) {
                    mPosition++;
                } else {
                    mPosition = 0;
                }
                break;
            case Constants.MODEL_RANDOM:
                Random random = new Random();
                mPosition = random.nextInt(mMusicList.size() - 1);
                break;
            case Constants.MODEL_SINGEL:
                if (mPosition < mMusicList.size() - 1) {
                    mPosition++;
                } else {
                    mPosition = 0;
                }
                break;
            default:
                break;
        }
    }

    // 播放上一首
    public void musicBeforIndex() {
        switch (mPlaymodel) {
            case Constants.MODEL_ORDER:
                mPosition--;
                if (mPosition < 0) {
                    mPosition = mMusicList.size() - 1;
                }
                break;
            case Constants.MODEL_RANDOM:
                Random random = new Random();
                mPosition = random.nextInt(mMusicList.size() - 1);
                break;
            case Constants.MODEL_SINGEL:
                if (mPosition < 0) {
                    mPosition = mMusicList.size() - 1;
                }
                break;

            default:
                break;
        }
    }

    class MusicBinder extends Binder {
        public void playMusic(String MusicURL) {
            MusicService.this.playMusic(MusicURL);
        }


        //暂停音乐
        @SuppressLint("NewApi")
        public void pauseMusic() {
            mMediaPlayer.pause();
            //发送广播通知通知栏更新控件为暂停
            Intent intent = new Intent(Constants.UPDATA_PAUSE);
            sendBroadcast(intent);
            notification.bigContentView.setImageViewResource(R.id.notifi_start,
                    R.mipmap.minilyric_play_button_press);
            mManager.notify(Constants.NOTIFY_ID, notification);
        }

        //播放音乐
        @SuppressLint("NewApi")
        public void start() {
            mMediaPlayer.start();
            //发送广播通知通知栏更新控件为播放
            Intent intent = new Intent(Constants.UPDATA_PLAY);
            sendBroadcast(intent);
            notification.bigContentView.setImageViewResource(R.id.notifi_start,
                    R.mipmap.minilyric_play_button_press);
            mManager.notify(Constants.NOTIFY_ID, notification);
        }

        //判断音乐是否播放
        public boolean isStart() {
            boolean playing = mMediaPlayer.isPlaying();
            return playing;
        }

        //获取音乐进度
        public int getProgress() {
            return mMediaPlayer.getCurrentPosition() / 1000;
        }

        //判断音乐是否准备完毕
        public boolean isPrepared() {
            return isPrepared;
        }

        // 从拖拽处播放音乐
        public void seekToplay(int progress) {
            mMediaPlayer.seekTo(progress * 1000);
        }

        // 获得正在播放的音乐的下标
        public int getMusicIndex() {
            return mPosition;
        }

        //下一首音乐
        public void nextMusic() {
            musicNextIndex();
            MusicService.this.playMusic(mMusicList.get(mPosition).getUrl());
        }

        //上一首音乐
        public void befoMusic() {
            musicBeforIndex();
            MusicService.this.playMusic(mMusicList.get(mPosition).getUrl());
        }

        // 接收界面传递过来的播放模式
        public void getModel(int model) {
            MusicService.this.mPlaymodel = model;
        }

        //获得音乐播放模式
        public int getModel() {
            return mPlaymodel;
        }

        //获得音乐实体类对象
        public MusicEntity sendMusicInfo() {
            return mMusicList.get(mPosition);
        }

        //获得音乐列表容器
        public ArrayList<MusicEntity> getPlaylist() {
            return mMusicList;
        }
    }

    class NotityReciver extends BroadcastReceiver {
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
    }

    public void notifityPrev() {
        binder.nextMusic();
        mManager.notify(Constants.NOTIFY_ID, notification);
    }

    private void notifyPrev() {
        binder.befoMusic();
        mManager.notify(Constants.NOTIFY_ID, notification);
    }

    @SuppressLint("NewApi")
    public void notifityPlay() {
        if (isPrepared) {
            if (binder.isStart()) {
                binder.pauseMusic();
                notification.bigContentView.setImageViewResource(R.id.notifi_circle,
                        R.mipmap.minilyric_play_button_press);
            } else {
                binder.start();
                notification.bigContentView.setImageViewResource(R.id.notifi_circle,
                        R.mipmap.minilyric_pause_button_press);
            }
        }
        mManager.notify(Constants.NOTIFY_ID, notification);
    }

}

