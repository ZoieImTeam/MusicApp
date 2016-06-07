package com.example.administrator.musictest.musicui.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.musictest.R;
import com.example.administrator.musictest.service.MusicPlayHelper;
import com.example.administrator.musictest.service.MusicService;

import org.srr.dev.base.BaseFragment;
import org.srr.dev.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/4
 */
public class BaseBottomFrag extends BaseFragment implements ServiceConnection {

    @Bind(R.id.notifi_circle)
    CircleImageView notifiCircle;
    @Bind(R.id.notifi_music_name)
    TextView notifiMusicName;
    @Bind(R.id.notifi_singer)
    TextView notifiSinger;
    @Bind(R.id.notifi_next)
    TextView notifiNext;
    @Bind(R.id.notifi_start)
    TextView notifiStart;
    @Bind(R.id.notifi_last)
    TextView notifiLast;

    private ServiceConnection mMusicCon=this;
    protected MusicService.MusicBinder mMusicBinder;
    private Intent mIntent;
    protected MusicPlayHelper helper;

    public static BaseBottomFrag newInstance() {
        Bundle args = new Bundle();
        BaseBottomFrag fragment = new BaseBottomFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frg_base_bottom;
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        helper=new MusicPlayHelper(getActivity());
    }

    @Override
    protected void initData() {
    }

    @Override
    public void doAfterReConnectNewWork() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.notifi_next, R.id.notifi_start, R.id.notifi_last})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notifi_next:
                // TODO: 2016/6/6  下一曲
                helper.nextMusic();
                break;
            case R.id.notifi_start:
                // TODO: 2016/6/6  开始
                helper.play();
                break;
            case R.id.notifi_last:
                // TODO: 2016/6/6  上一首
                helper.lastMusic();
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMusicBinder= (MusicService.MusicBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
