package com.asiainfo.netxpert.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asiainfo.netxpert.R;
import com.asiainfo.netxpert.bean.Trouble;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uuom on 16-8-9.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int EMPTY_ITEM_TYPE = 0;
    private static final int LIST_ITEM_TYPE = 1;
    private static final int MORE_ITEM_TYPE = 2;

    private List<Trouble> dataList = new ArrayList<>();
    private List<Trouble> tempList = new ArrayList<>();
    private Context context;

    public DataAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<Trouble> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public void addDataList(List<Trouble> dataList) {
        tempList.clear();
        tempList.addAll(dataList);
        this.dataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        Log.e("DataAdapter", viewType+" ------------ "+dataList.size());
        if (viewType == LIST_ITEM_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_trouble, parent, false);
            vh = new ListViewHolder(view);
        }else if (viewType == EMPTY_ITEM_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.empty_list, parent, false);
            vh = new EmptyViewHolder(view);
        }else if (viewType == MORE_ITEM_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.more_list, parent, false);
            vh = new MoreViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder){
            Trouble trouble = dataList.get(position);
            //TODO bing data
        }else if (holder instanceof EmptyViewHolder){
            //TODO set empty why.
        }else if (holder instanceof MoreViewHolder){
            MoreViewHolder vh = (MoreViewHolder) holder;
            if (tempList.size() > 0){
                vh.no_more_data.setVisibility(View.GONE);
                vh.avLoadingIndicatorView.setVisibility(View.VISIBLE);
            }else{
                vh.no_more_data.setVisibility(View.VISIBLE);
                vh.avLoadingIndicatorView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList ==null || dataList.size() == 0){
            return EMPTY_ITEM_TYPE;
        }else if(dataList.size()>=10 && position == dataList.size()){
            return MORE_ITEM_TYPE;
        }
        return LIST_ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        if (dataList == null || dataList.size() == 0){
            return 1;
        }else if(dataList.size()>=10){
            return dataList.size()+1;
        }else{
            return dataList.size();
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_trouble_level) ImageView iv_trouble_level;
        @BindView(R.id.tv_trouble_type) TextView tv_trouble_type;
        @BindView(R.id.tv_trouble_time) TextView tv_trouble_time;
        @BindView(R.id.iv_unread) ImageView iv_unread;
        @BindView(R.id.tv_trouble_content) TextView tv_trouble_content;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
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
