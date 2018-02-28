package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by issuser on 2018/2/7.
 */

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

//        Observable.intervalRange(0,3,1,1, TimeUnit.SECONDS)
//                .doOnNext(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e("yzh","第"+aLong+"查询");
//                        Retrofit retrofit=new Retrofit.Builder()
//                                .baseUrl(Urls.baseUrl)
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                                .build();
//                        GetRequest request=retrofit.create(GetRequest.class);
//                        Observable<Translation> observable=request.getCall();
//                        observable.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Observer<Translation>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//
//                                    }
//
//                                    @Override
//                                    public void onNext(Translation translation) {
//                                            translation.show();
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("yzh","网络问题--"+e.toString());
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//                                        Log.e("yzh","onComplete");
//                                    }
//                                });
//                    }
//                }).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("yzh","onSubscribe");
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.e("yzh","onNext--"+aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("yzh","onError--"+e.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("yzh","onComplete");
//            }
//        });
//        02-07 10:40:17.703 26342-26342/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-07 10:40:17.934 26342-26373/com.example.issuser.rxtest I/OpenGLRenderer: Initialized EGL, version 1.4
//        02-07 10:40:17.949 26342-26373/com.example.issuser.rxtest W/linker: /vendor/lib64/libhwuibp.so: unused DT entry: type 0xf arg 0xe3a
//        02-07 10:40:18.704 26342-26370/com.example.issuser.rxtest E/yzh: 第0查询
//        02-07 10:40:18.802 26342-26370/com.example.issuser.rxtest E/yzh: onNext--0
//        02-07 10:40:19.022 26342-26342/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-07 10:40:19.022 26342-26342/com.example.issuser.rxtest E/yzh: onComplete
//        02-07 10:40:19.704 26342-26370/com.example.issuser.rxtest E/yzh: 第1查询
//        02-07 10:40:19.709 26342-26370/com.example.issuser.rxtest E/yzh: onNext--1
//        02-07 10:40:19.895 26342-26342/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-07 10:40:19.895 26342-26342/com.example.issuser.rxtest E/yzh: onComplete
//        02-07 10:40:20.706 26342-26370/com.example.issuser.rxtest E/yzh: 第2查询
//        02-07 10:40:20.716 26342-26370/com.example.issuser.rxtest E/yzh: onNext--2
//        02-07 10:40:20.716 26342-26370/com.example.issuser.rxtest E/yzh: onComplete
//        02-07 10:40:20.989 26342-26342/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-07 10:40:20.990 26342-26342/com.example.issuser.rxtest E/yzh: onComplete

//        Observable<Integer> observable =Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.e("yzh","被观察者所在的线程--"+Thread.currentThread().getName());
//                e.onNext(1);
//                e.onComplete();
//            }
//        });
//
//        Observer<Integer> observer=new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                    Log.e("yzh","onSubscribe--观察者所在的线程--"+Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onNext(Integer o) {
//                Log.e("yzh","onNext--"+o);
//            }
//
//
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
//        };
//        observable.subscribe(observer);
//        02-07 10:53:38.484 27661-27661/com.example.issuser.rxtest E/yzh: onSubscribe--观察者所在的线程--main
//        02-07 10:53:38.484 27661-27661/com.example.issuser.rxtest E/yzh: 被观察者所在的线程--main
//        02-07 10:53:38.485 27661-27661/com.example.issuser.rxtest E/yzh: onNext--1
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//        02-07 10:57:17.625 28014-28014/? E/yzh: onSubscribe--被观察者所在的线程--main
//        02-07 10:57:17.626 28014-28040/? E/yzh: 被观察者所在的线程--RxNewThreadScheduler-1
//        02-07 10:57:17.644 28014-28014/? E/yzh: onNext--1
//        observable.subscribeOn(Schedulers.newThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//        02-07 11:05:33.148 28916-28916/com.example.issuser.rxtest E/yzh: onSubscribe--观察者所在的线程--main
//        02-07 11:05:33.169 28916-28961/com.example.issuser.rxtest E/yzh: 被观察者所在的线程--RxNewThreadScheduler-1
//        02-07 11:05:33.220 28916-28916/com.example.issuser.rxtest E/yzh: onNext--1
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e("yzh","第一次观察者所在的线程--"+Thread.currentThread().getName());
//                    }
//                }).observeOn(Schedulers.newThread())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e("yzh","第二次观察者所在的线程--"+Thread.currentThread().getName());
//                    }
//                })
//                .subscribe(observer);
//        02-07 11:17:01.133 29576-29576/com.example.issuser.rxtest E/yzh: onSubscribe--观察者所在的线程--main
//        02-07 11:17:01.134 29576-29603/com.example.issuser.rxtest E/yzh: 被观察者所在的线程--RxNewThreadScheduler-1
//        02-07 11:17:01.150 29576-29576/com.example.issuser.rxtest E/yzh: 第一次观察者所在的线程--main
//        02-07 11:17:01.150 29576-29605/com.example.issuser.rxtest E/yzh: 第二次观察者所在的线程--RxNewThreadScheduler-2
//        02-07 11:17:01.150 29576-29605/com.example.issuser.rxtest E/yzh: onNext--1

//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(Urls.baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        GetRequest request =retrofit.create(GetRequest.class);
//        Observable<Translation> observable=request.getCall();
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Translation>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                            Log.e("yzh","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Translation translation) {
//                            translation.show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                            Log.e("yzh","onError-"+e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                            Log.e("yzh","onComplete");
//                    }
//                });
//        02-07 11:38:03.833 31202-31202/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-07 11:38:04.349 31202-31202/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-07 11:38:04.349 31202-31202/com.example.issuser.rxtest E/yzh: onComplete

//        防止activity结束 出现问题
//        private final CompositeDisposable disposables = new CompositeDisposable();
//        disposables.add(observer);
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            // 将所有的 observer 取消订阅
//            disposables.clear();
//        }

        //merge在不使用intervalRanger时与concat效果一直，concat用intervalRanger时依旧
//        Observable observable1=Observable.just("1","2");
//        Observable observable2=Observable.just("a","b");
//        Observable.merge(observable1,observable2)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("yzh","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(String o) {
//                        Log.e("yzh","onNext--"+o);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        Observable<Integer> observable1 =Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.e("yzh","观察者1所在线程--"+Thread.currentThread().getName());
//                Log.e("yzh","被观察者1发送事件1");
//                e.onNext(1);
//                Thread.sleep(1000);
//                Log.e("yzh","被观察者1发送事件2");
//                e.onNext(2);
//                Thread.sleep(1000);
//
////                    e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//        Observable<String> observable2 =Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.e("yzh","观察者2所在线程-"+Thread.currentThread().getName());
//                Log.e("yzh","观察者2发送事件1");
//                e.onNext("a");
//                Thread.sleep(1000);
//                Log.e("yzh","观察者2发送事件2");
//                e.onNext("b");
//                Thread.sleep(1000);
//                Log.e("yzh","被观察者2发送事件3");
//                e.onNext("c");
//                Thread.sleep(1000);
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String string) throws Exception {
//                Log.e("yzh","apply") ;
//                return  integer + string;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("yzh", "onSubscribe");
//            }
//
//            @Override
//            public void onNext(String value) {
//                Log.e("yzh", "onNext =  " + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("yzh", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("yzh", "onComplete");
//            }
//        });
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetRequest getRequest=retrofit.create(GetRequest.class);
        Observable<Translation> observable1=getRequest.getCall().subscribeOn(Schedulers.io());
        Observable<Translation> observable2=getRequest.getCall1().subscribeOn(Schedulers.io());
        Observable.zip(observable1,observable2, new BiFunction<Translation, Translation, String>() {
            @Override
            public String apply(Translation translation, Translation translation2) throws Exception {
                return translation.show()+"---"+translation2.show();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                            Log.e("yzh","onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                            Log.e("yzh","onNext--"+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        02-07 14:48:14.963 19576-19576/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-07 14:48:15.025 19576-19651/com.example.issuser.rxtest I/OpenGLRenderer: Initialized EGL, version 1.4
//        02-07 14:48:15.029 19576-19651/com.example.issuser.rxtest W/linker: /vendor/lib64/libhwuibp.so: unused DT entry: type 0xf arg 0xe3a
//        02-07 14:48:15.245 19576-19648/com.example.issuser.rxtest E/yzh: 嗨世界
//        02-07 14:48:15.245 19576-19648/com.example.issuser.rxtest E/yzh: hi china
//        02-07 14:48:15.245 19576-19576/com.example.issuser.rxtest E/yzh: onNext--嗨世界---hi china

    }
}
