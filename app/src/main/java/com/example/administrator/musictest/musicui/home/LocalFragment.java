package com.example.administrator.musictest.musicui.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.administrator.musictest.R;
import com.example.administrator.musictest.entity.MusicEntity;
import com.example.administrator.musictest.entity.StaticGloaData;
import com.example.administrator.musictest.service.MusicService;

import org.srr.dev.adapter.RecyclerViewDataAdapter;
import org.srr.dev.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/2
 */
public class LocalFragment extends BaseFragment implements ServiceConnection {

    @Bind(R.id.rv_base)
    RecyclerView rvBase;

    MusicListAdapter mAdapter = new MusicListAdapter();
    ArrayList<MusicEntity> mDatas;

    Intent mIntent;
    MusicService.MusicBinder mMusicBinder;
    ServiceConnection connection = this;


    public static LocalFragment newInstance() {
        Bundle args = new Bundle();
        LocalFragment fragment = new LocalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.frg_base_list;
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        mIntent = new Intent(getActivity(), MusicService.class);
        getContext().bindService(mIntent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        rvBase.setAdapter(mAdapter);
        rvBase.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas.addAll(StaticGloaData.getMusic());
        mAdapter.setData(mDatas);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void doAfterReConnectNewWork() {

    }

    @Override
    public void onDestroyView() {
        getContext().unbindService(connection);
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMusicBinder = (MusicService.MusicBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


    public class VH extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.tv_music_name)
        TextView tvMusicName;
        @Bind(R.id.swipe)
        SwipeLayout swipe;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


    class MusicListAdapter extends RecyclerViewDataAdapter<MusicEntity, VH>  {

        @Override
        public void onBindHolder(final VH holder, int i, final MusicEntity musicEntity) {
            holder.tvMusicName.setText(musicEntity.getName());
            View.OnClickListener l =new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMusicBinder.playMusic(musicEntity.getUrl());
                }
            };
            holder.tvMusicName.setOnClickListener(l);

        }



        @Override
        public VH getViewHolder(View v) {
            return new VH(v);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_music_base;
        }
    }


}
