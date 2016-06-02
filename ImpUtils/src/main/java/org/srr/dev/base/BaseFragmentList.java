package org.srr.dev.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import org.srr.dev.adapter.RecyclerViewDataAdapter;
import org.srr.dev.view.xrecyclerview.JRecyclerView;
import org.srr.dev.view.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Zoi on 2016/5/3.
 * 基础的Fragment 内置 recyclerview，下拉刷新以及加载更多
 * 需传入2泛型 分别是VH和数据类型
 * 暂仅支持一种item类型的适配处理
 */
public abstract class BaseFragmentList<DataType,VH extends RecyclerView.ViewHolder> extends BaseFragment implements
        XRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private XRecyclerView mRecyclerList;
    private SwipeRefreshLayout srl;
    public JRecyclerView jRecyclerView;

    public MyAdapter adapter=new MyAdapter();
    private ArrayList<DataType> mData;


    /**
     * 获得布局
     * @return
     */
    @Override
    public abstract int getLayoutId() ;

    @Override
    protected abstract void initView(View contentView);

    @Override
    protected abstract void initData();

    /**
     * 重新联网时变量初始化
     */
    @Override
    public abstract void doAfterReConnectNewWork();

    @Override
    protected void initRec() {
        jRecyclerView=initJrec();
        mRecyclerList=jRecyclerView.getRecyclerView();
        srl=jRecyclerView.getSwipeRefreshLayout();
        initrecs();
        mData=getmDatalist();
        adapter.setData(mData);
        mRecyclerList.setAdapter(adapter);
        mRecyclerList.setLayoutManager(setLayoutManager());

    }

    private void initrecs() {
        mRecyclerList.setLoadingListener(this);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipRefresh();
            }
        });
    }


    /**
     * 填写下拉刷新执行的逻辑
     */
    protected abstract void swipRefresh();


    /**
     * 不用继承或重写，重写无效
     */
    @Override
    public void onRefresh() {
    }


    /**
     * 布局管理器
     * @return
     */
    protected abstract RecyclerView.LayoutManager setLayoutManager();

    /**
     * 传入自定义控件 JrecyclerView
     * @return
     */
    protected abstract JRecyclerView initJrec();

    /**
     * 传入数据类型集合
     * @return
     */
    protected abstract ArrayList<DataType> getmDatalist();

    /**
     * 返回Item的布局文件
     * @param viewType  item 的类型
     * @return
     * 可以通过 枚举选择返回不同的item
     */
    protected abstract int getItemLayout(int viewType);

    /**
     *用于获得ViewHolder,持有每个Item的的所有界面元素
     * 通常 new VH(v)
     * @return
     */
    protected abstract VH getViewMyViewHolder(View v);

    /**
     * 用于 Adapter绑定VH
     * @param holder
     * @param i (position)
     * @param dataType data的数据类型
     */
    protected abstract void onBindAdapterHolder(VH holder, int i, DataType dataType);

    /**
     * 用于下拉刷新结束
     */
    public void dismiss()
    {
        srl.setRefreshing(false);
    }

    /**
     * 设置下拉刷新是否可用，默认可用
     * @param aEnable
     */
    public void setSrlEnable(boolean aEnable)
    {
        srl.setEnabled(aEnable);
    }

    /**
     * 加载更多状态重置
     * 出现加载更多状态为已完成，下拉刷新后没办法继续加载更多后，需在下拉刷新部分调用该方法
     */
    public void resetMoreState()
    {
        mRecyclerList.resetStatus();
    }

    /**
     * 抽象父类根据数据类型和VH，生成的adapter
     */
    public class MyAdapter extends RecyclerViewDataAdapter<DataType,VH>
    {

        @Override
        public void onBindHolder(VH holder, int i, DataType dataType) {
            onBindAdapterHolder(holder,i,dataType);
        }

        @Override
        public VH getViewHolder(View v) {
            return getViewMyViewHolder(v);
        }

        @Override
        public int getLayoutId(int viewType) {
            return getItemLayout(viewType);
        }
    }

    /**
     * 加载更多完成调用
     */
    public void loadMoreComplete()
    {
        mRecyclerList.loadMoreComplete();
    }

}
