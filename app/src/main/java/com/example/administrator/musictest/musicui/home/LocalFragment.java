package com.example.administrator.musictest.musicui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.administrator.musictest.R;
import com.example.administrator.musictest.entity.MusicEntity;
import com.example.administrator.musictest.entity.StaticGloaData;

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
public class LocalFragment extends BaseFragment {

    @Bind(R.id.rv_base)
    RecyclerView rvBase;

    MusicListAdapter mAdapter = new MusicListAdapter();
    ArrayList<MusicEntity> mDatas;

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
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    static public class VH extends RecyclerView.ViewHolder {
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


    static class MusicListAdapter extends RecyclerViewDataAdapter<MusicEntity, VH> {




        @Override
        public void onBindHolder(VH holder, int i, MusicEntity musicEntity) {
            holder.tvMusicName.setText(musicEntity.getName());
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
