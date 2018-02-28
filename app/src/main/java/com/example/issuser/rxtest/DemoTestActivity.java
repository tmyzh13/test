package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by issuser on 2018/2/23.
 */

public class DemoTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Observable<Integer> observable =Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e("yzh","被观察者Observable的工作线程："+Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        });

        Observer<Integer> observer =new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("yzh","onSubscribe");
                Log.e("yzh","观察者observer所在线程--"+Thread.currentThread().getName());

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("yzh","onNext--"+integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("yzh","onComplete");
            }
        };
//        observable.subscribe(observer);
//        02-23 16:28:06.912 18139-18139/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 16:28:06.912 18139-18139/com.example.issuser.rxtest E/yzh: 观察者observer所在线程--main
//        02-23 16:28:06.912 18139-18139/com.example.issuser.rxtest E/yzh: 被观察者Observable的工作线程：main
//        02-23 16:28:06.912 18139-18139/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 16:28:06.912 18139-18139/com.example.issuser.rxtest E/yzh: onComplete
        //subscribeOn 切换被观察者（Observable）线程
        //observeOn 切换观察者（Observer）线程
        //Schedulers.immediate()  当前线程 不指定线程
        //AndroidScheduler.mainThread() Android主线程 操作ui
        //Schedulers.newThread() 常规新线程  耗时操作
        //Schedulers.io()  io操作线程  网络请求，读写文件等
        //Schedulers.computation() cpu计算操作线程  大量计算操作

//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//        02-23 16:46:11.775 21680-21680/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 16:46:11.775 21680-21680/com.example.issuser.rxtest E/yzh: 观察者observer所在线程--main
//        02-23 16:46:11.777 21680-21706/com.example.issuser.rxtest E/yzh: 被观察者Observable的工作线程：RxNewThreadScheduler-1
//        02-23 16:46:11.791 21680-21680/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 16:46:11.791 21680-21680/com.example.issuser.rxtest E/yzh: onComplete

//        若Observable.subscribeOn（）多次指定被观察者 生产事件的线程，则只有第一次指定有效，其余的指定线程无效
//        observable.subscribeOn(Schedulers.newThread()) // 第一次指定被观察者线程 = 新线程
//                .subscribeOn(AndroidSchedulers.mainThread()) // 第二次指定被观察者线程 = 主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
        //第二次指定没起作用
//        02-23 16:49:18.619 22236-22236/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 16:49:18.619 22236-22236/com.example.issuser.rxtest E/yzh: 观察者observer所在线程--main
//        02-23 16:49:18.646 22236-22281/com.example.issuser.rxtest E/yzh: 被观察者Observable的工作线程：RxNewThreadScheduler-1
//        02-23 16:49:18.685 22236-22236/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 16:49:18.685 22236-22236/com.example.issuser.rxtest E/yzh: onComplete

//        若Observable.observeOn（）多次指定观察者 接收 & 响应事件的线程，则每次指定均有效，即每指定一次，就会进行一次线程的切换
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread()) // 第一次指定观察者线程 = 主线程
//                .doOnNext(new Consumer<Integer>() { // 生产事件
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e("yzh", "第一次观察者Observer的工作线程是： " + Thread.currentThread().getName());
//                    }
//                })
//                .observeOn(Schedulers.newThread()) // 第二次指定观察者线程 = 新的工作线程
//                .doOnNext(new Consumer<Integer>() { // 生产事件
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e("yzh", "第二次观察者Observer的工作线程是： " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe(observer); // 生产事件

//        02-23 16:57:47.946 23969-23969/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 16:57:47.946 23969-23969/com.example.issuser.rxtest E/yzh: 观察者observer所在线程--main
//        02-23 16:57:47.947 23969-24001/com.example.issuser.rxtest E/yzh: 被观察者Observable的工作线程：RxNewThreadScheduler-1
//        02-23 16:57:47.961 23969-23969/com.example.issuser.rxtest E/yzh: 第一次观察者Observer的工作线程是： main
//        02-23 16:57:47.962 23969-24003/com.example.issuser.rxtest E/yzh: 第二次观察者Observer的工作线程是： RxNewThreadScheduler-2
//        02-23 16:57:47.962 23969-24003/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 16:57:47.962 23969-24003/com.example.issuser.rxtest E/yzh: onComplete


    }
}
