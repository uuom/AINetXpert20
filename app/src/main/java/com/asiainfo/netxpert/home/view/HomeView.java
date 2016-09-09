package com.asiainfo.netxpert.home.view;

import com.asiainfo.netxpert.bean.Trouble;

import java.util.List;

/**
 * Created by uuom on 16-8-12.
 */
public interface HomeView {


    void setNewData(List<Trouble> troubles);

    void hideRefreshLayout();

    void setRecyclerViewCurrentPage(int i);

    void addNewData(List<Trouble> troubles);
}
