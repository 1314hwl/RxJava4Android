package com.crm.rxjava4android;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crm.rxjava4android.databinding.ActivityObserverBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Desc: Rxjava2
 * Author:huweiliang
 * Date: 2017/12/6 11:37
 * Email:2072025612@qq.com
 **/
public class ObserverActivity extends AppCompatActivity {

    ActivityObserverBinding activityObserverBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityObserverBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer);
        activityObserverBinding.rxjava2NormalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(observer);
            }
        });
        activityObserverBinding.justBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //just:将传入的参数依次发送出来。
                Observable observable = Observable.just("Hello", "Hi", "Aloha");
                //subscribe:可直接理解为"被观察者"订阅"观察者"
                observable.subscribe(observer);
            }
        });
        activityObserverBinding.formArrayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] words = {"zhangsan", "lisi", "wangwu"};
//                // from：将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
                Observable observable1 = Observable.fromArray(words);
//                //订阅一个订阅成员
                observable1.subscribe(observer);
            }
        });
        activityObserverBinding.formIteratorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    list.add("Hello" + i);
                }
                Observable<String> observable = Observable.fromIterable((Iterable<String>) list);
                observable.subscribe(observer);
            }
        });
        activityObserverBinding.deferObserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //defer:使推迟,使延期,使服从
                Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> call() throws Exception {
                        return Observable.just("hello");
                    }
                });
                observable.subscribe(observer);

            }
        });

        activityObserverBinding.intervalObserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可用作定时器。即按照固定2秒一次调用onNext()方法。
                final Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
                SystemClock.sleep(5000);//睡眠10秒后，才进行订阅  仍然从0开始，表示Observable内部逻辑刚开始执行
                observable.subscribe(new Observer<Long>() {

                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        this.disposable = disposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.d("interval：" + aLong);
                        if (aLong == 10) {
                            //取消一个订阅
                            this.disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
        activityObserverBinding.rangeObserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上述表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
                Observable<Integer> observable = Observable.range(1, 20);
                observable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.d("range：" + integer);
                    }
                });
            }
        });

        activityObserverBinding.timerObserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //即表示延迟2秒后，调用onNext()方法
                Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS);
                observable.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long mLong) throws Exception {
                        LogUtils.d("timer：" + mLong);
                    }
                });
            }
        });
        activityObserverBinding.repeatObserverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Integer> observable = Observable.just(123).repeat();
                observable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.d("repeat：" + integer);
                    }
                });
            }
        });
        activityObserverBinding.iamgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayImage();
            }
        });
    }


    private void displayImage() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> observableEmitter) throws Exception {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                observableEmitter.onNext(drawable);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        activityObserverBinding.imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            //执行一些其他操作
            //.............
            //执行完毕，触发回调，通知观察者
            e.onNext("Hello Rxjava2...");
        }
    });
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            LogUtils.d("abserver Disposable... ");
        }

        @Override
        //观察者接收到通知,进行相关操作
        public void onNext(String aLong) {
            LogUtils.d("abserver onNext: " + aLong);
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d("abserver Disposable... " + e.getMessage());
        }

        @Override
        public void onComplete() {
            LogUtils.d("abserver onComplete... ");
        }
    };

}
