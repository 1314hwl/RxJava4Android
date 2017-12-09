package com.crm.rxjava4android;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.crm.rxjava4android.databinding.ActivitySchedulerBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:  Scheduler
 * Author:Huweiliang
 * Date: 2017/12/9 15:34
 * Email:2072025612@qq.com
 **/
public class SchedulerActivity extends AppCompatActivity {

    ActivitySchedulerBinding schedulerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schedulerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scheduler);
        schedulerBinding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        LogUtils.d("abservable 所在的线程=" + Thread.currentThread().getName());
                        LogUtils.d("abservable 发送的数据=" + 1 + "");
                        e.onNext(1);
                    }
                }).subscribeOn(Schedulers.io())//被观察中操作的内容在io线程
                        .observeOn(AndroidSchedulers.mainThread())//观察者中返回的数据在主线程中操作
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                LogUtils.d("所在的线程=" + Thread.currentThread().getName());
                                LogUtils.d("接收到的数据=", "integer:" + integer);
                            }
                        });

            }
        });

        schedulerBinding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observer<String> observer = new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
            }
        });
        schedulerBinding.button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = Observable.just("你好").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.d(s);
                    }
                });


            }
        });
    }
}
