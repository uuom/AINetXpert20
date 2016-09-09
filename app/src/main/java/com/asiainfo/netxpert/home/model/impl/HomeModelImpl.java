package com.asiainfo.netxpert.home.model.impl;

import android.util.Log;

import com.asiainfo.netxpert.bean.Trouble;
import com.asiainfo.netxpert.home.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by uuom on 16-8-12.
 */
public class HomeModelImpl implements HomeModel {

    @Override
    public Observable<List<Trouble>> getNewData(final int i) {
        Log.d("HomeModelImpl", i+"");
        Observable<List<Trouble>> observable = Observable.create(new Observable.OnSubscribe<List<Trouble>>() {
            @Override
            public void call(final Subscriber<? super List<Trouble>> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(getData(3, i));
                        subscriber.onCompleted();
                    }
                }).start();
            }
        });

        return observable;
    }

    public List<Trouble> getData(int count,int page){
        List<Trouble> dataList = new ArrayList<>();
        if (page == 3){
            return dataList;
        }
        for (int i = 0; i < count; i++) {
            dataList.add(new Trouble());
        }
        return dataList;
    }
}
