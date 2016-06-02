package org.srr.dev.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.srr.dev.adapter.QuickDeAdapter.BaseAdapterHelper;
import org.srr.dev.adapter.QuickDeAdapter.MultiItemTypeSupport;
import org.srr.dev.adapter.QuickDeAdapter.QuickAdapter;
import org.srr.dev.view.xrecyclerview.JRecyclerView;
import org.srr.dev.view.xrecyclerview.XRecyclerView;

import java.util.List;


/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/12
 */
public  abstract class BaseRecyItemFragment<DataType> extends BaseFragment implements
        XRecyclerView.LoadingListener, SwipeRefreshLayout.OnRefreshListener {
    private JRecyclerView mJrecyclerView;
    private XRecyclerView mRecyclerList;
    private SwipeRefreshLayout mSrl;
    public QuickAdapter<DataType> mQuickAdapter;
    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;



    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView(View contentView) ;

    @Override
    public abstract void initData() ;

    @Override
    protected void initRec() {
        super.initRec();
        mJrecyclerView=initJrec();
        mRecyclerList=mJrecyclerView.getRecyclerView();
        mSrl=mJrecyclerView.getSwipeRefreshLayout();

        mRecyclerList.setLayoutManager(setLayoutManager());
        MultiItemTypeSupport<DataType> multiItemTypeSupport=new MultiItemTypeSupport<DataType>() {
           @Override
           public int getLayoutId(int viewType) {
               return setayoutId(viewType);
           }
           @Override
           public int getItemViewType(int position, DataType s) {
               return setItemViewType(position,s);
           }
        };

        //内部处理的快速adapter
        mQuickAdapter= new QuickAdapter<DataType>(setContext(), multiItemTypeSupport) {
            @Override
            protected void convert(BaseAdapterHelper helper, DataType item) {
               doHolderDispose(helper,item);
            }
        };
        //TODO:预留下拉刷新的监听事件绑定方法
        initrecs();

        mRecyclerList.setAdapter(mQuickAdapter);
        mQuickAdapter.addAll(setFirstData());
    }


    /**
     * 绑定下拉监听接口
     */
    protected void initrecs()
    {
        mRecyclerList.setLoadingListener(this);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipRefresh();
            }
        });
    }



    /**
     * 提供Context
     * @return Context
     */
    protected abstract Context setContext();

    /**
     * 自定义控件Jrecyclerview赋值
     * @return JRecyclerView
     */
    protected abstract JRecyclerView initJrec();

    /**
     * 设置布局管理器
     * @return int
     */
    protected abstract RecyclerView.LayoutManager setLayoutManager();
    /**
     * 赋予当前positon 中viewtype的值
     * @return viewtype
     */
    protected abstract int setItemViewType(int position, DataType datatype);

    /**
     * 根据viewtype返回不同的itemlayout
     * @param viewType
     * @return layoutId
     */
    protected abstract int setayoutId(int viewType);

    /**
     * 容器初始化
     * 后续更新数据只需执行mQuickAdapter.addAll或者replaceAll
     */
    protected abstract List<DataType> setFirstData();

    /**
     * 根据helper对内部holder做相应处理
     * @param helper
     * @param itemdata
     */
    protected abstract void doHolderDispose(BaseAdapterHelper helper, DataType itemdata);


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
     * 加载更多状态重置
     * 出现加载更多状态为已完成，下拉刷新后没办法继续加载更多后，需在下拉刷新部分调用该方法
     */
    public void resetMoreState()
    {
        mRecyclerList.resetStatus();
    }
    /**
     * 设置下拉刷新是否可用，默认可用
     * @param aEnable
     */
    public void setSrlEnable(boolean aEnable)
    {
        mSrl.setEnabled(aEnable);
    }
    /**
     * 用于下拉刷新结束
     */
    public void dismiss()
    {
        mSrl.setRefreshing(false);
    }

    /**
     * 加载更多完成调用
     */
    public void loadMoreComplete()
    {
        mRecyclerList.loadMoreComplete();
    }
}
