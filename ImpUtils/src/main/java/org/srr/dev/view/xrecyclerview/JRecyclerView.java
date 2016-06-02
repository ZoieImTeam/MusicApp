package org.srr.dev.view.xrecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import org.srr.dev.R;


/**
 * Created by Cainer on 2016/5/3.
 */
//@EViewGroup(R.layout.custom_jrecyclerview)
public class JRecyclerView extends FrameLayout {
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

//    @ViewById(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
//    @ViewById(R.id.recyclerview)
    XRecyclerView recyclerView;
    public JRecyclerView(Context context,AttributeSet attrs) {
        super(context,attrs);
        View.inflate(context, R.layout.custom_jrecyclerview, this);
        swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.swipe);
        recyclerView=(XRecyclerView)findViewById(R.id.recyclerview);
    }
}
