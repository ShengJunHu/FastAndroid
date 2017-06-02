package com.hsj.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author:HSJ
 * @E-mail:mr.ajun@foxmail.com
 * @Date:2017/5/27 16:39
 * @Class:Xlog
 * @Description: 日志工具类
 */
public class Xlog {

    private static String TAG           = "[★XLog★]";  // 默认的 TAG

    private static boolean isDebug      = BuildConfig.DEBUG;

    private static boolean LOG_SWITCH   = isDebug;   // 日志文件总开关

    private static boolean LOG_TO_FILE  = isDebug;   // 日志写入文件开关，默认false

    private static char LOG_TYPE = 'v';              // 输入日志类型，v代表输出所有信息,w输出警告...

    private static int LOG_SAVE_DAYS = 3;            // sd卡中日志文件的最多保存天数

    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat LOG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日志的输出格式

    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat FILE_SUFFIX = new SimpleDateFormat("yyyy-MM-dd");         // 日志文件格式

    private static String LOG_FILE_PATH;            // 日志文件保存路径

    private static String LOG_FILE_NAME = "log";    // 日志文件保存名称

    /**
     * 在项目中主模块中初始化
     * @param context
     */
    public static void init(Context context) {
        LOG_FILE_PATH = getCacheDir(context);

        deleteLogFile();
    }

    /**************************************** Warn：警告 *******************************************/
    public static void w(Object msg) {
        w(TAG, msg);
    }

    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /************************************* Error： 错误 ********************************************/
    public static void e(Object msg) {
        e(TAG, msg);
    }

    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /************************************** Debug： 调试 *******************************************/
    public static void d(Object msg) {
        d(TAG, msg);
    }

    public static void d(String tag, Object msg) {
        d(tag, msg, null);
    }

    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**************************************** Info ************************************************/
    public static void i(Object msg) {
        i(TAG, msg);
    }

    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /*************************************** Verbose **********************************************/
    public static void v(Object msg) {
        v(TAG, msg);
    }

    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, Throwable tr, char level) {
        if (LOG_SWITCH) {//输出日志开关
            if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) {         //e输出优先级最高
                Log.e(tag, msg, tr);
            } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {  //w输出优先级第二
                Log.w(tag, msg, tr);
            } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {  //d输出优先级第三
                Log.d(tag, msg, tr);
            } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {  //i输出有限级第四
                Log.i(tag, msg, tr);
            } else {
                Log.v(tag, msg, tr);                                            //v输出优先最低
            }

            if (LOG_TO_FILE){//日志写入文件开关
                log2File(String.valueOf(level), tag, msg + tr == null ? "" : "\n" + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 将日志写入文件
     *
     * @param logType log类型（e\w\d\i\v）
     * @param tag
     * @param text
     */
    private synchronized static void log2File(String logType, String tag, String text) {
        Date currentTime = new Date();
        String date = FILE_SUFFIX.format(currentTime);
        String dateLogContent = LOG_FORMAT.format(currentTime) + ":" + logType + ":" + tag + ":" + text; // 日志输出格式
        File destDir = new File(LOG_FILE_PATH);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File(LOG_FILE_PATH+"/log", LOG_FILE_NAME + date);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(dateLogContent);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定的日志文件
     */
    private static void deleteLogFile() {
        String deleteLog = FILE_SUFFIX.format(getDateBefore());
        File file = new File(LOG_FILE_PATH, deleteLog + LOG_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到LOG_SAVE_DAYS天前的日期
     *
     * @return
     */
    private static Date getDateBefore() {
        Date time = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - LOG_SAVE_DAYS);
        return now.getTime();
    }

    /**
     * SD卡是否可用
     *
     * @return
     */
    public static boolean isSdUsable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取根缓存目录: 优先获取SD卡目录
     *
     * @return
     */
    public static String getCacheDir(Context context) {
        String cachePath;
        if (isSdUsable()) {
            //路径： /sdcard/Android/data/(PackageName)/cache
            File externalCacheDir = context.getExternalCacheDir();
            if(externalCacheDir!=null){
                cachePath =  externalCacheDir.getPath();
            }else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            //路径： /data/data/(PackageName)/cache
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

}
