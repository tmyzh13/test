package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by issuser on 2018/2/24.
 */

public class IntervalDemoTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest request=retrofit.create(GetRequest.class);
        final Observable<Translation> observable =request.getCall();



        Observable.interval(2,1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("yzh","第"+aLong+"次轮询");
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("yzh","请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("yzh","对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e("yzh","对Complete事件作出响应");
            }
        });
//        02-24 14:56:16.528 8733-8760/com.example.issuser.rxtest E/yzh: 第0次轮询
//        02-24 14:56:16.886 8733-8733/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-24 14:56:17.528 8733-8760/com.example.issuser.rxtest E/yzh: 第1次轮询
//        02-24 14:56:17.702 8733-8733/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-24 14:56:18.529 8733-8760/com.example.issuser.rxtest E/yzh: 第2次轮询
//        02-24 14:56:18.675 8733-8733/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-24 14:56:19.528 8733-8760/com.example.issuser.rxtest E/yzh: 第3次轮询
//        02-24 14:56:19.674 8733-8733/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-24 14:56:20.528 8733-8760/com.example.issuser.rxtest E/yzh: 第4次轮询
//        02-24 14:56:20.674 8733-8733/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-24 14:56:21.529 8733-8760/com.example.issuser.rxtest E/yzh: 第5次轮询
    }
}
