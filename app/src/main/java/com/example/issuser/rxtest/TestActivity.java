package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


/**
 * Created by issuser on 2018/2/7.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        Observable.just(1,2,3,4,5)
//                .filter(new Predicate<Integer>() {
//                    @Override
//                    public boolean test(Integer integer) throws Exception {
//                        Log.e("yzh","filter");
//                        return integer>3;
//                    }
//                }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e("yzh","onNext--"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: filter
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: filter
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: filter
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: filter
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: onNext--4
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: filter
//        02-07 15:35:28.549 22207-22207/com.example.issuser.rxtest E/yzh: onNext--5

//        Observable.just("1",2,"abv",5,"qqq")
//                .ofType(Integer.class)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                            Log.e("yzh","accept--"+integer);
//                    }
//                }) ;
//        02-07 15:40:03.320 22735-22735/com.example.issuser.rxtest E/yzh: accept--2
//        02-07 15:40:03.320 22735-22735/com.example.issuser.rxtest E/yzh: accept--5

//        Observable.just(1,2,3,4,5)
//                .skip(1)
//                .skipLast(1)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                            Log.e("yzh","accept--"+integer);
//                    }
//                });
//        E/yzh: accept--2
//        E/yzh: accept--3
//        E/yzh: accept--4
        Observable.intervalRange(0,5,1,1, TimeUnit.SECONDS)
                .skip(1)
                .skipLast(1)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        Log.e("yzh","accept--"+integer);
                    }
                });
//        02-07 15:59:26.198 24724-24765/com.example.issuser.rxtest E/yzh: accept--1
//        02-07 15:59:27.198 24724-24765/com.example.issuser.rxtest E/yzh: accept--2
//        02-07 15:59:28.198 24724-24765/com.example.issuser.rxtest E/yzh: accept--3
        Observable.just(1,2,3,2,1)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("yzh","accept--"+integer);
                    }
                });
        //accept--1
        //accept--2
        //accept--3
    }
}
