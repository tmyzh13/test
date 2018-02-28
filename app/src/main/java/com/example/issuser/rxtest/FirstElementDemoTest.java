package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by issuser on 2018/2/24.
 */

public class FirstElementDemoTest extends Activity {

    String memoryCache=null;
    String diskCache="从磁盘缓存中获取数据";
    String result="数据来源于..";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

//        Observable<String> memory=Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                if(memoryCache!=null){
//                    e.onNext(memoryCache);
//                }else{
//                    e.onComplete();
//                }
//            }
//        });
//
//         Observable<String> disk =Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                if(diskCache!=null){
//                    e.onNext(diskCache);
//                }else{
//                    e.onComplete();
//                }
//            }
//        });
//        Observable<String> network = Observable.just("从网络中获取数据");
//         Observable.concat(disk,memory,network)
//                 .firstElement()
//                 .subscribe(new Consumer<String>() {
//                     @Override
//                     public void accept(String s) throws Exception {
//                         Log.e("yzh","最终的数据来源--"+s);
//                     }
//                 });
//        02-24 14:12:51.159 1276-1276/com.example.issuser.rxtest E/yzh: 最终的数据来源--从磁盘缓存中获取数据


        Observable<String> observable=Observable.just("网络");
        Observable<String> observable1=Observable.just("本地文件");
        Observable.merge(observable,observable1)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("yzh","onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("yzh","数据源有："+s);
                        result+=s+"+";
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("yzh","获取数据完成");
                        Log.e("yzh",result);
                    }
                });
//        02-24 14:25:55.428 3474-3474/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-24 14:25:55.428 3474-3474/com.example.issuser.rxtest E/yzh: 数据源有：网络
//        02-24 14:25:55.428 3474-3474/com.example.issuser.rxtest E/yzh: 数据源有：本地文件
//        02-24 14:25:55.428 3474-3474/com.example.issuser.rxtest E/yzh: 获取数据完成
//        02-24 14:25:55.428 3474-3474/com.example.issuser.rxtest E/yzh: 数据来源于..网络+本地文件+
    }
}
