package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by issuser on 2018/2/24.
 */

public class ReconnectActivity extends Activity {

    private int maxConnectCount=10;
    private int currentRetryCount=0;
    private int waitRetryTime=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(Urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest request=retrofit.create(GetRequest.class);
        Observable<Translation> observable=request.getCall();
       observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
           @Override
           public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
               return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                   @Override
                   public ObservableSource<?> apply(Throwable throwable) throws Exception {
                       Log.e("yzh","发生异常=="+throwable.toString());
                       if(throwable instanceof IOException){
                           Log.e("yzh","属于IO异常，重试");
                           if(currentRetryCount<maxConnectCount){
                               currentRetryCount++;
                               Log.e("yzh","重试的次数--"+currentRetryCount);
                               waitRetryTime=1000+currentRetryCount*1000;
                               Log.e("yzh","等待时间=="+waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
                           }else{
                               return Observable.error(new Throwable("重试次数已超过设置次数 = " +currentRetryCount  + "，即 不再重试"));

                           }
                       }else{
                           return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                       }

                   }
               });
           }
       }).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Translation>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(Translation translation) {
                       Log.e("yzh",  "发送成功");
                       translation.show();
                   }

                   @Override
                   public void onError(Throwable e) {
                        Log.e("yzh",e.toString());
                   }

                   @Override
                   public void onComplete() {

                   }
               });

    }
}
