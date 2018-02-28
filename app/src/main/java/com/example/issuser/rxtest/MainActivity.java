package com.example.issuser.rxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable.concat(Observable.just(1, 2, 3),
//                Observable.just(4, 5, 6),
//                Observable.just(7, 8, 9),
//                Observable.just(10, 11, 12))
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
//                        Log.e("yzh","onError--"+e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
//        Observable.concatArray(Observable.just(1, 2, 3),
//                Observable.just(4, 5, 6),
//                Observable.just(7, 8, 9),
//                Observable.just(10, 11, 12),
//                Observable.just(11,12,13))
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
//                        Log.e("yzh","onError--"+e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
        Observable.merge(Observable.intervalRange(0,3,1,1, TimeUnit.SECONDS),
                Observable.intervalRange(2,3,1,1,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("yzh","onSubScribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e("yzh","onNext--"+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("yzh","onError--"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("yzh","onComplete");
                    }
                });
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("1");
//                e.onNext("2");
//                e.onComplete();
//                e.onNext("3");
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e("yzh","onNext--"+s);
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
       Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> e) throws Exception {
               e.onNext(1);
               e.onNext(2);

               e.onError(new NullPointerException());
           }
       }),Observable.just(1,2,3))
               .subscribe(new Observer<Integer>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(Integer serializable) {
                       Log.e("yzh","onNext--"+serializable);
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.e("yzh","onError-"+e.toString());
                   }

                   @Override
                   public void onComplete() {
                        Log.e("yzh","onComplete");
                   }
               });
        Observable<Integer> observable1 =Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    Log.e("yzh","被观察者1发送事件1");
                    e.onNext(1);
                    Thread.sleep(1000);
                    Log.e("yzh","被观察者1发送事件2");
                    e.onNext(2);
                    Thread.sleep(1000);

//                    e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable2 =Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e("yzh","观察者2发送事件1");
                e.onNext("a");
                Thread.sleep(1000);
                Log.e("yzh","观察者2发送事件2");
                e.onNext("b");
                Thread.sleep(1000);
                Log.e("yzh","被观察者2发送事件3");
                e.onNext("c");
                Thread.sleep(1000);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                Log.e("yzh","apply") ;
                return  integer + string;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("yzh", "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.e("yzh", "onNext =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("yzh", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("yzh", "onComplete");
            }
        });
        Observable.combineLatest(Observable.just(1L, 2L, 3L),
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long integer, Long aLong) throws Exception {
                        Log.e("yzh","合并的对象--"+integer+"--"+aLong);
                        return integer+aLong;
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("yzh","onSubscribe");
            }

            @Override
            public void onNext(Long s) {
                Log.e("yzh","onNext--"+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("yzh","onError--"+e.toString());
            }

            @Override
            public void onComplete() {
                Log.e("yzh","onComplete");
            }
        });
        Observable.just(1,2,3,4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e("yzh","操作数据--"+integer+"---"+integer2);
                        return integer*integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("yzh","接受到的数据--"+integer);
            }
        });
        Observable.just(1,2,3,4,5)
                .collect(new Callable<ArrayList<Integer>>() {

                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                                integers.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                    Log.e("yzh","accept--"+integers.toString());
            }
        });
        Observable.just(4,5,6)
                .startWith(0)
                .startWithArray(1,2,3)
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
                        Log.e("yzh","onError--"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("yzh","onComplete");
                    }
                });
//        Observable.just(1,2,3,4)
//                .count()
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e("yzh","accept--"+aLong);
//                    }
//                });
//        Observable.just(1,2,3)
//                .delay(3,TimeUnit.SECONDS)
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
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onError(new Throwable("发送错误"));
//            }
//        }).doOnEach(new Consumer<Notification<Integer>>() {
//            @Override
//            public void accept(Notification<Integer> integerNotification) throws Exception {
//                Log.e("yzh","doOnEach--"+integerNotification.getValue());
//            }
//        }).doOnNext(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e("yzh","doOnNext--"+integer);
//            }
//        }).doAfterNext(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e("yzh","doAfterNext--"+integer);
//            }
//        }).doOnComplete(new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.e("yzh","doOnComplete");
//            }
//        }).doOnError(new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.e("yzh","doOnError--"+throwable.getMessage());
//            }
//        }).doOnSubscribe(new Consumer<Disposable>() {
//            @Override
//            public void accept(Disposable disposable) throws Exception {
//                Log.e("yzh","doOnSubScribe");
//            }
//        }).doAfterTerminate(new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.e("yzh","doAfterTerminate");
//            }
//        }).doFinally(new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.e("yzh","doFinally");
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("yzh","onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e("yzh","onNext--"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("yzh","onError=="+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("yzh","onComplete");
//            }
//        });
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Throwable("发送错误"));
//            }
//        }).onErrorReturn(new Function<Throwable, Integer>() {
//            @Override
//            public Integer apply(Throwable throwable) throws Exception {
//                Log.e("yzh","onErrorReturn--"+throwable.getMessage());
//                return 666;
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("yzh","onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e("yzh","onNext--"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("yzh","onError"+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("yzh","onComplete");
//            }
//        });
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生了00错误"));
//            }
//        })
//                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
//               Log.e("yzh","onErrorResumeNext--"+throwable.getMessage());
//                return Observable.just(11,22);
//            }
//        })
//            .onExceptionResumeNext(new ObservableSource<Integer>() {
//                @Override
//                public void subscribe(Observer<? super Integer> observer) {
//                    observer.onNext(11);
//                    observer.onNext(22);
//                    observer.onComplete();
//                }
//            })
//                .subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("yzh","onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e("yzh","onNext---"+integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("yzh","onError--"+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("yzh","onComplete");
//            }
//        });
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Throwable("发生错误"));
//                e.onNext(3);
//            }
//        })
//                .retry(3)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("yzh","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("yzh","onNext-"+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("yzh","onError-"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("yzh","onComplete");
//                    }
//                });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误"));
                e.onNext(3);
            }
        }).retry(3, new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                Log.e("yzh","retry--"+throwable.getMessage());
                return true;
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
