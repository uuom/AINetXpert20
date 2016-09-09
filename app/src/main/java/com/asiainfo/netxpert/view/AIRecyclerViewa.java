package com.asiainfo.netxpert.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asiainfo.netxpert.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by uuom on 16-8-10.
 */
public class AIRecyclerViewa extends LinearLayout {

    private static final int EMPTY_ITEM_TYPE = 0;
    private static final int LIST_ITEM_TYPE = 1;
    private static final int MORE_ITEM_TYPE = 2;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    private LinearLayoutManager layoutManager;
    private int[] swipeRefreshLayoutColor;
    private AIAdapter adapter;

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
    }
    public void setSwipeRefreshLayoutColor(int... swipeRefreshLayoutColor) {
        this.swipeRefreshLayoutColor = swipeRefreshLayoutColor;
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshLayoutColor);
    }
    public void setAdapter(AIAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
        Log.e("AIRecyclerView", mRecyclerView.toString());
    }

    public AIRecyclerViewa(Context context, AttributeSet attributeSet) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.ai_recyclerview, this, true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_dataList);

        Log.e("AIRecyclerView", "ButterKnife");

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastPosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition +1 == adapter.getItemCount()){
                    if (onScrollBottomListener != null){
                        onScrollBottomListener.onScrollBottomListener();
                    }
                }
            }
        });

        if(onRefreshListener != null){
            mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    private OnScrollBottomListener onScrollBottomListener;
    public void setOnScrollBottomListener(OnScrollBottomListener onScrollBottomListener) {
        this.onScrollBottomListener = onScrollBottomListener;
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public abstract static class AIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List dataList;
        List newDataList;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh = null;
            View view;
            switch (viewType){
                case EMPTY_ITEM_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
                    vh = new EmptyViewHolder(view);
                    break;
                case MORE_ITEM_TYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_list, parent, false);
                    vh = new MoreViewHolder(view);
                    break;
                case LIST_ITEM_TYPE:
                    onCreateDateItemViewHolder(parent);
                    break;
            }
            return vh;
        }
        public abstract RecyclerView.ViewHolder onCreateDateItemViewHolder(ViewGroup parent);

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof EmptyViewHolder){
                //TODO set empty why.
            }else if (holder instanceof MoreViewHolder){
                MoreViewHolder vh = (MoreViewHolder) holder;
                if (newDataList.size() > 0){
                    vh.no_more_data.setVisibility(View.GONE);
                    vh.avLoadingIndicatorView.setVisibility(View.VISIBLE);
                }else{
                    vh.no_more_data.setVisibility(View.VISIBLE);
                    vh.avLoadingIndicatorView.setVisibility(View.GONE);
                }
            }else{
                onBindDataItemViewHolder(holder, position);
            }
        }
        public abstract void onBindDataItemViewHolder(RecyclerView.ViewHolder holder, int position);

        public void setDataList(List<?> dataList) {
            this.dataList = dataList;
        }

        public void addDataList(List dataList) {
            this.newDataList = dataList;
            this.dataList.addAll(dataList);
        }

        @Override
        public int getItemCount() {
            if (dataList == null || dataList.size() == 0){ //为空，显示一个无数据。
                return 1;
            }
            return dataList.size()+1;   //其他情况显示n+1个，最后面为正在加载或者没有更多数据。
        }

        @Override
        public int getItemViewType(int position) {
            if (dataList == null || dataList.size() == 0){  //为空，返回empty type。
                return EMPTY_ITEM_TYPE;
            }else if(position == dataList.size()){  //不为空，最后一个显示未加载更多获取没有更多。
                return MORE_ITEM_TYPE;
            }
            return LIST_ITEM_TYPE;  //真正dataItem。
        }

        class EmptyViewHolder extends RecyclerView.ViewHolder{

            public EmptyViewHolder(View itemView) {
                super(itemView);
            }
        }

        class MoreViewHolder extends RecyclerView.ViewHolder{

            TextView no_more_data;
            AVLoadingIndicatorView avLoadingIndicatorView;

            public MoreViewHolder(View itemView) {
                super(itemView);
                no_more_data = (TextView) itemView.findViewById(R.id.no_more_data);
                avLoadingIndicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.avi);
            }
        }
    }


    interface OnScrollBottomListener{
        void onScrollBottomListener();
    }
}
