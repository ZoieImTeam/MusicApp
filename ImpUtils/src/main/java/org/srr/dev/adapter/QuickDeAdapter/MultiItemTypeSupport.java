package org.srr.dev.adapter.QuickDeAdapter;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/12
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}
