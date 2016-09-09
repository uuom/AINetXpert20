package com.asiainfo.netxpert.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.netxpert.R;
import com.asiainfo.netxpert.bean.Trouble;
import com.asiainfo.netxpert.home.adapter.HomeDataAdapter;
import com.asiainfo.netxpert.home.presenter.HomePresenter;
import com.asiainfo.netxpert.home.presenter.impl.HomePresenterImpl;
import com.asiainfo.netxpert.home.view.HomeView;
import com.asiainfo.netxpert.view.AIRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeView{

    @BindView(R.id.home_toolbar)
    Toolbar home_toolbar;

    @BindView(R.id.home_airv)
    AIRecyclerView mAIRecyclerView;

    private AIRecyclerView.AIAdapter adapter;

    private HomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(home_toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("首页");

        homePresenter = new HomePresenterImpl(this);

        adapter = new HomeDataAdapter();
        adapter.setDataList(getData(10));
        mAIRecyclerView.setAdapter(adapter);
        mAIRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mAIRecyclerView.setSwipeRefreshLayoutColor(getResources().getColor(R.color.md_my_blue));
        mAIRecyclerView.setOnRefreshListener(new AIRecyclerView.OnRefreshListener() {
            @Override
            public void onRefreshHelper(final SwipeRefreshLayout refreshLayout) {
                homePresenter.refreshData();
            }
        });
        mAIRecyclerView.setOnScrollBottomListener(new AIRecyclerView.OnScrollBottomListener() {
            @Override
            public void onScrollBottomListener(int currentPage) {
                homePresenter.loadMoreData(currentPage);
            }
        });

        return view;
    }

    public List<Trouble> getData(int count){
        List<Trouble> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataList.add(new Trouble());
        }
        return dataList;
    }

    public List<Trouble> getMoreData(){
        List<Trouble> dataList = new ArrayList<>();
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

    @Override
    public void setNewData(List<Trouble> troubles) {
        adapter.setDataList(troubles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshLayout() {
        mAIRecyclerView.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void setRecyclerViewCurrentPage(int i) {
        mAIRecyclerView.setCurrentPage(i);
    }

    @Override
    public void addNewData(List<Trouble> troubles) {
        adapter.addDataList(troubles);
        adapter.notifyDataSetChanged();
    }
}
