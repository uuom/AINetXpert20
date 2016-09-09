package com.asiainfo.netxpert.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asiainfo.netxpert.R;
import com.asiainfo.netxpert.view.AIRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uuom on 16-8-11.
 */
public class HomeDataAdapter extends AIRecyclerView.AIAdapter {

    @Override
    public RecyclerView.ViewHolder onCreateDateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trouble, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindDataItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_trouble_level)
        ImageView iv_trouble_level;
        @BindView(R.id.tv_trouble_type)
        TextView tv_trouble_type;
        @BindView(R.id.tv_trouble_time) TextView tv_trouble_time;
        @BindView(R.id.iv_unread) ImageView iv_unread;
        @BindView(R.id.tv_trouble_content) TextView tv_trouble_content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
