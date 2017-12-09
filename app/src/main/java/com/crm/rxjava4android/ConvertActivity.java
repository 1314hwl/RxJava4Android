package com.crm.rxjava4android;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crm.rxjava4android.databinding.ActivityConvertBinding;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


public class ConvertActivity extends AppCompatActivity {

    private ActivityConvertBinding activityConvertBinding;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 5; i++) {
            list.add("Hello" + i);
        }
        activityConvertBinding = DataBindingUtil.setContentView(this, R.layout.activity_convert);
        activityConvertBinding.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//map()操作符，就是把原来的Observable对象转换成另一个Observable对象，同时将传输的数据进行一些灵活的操作，方便Observer获得想要的数据形式。
                Observable<Integer> observable = Observable.just("hello").map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                });
                observable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        activityConvertBinding.mapBtn.setText("map + function : hello length =" + integer);
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

        activityConvertBinding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Object> observable = Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                });
                Observer<Object> observer = new Observer<Object>() {

                    private StringBuilder sb = new StringBuilder();

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Object o) {
                        sb.append((String) o);
                        sb.append(",");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        activityConvertBinding.button5.setText(sb.toString());
                    }
                };
                observable.subscribe(observer);

            }
        });
        activityConvertBinding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //filter()操作符根据test()方法中，根据自己想过滤的数据加入相应的逻辑判断，返回true则表示数据满足条件，返回false则表示数据需要被过滤。最后过滤出的数据将加入到新的Observable对象中，方便传递给Observer想要的数据形式。
                Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                }).filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object s) throws Exception {
                        String newStr = (String) s;
                        if (newStr.charAt(5) - '0' < 3) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new Observer<Object>() {
                    private StringBuilder sb = new StringBuilder();

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Object o) {
                        sb.append((String) o);
                        sb.append(",");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        activityConvertBinding.button6.setText(sb.toString());
                    }
                });
            }
        });
        activityConvertBinding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take()操作符：输出最多指定数量的结果。
                Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                }).take(2).subscribe(new Observer<Object>() {
                    private StringBuilder sb = new StringBuilder();

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Object o) {
                        sb.append((String) o);
                        sb.append(",");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        activityConvertBinding.button7.setText(sb.toString());
                    }
                });


            }
        });
        activityConvertBinding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                }).take(5).doOnNext(new Consumer<Object>() {
                    //doOnNext()允许我们在每次输出一个元素之前做一些额外的事情。
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("准备工作" + (String) o);
                    }
                }).subscribe(new Observer<Object>() {
                    private StringBuilder sb = new StringBuilder();

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Object o) {
                        sb.append((String) o);
                        sb.append(",");
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        activityConvertBinding.button8.setText(sb.toString());
                    }
                });


            }
        });
    }

    private void testMap() {
//        String filePath = SDCardUtils.getSDCardPath() + "rhcloud/Images/5a0bf6e22aadf.png";
//        Observable.just(filePath) // 输入类型 String
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) { // 参数类型 String
//                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
//                        LogUtils.d(bitmap);
//                        activityConvertBinding.testIv.setImageBitmap(bitmap);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//
//                    }
//                });
    }

    public Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
    }


}
