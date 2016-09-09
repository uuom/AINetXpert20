package com.asiainfo.netxpert.home.model;

import com.asiainfo.netxpert.bean.Trouble;

import java.util.List;

import rx.Observable;

/**
 * Created by uuom on 16-8-12.
 */
public interface HomeModel {

    /**
     * 分页获取数据
     * @param i
     * @return
     */
    Observable<List<Trouble>> getNewData(int i);
}
