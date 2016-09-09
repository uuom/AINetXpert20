package com.asiainfo.netxpert.home.presenter;

/**
 * Created by uuom on 16-8-12.
 */
public interface HomePresenter {

    /**
     * 数据列表刷新
     */
    void refreshData();

    /**
     * 加载更多
     * @param currentPage
     */
    void loadMoreData(int currentPage);
}
