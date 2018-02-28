package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by issuser on 2018/2/24.
 */

public class RepeatWhenDemoActivity extends Activity {

    //模拟轮询服务器次数
    private int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest request=retrofit.create(GetRequest.class);
        Observable<Translation> observable=request.getCall1();

        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if(i>3){
                            return Observable.error(new Throwable("轮询结束"));
                        }else{
                            return Observable.just(1).delay(2, TimeUnit.SECONDS);
                        }

                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("yzh","onSubscribe");
                    }

                    @Override
                    public void onNext(Translation translation) {
                        translation.show();
                        i++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("yzh","onError--"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        02-24 15:25:00.524 12910-12910/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-24 15:25:00.715 12910-12910/com.example.issuser.rxtest E/yzh: hi china
//        02-24 15:25:02.852 12910-12910/com.example.issuser.rxtest E/yzh: hi china
//        02-24 15:25:05.092 12910-12910/com.example.issuser.rxtest E/yzh: hi china
//        02-24 15:25:07.244 12910-12910/com.example.issuser.rxtest E/yzh: hi china
//        02-24 15:25:09.382 12910-12910/com.example.issuser.rxtest E/yzh: hi china
//        02-24 15:25:09.395 12910-12910/com.example.issuser.rxtest E/yzh: onError--java.lang.Throwable: 轮询结束
    }
}
