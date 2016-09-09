package com.asiainfo.netxpert.message.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.netxpert.R;
import com.asiainfo.netxpert.bean.Trouble;
import com.asiainfo.netxpert.message.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by uuom on 16-8-8.
 */
public class TroubleFragment extends Fragment {

    private static final int HANDLER_WHAT_REFRESH = 0x1;
    private static final int HANDLER_WHAT_MORE = 0x2;

    private int type;

    @BindView(R.id.rv_dataList) RecyclerView mAIRecyclerView;
    @BindView(R.id.srl_refreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    public TroubleFragment(int type) {
        this.type = type;
    }

    DataAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("TroubleFragment", type+"");

        View view = inflater.inflate(R.layout.fragment_trouble, container, false);
        ButterKnife.bind(this, view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mAIRecyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(this.getContext());
        adapter.addDataList(getData());
        mAIRecyclerView.setAdapter(adapter);
        mAIRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastPosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("TroubleFragment", lastPosition+"----"+adapter.getItemCount());
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition +1 == adapter.getItemCount()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.obj = getMoreData();
                            msg.what = HANDLER_WHAT_MORE;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.md_my_blue));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        msg.what = HANDLER_WHAT_REFRESH;
                        msg.obj = getNewData();
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
        return view;
    }

    public List<Trouble> getData(){
        List<Trouble> dataList = new ArrayList<>();
        if (type == 2){
            return dataList;
        }
        for (int i = 0; i < 20; i++) {
            dataList.add(new Trouble());
        }
        return dataList;
    }

    public List<Trouble> getMoreData(){
        List<Trouble> dataList = new ArrayList<>();
        if (type == 1){
            return dataList;
        }
        for (int i = 0; i < 5; i++) {
            dataList.add(new Trouble());
        }
        return dataList;
    }

    public List<Trouble> getNewData(){
        List<Trouble> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add(new Trouble());
        }
        return dataList;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_WHAT_REFRESH:
                    adapter.setDataList((List<Trouble>) msg.obj);
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case HANDLER_WHAT_MORE:
                    adapter.addDataList((List<Trouble>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
