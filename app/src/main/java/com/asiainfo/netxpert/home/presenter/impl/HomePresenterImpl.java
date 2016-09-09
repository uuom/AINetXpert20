package com.asiainfo.netxpert.home.presenter.impl;

import com.asiainfo.netxpert.bean.Trouble;
import com.asiainfo.netxpert.home.model.HomeModel;
import com.asiainfo.netxpert.home.model.impl.HomeModelImpl;
import com.asiainfo.netxpert.home.presenter.HomePresenter;
import com.asiainfo.netxpert.home.view.HomeView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by uuom on 16-8-12.
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView homeView;
    private HomeModel homeModel;

    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
        homeModel = new HomeModelImpl();
    }

    @Override
    public void refreshData() {
        homeModel.getNewData(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Trouble>>() {
                    @Override
                    public void call(List<Trouble> troubles) {
                        homeView.setNewData(troubles);
                        homeView.hideRefreshLayout();
                        homeView.setRecyclerViewCurrentPage(1);
                    }
                });
    }

    @Override
    public void loadMoreData(final int currentPage) {
        homeModel.getNewData(currentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Trouble>>() {
                    @Override
                    public void call(List<Trouble> troubles) {
                        if (troubles != null && troubles.size()>0){
                            homeView.setRecyclerViewCurrentPage(currentPage+1);
                        }
                        homeView.addNewData(troubles);
                    }
                });
    }

    public static void main(String[] args) {

        Observable.just("hello world")
                .map(new Func1<String, Integer>() {  //变换
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {  //订阅
                    @Override
                    public void call(Integer s) {
                        System.out.println(s);
                    }
                });
    }
}
