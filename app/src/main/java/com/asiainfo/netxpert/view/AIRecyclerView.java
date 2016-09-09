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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuom on 16-8-10.
 */
public class AIRecyclerView extends LinearLayout {

    private int currentPage = 1;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AIAdapter adapter;
    public void setAdapter(AIAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    private LinearLayoutManager layoutManager;

    public AIRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.ai_recyclerview, this, true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.ai_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ai_refreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
    }
    public void setSwipeRefreshLayoutColor(int... swipeRefreshLayoutColor) {
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshLayoutColor);
    }

    public void setOnScrollBottomListener(final OnScrollBottomListener onScrollBottomListener) {
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
                        onScrollBottomListener.onScrollBottomListener(currentPage);
                    }
                }
            }
        });
    }

    public void setOnRefreshListener(final OnRefreshListener onRefreshListener) {
        if(onRefreshListener != null){
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onRefreshListener.onRefreshHelper(mSwipeRefreshLayout);
                }
            });
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public interface OnScrollBottomListener{
        void onScrollBottomListener(int currentPage);
    }

    public interface OnRefreshListener{
        void onRefreshHelper(SwipeRefreshLayout refreshLayout);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public static abstract class AIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private static final int EMPTY_ITEM_TYPE = 0;
        private static final int LIST_ITEM_TYPE = 1;
        private static final int MORE_ITEM_TYPE = 2;

        private List dataList = new ArrayList();
        private List newDataList = new ArrayList();

        public void setDataList(List dataList) {
            if (newDataList != null){
                newDataList.clear();
            }
            newDataList = dataList;
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh = null;
            View view;
            Log.d("AIRecyclerView", viewType+"");
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
                    vh = onCreateDateItemViewHolder(parent);
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
                if (newDataList.size() > 0 && dataList.size()>9){
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

        @Override
        public int getItemViewType(int position) {
            if (dataList == null || dataList.size() == 0){
                return EMPTY_ITEM_TYPE;
            }else if(position == dataList.size()){
                return MORE_ITEM_TYPE;
            }
            return LIST_ITEM_TYPE;
        }

        @Override
        public int getItemCount() {
            if (dataList == null || dataList.size() == 0){
                return 1;
            }
            return dataList.size()+1;
        }

        public void addDataList(List datas){
            if (datas != null){
                newDataList = datas;
            }
            dataList.addAll(datas);
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
}
