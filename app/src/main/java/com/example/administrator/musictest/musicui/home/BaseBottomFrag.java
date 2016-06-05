package com.example.administrator.musictest.musicui.home;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.musictest.R;

import org.srr.dev.base.BaseFragment;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/4
 */
public class BaseBottomFrag extends BaseFragment{

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

    }

    @Override
    protected void initData() {

    }

    @Override
    public void doAfterReConnectNewWork() {

    }
}
