package com.example.administrator.musictest.viewpag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.musictest.R;
import com.example.administrator.musictest.service.MusicService;

import org.srr.dev.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/2
 */
public class ButtonFragment extends BaseFragment {
    @Bind(R.id.btn_start)
    Button btnStart;

    private boolean mOpen = true;
    private Intent mMusicIntent;

    public static ButtonFragment newInstance() {
        Bundle args = new Bundle();
        ButtonFragment fragment = new ButtonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frg_button;
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        mMusicIntent = new Intent(getContext(), MusicService.class);
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

    @OnClick(R.id.btn_start)
    public void onClick() {
        if (mOpen) {
            getContext().startService(mMusicIntent);
        } else
            getContext().stopService(mMusicIntent);
        mOpen = !mOpen;
    }
}
