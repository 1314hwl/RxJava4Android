package com.crm.rxjava4android;

import android.app.Application;

/**
 * Created by User on 2017/12/6.
 */

public class RxJavaApplication extends Application {


    public static boolean isDebug = true;//测试时请置为true


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLog();
    }

    public void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(isDebug)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(isDebug)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(isDebug)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setBorderSwitch(isDebug)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V);// log文件过滤器，和logcat过滤器同理，默认Verbose
        LogUtils.d(config.toString());
    }
}
