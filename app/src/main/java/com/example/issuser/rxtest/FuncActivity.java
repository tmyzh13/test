package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by issuser on 2018/2/23.
 */

public class FuncActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func);

        //delay使得被观察者延迟一段时间发送
        Observable.just(1,2,3)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("yzh","onSubscribe");
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

                    }
                });
//        02-23 15:21:24.146 9436-9436/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 15:21:27.148 9436-9478/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 15:21:27.148 9436-9478/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 15:21:27.148 9436-9478/com.example.issuser.rxtest E/yzh: onNext--3


      Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                //当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Log.e("yzh","doOnEach---"+integerNotification.getValue());
            }
        })
                //执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("yzh","doOnNext--"+integer);
                    }
                })
                //执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("yzh","doAfterNext--"+integer);
                    }
                })
                //observable发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("yzh","doOnComplete");
                    }
                })
                //observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("yzh","doOnError--"+throwable.getMessage());
                    }
                })
                //观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.e("yzh","donOnSubscribe");
                    }
                })
                //observable发送事件完毕后调用 无论正常发送完毕/异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("yzh","doAfterTerminate");
                    }
                })
                //最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("yzh","doFinally");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
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

                    }
                });
//
//        02-23 11:27:09.946 31016-31016/com.example.issuser.rxtest E/yzh: donOnSubscribe
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnEach---1
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnNext--1
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doAfterNext--1
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnEach---2
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnNext--2
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doAfterNext--2
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnEach---3
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnNext--3
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: onNext--3
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doAfterNext--3
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnEach---null
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doOnError--发生错误了
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doFinally
//        02-23 11:27:09.947 31016-31016/com.example.issuser.rxtest E/yzh: doAfterTerminate

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Throwable("发生错误了"));
//            }
//        })
//                //遇到错误时，发送一个特殊事件&正常终止
//                .onErrorReturn(new Function<Throwable, Integer>() {
//                    @Override
//                    public Integer apply(Throwable throwable) throws Exception {
//                        Log.e("yzh","onErrorReturn--"+throwable.getMessage());
//
//                        return 123;
//                    }
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                            Log.e("yzh","onNext--"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError--"+e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
//        02-23 11:46:07.620 2761-2761/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 11:46:07.620 2761-2761/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 11:46:07.620 2761-2761/com.example.issuser.rxtest E/yzh: onErrorReturn--发生错误了
//        02-23 11:46:07.620 2761-2761/com.example.issuser.rxtest E/yzh: onNext--123
//        02-23 11:46:07.620 2761-2761/com.example.issuser.rxtest E/yzh: onComplete

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//            }
//        })
//                //遇到错误时，发送1个新的Observable 不分Throwable 和Exception
//                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
//                    @Override
//                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
//                        Log.e("yzh","onErrorResumeNext--"+throwable.getMessage());
//                        return Observable.just(3,4);
//                    }
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("yzh","onNext--"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError--"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        //throwable
//        02-23 11:59:30.439 5596-5596/? E/yzh: onNext--1
//        02-23 11:59:30.439 5596-5596/? E/yzh: onNext--2
//        02-23 11:59:30.439 5596-5596/? E/yzh: onErrorResumeNext--发生错误了
//        02-23 11:59:30.439 5596-5596/? E/yzh: onNext--3
//        02-23 11:59:30.439 5596-5596/? E/yzh: onNext--4

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//            }
//        })
//                .onExceptionResumeNext(new ObservableSource<Integer>() {
//                    @Override
//                    public void subscribe(Observer<? super Integer> observer) {
//                        observer.onNext(11);
//                        observer.onNext(22);
//                        observer.onComplete();
//                    }
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("yzh","onNext--"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError--"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
//        Exception
//         onNext--1
//         onNext--2
//        onNext--1
//        onNext--2
//        onComplete
//        Throwable
//        02-23 14:38:58.163 30725-30725/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 14:38:58.163 30725-30725/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 14:38:58.163 30725-30725/com.example.issuser.rxtest E/yzh: onError--发生错误了

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onNext(3);
//            }
//        })
//              .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//                  // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
//                  // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
//                  @Override
//                  public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                     return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                      @Override
//                      public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
//
//                          // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
//                          // 该异常错误信息可在观察者中的onError（）中获得
//                          return Observable.error(new Throwable("retryWhen终止啦"));
//
//                          // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
////                           return Observable.just(1);
//                      }
//                  });
//
//
//                  }
//              })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("yzh","onNext-"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError--"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });

//        3 14:45:31.130 31188-31188/com.example.issuser.rxtest E/yzh: onNext-1
//        02-23 14:45:31.130 31188-31188/com.example.issuser.rxtest E/yzh: onNext-2
//        02-23 14:45:31.130 31188-31188/com.example.issuser.rxtest E/yzh: onNext-1
//        02-23 14:45:31.130 31188-31188/com.example.issuser.rxtest E/yzh: onNext-2
//        .....

//        Observable.just(1,2,3)
//                .repeat(3)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("yzh","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("yzh","onNext--"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError--"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
//        02-23 15:06:32.213 6543-6543/com.example.issuser.rxtest E/yzh: onSubscribe
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--3
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--3
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--1
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--2
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onNext--3
//        02-23 15:06:32.214 6543-6543/com.example.issuser.rxtest E/yzh: onComplete

        Observable.just(1,2,3)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                return Observable.empty();
                            }
                        });
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("yzh","onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e("yzh","onNext--"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("yzh","onError--"+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("yzh","onComplete");
            }
        });
    }

}
