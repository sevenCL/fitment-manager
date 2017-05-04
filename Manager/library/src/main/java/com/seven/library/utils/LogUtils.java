package com.seven.library.utils;

import android.os.Environment;

import com.seven.library.config.RunTimeConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志工具辅助类
 *
 * @author seven
 */
public class LogUtils {
    private static LogUtils iInstance;
    private FileOutputStream ifOut;
    private static String KDEBUGTITLE = "seven-->";
    /**
     * 日志保存路径
     */
    public static String LOG_SAVE_PATH = RunTimeConfig.PathConfig.LOG_SAVE_PATH;
    //log开关
    public static boolean isDebug = false;

    public static LogUtils getInstance() {
        if (iInstance == null) {
            iInstance = new LogUtils();
        }
        return iInstance;
    }

    public static void destroy() {
        if (iInstance != null) {
            iInstance.destroyTracer();
            iInstance = null;
        }
    }

    public static void log(Class<?> aClass, String aLog) {
        if (isDebug) {
            StringBuffer sb = new StringBuffer(aLog);
            android.util.Log.d(aClass.getSimpleName(), sb.toString());
        }
    }

    public static void error(String aError) {
        if (isDebug) {
            android.util.Log.e(KDEBUGTITLE, aError);
        }
    }

    public static void println(String msg) {
        if (isDebug) {
            System.out.println(KDEBUGTITLE + msg);
        }
    }

    public static void print(String msg) {
        if (isDebug) {
            System.out.print(KDEBUGTITLE + msg);
        }
    }

    public static void log(String aLog) {
        if (isDebug) {
            StringBuffer sb = new StringBuffer(aLog);
//			sb.append(getTraceInfo(2));
            android.util.Log.d(KDEBUGTITLE, sb.toString());
//			LogUtils.GetInstance().WriteLog(aLog);
        }
    }

    public static void Log(String aLog, int level) {
        if (isDebug) {
            StringBuffer sb = new StringBuffer(aLog);
            for (int i = 0; i <= level; i++) {
                sb.append("\n");
                sb.append("level>>");
                sb.append(i);
                sb.append(":");
                sb.append(getTraceInfo(i));
            }
            android.util.Log.d(KDEBUGTITLE, sb.toString());
        }
    }

    /**
     * 打印异常堆栈信息
     *
     * @param e
     * @return
     */
    public static String getExceptionStackTrace(Throwable e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
        return "";
    }

    public static void printlnTraceInfo(String aLog, int level) {
        if (isDebug) {
            StringBuffer sb = new StringBuffer(aLog);
            for (int i = 0; i <= level; i++) {
                sb.append("\n");
                sb.append("level>>");
                sb.append(i);
                sb.append(":");
                sb.append(getTraceInfo(i));
            }
            System.out.println(sb.toString());
        }
    }

    public static void Log1(String aLog) {
        if (isDebug) {
            StringBuffer sb = new StringBuffer(aLog);
            sb.append(getTraceInfo(1));
            android.util.Log.d(KDEBUGTITLE, sb.toString());
        }
    }

    public static void info(String aLog) {
        if (isDebug) {
//			StringBuffer sb = new StringBuffer(aLog);
//			sb.append(getTraceInfo(2));
            android.util.Log.i(KDEBUGTITLE, aLog);
        }
    }

    public static void info(String tag, String aLog) {
        if (isDebug) {
            android.util.Log.i(tag, aLog);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(tag, msg);
        }
    }

    public static String getTraceInfo(int aLevel) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        int stacksLen = stacks.length;
        if (aLevel > stacksLen - 1) {
            aLevel = stacksLen - 1;
        }
        sb.append(" [class: ").append(stacks[aLevel].getClassName()).append("; method: ").append(stacks[aLevel].getMethodName()).append("; number: ").append(stacks[aLevel].getLineNumber()).append(']');

        return sb.toString();
    }

    /**
     * 插入日志
     */
    public void addLog(String logStr) {

        File file = checkLogFileIsExist();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            logStr = logStr.replaceAll("\n", "\r\n");
            fos.write((new Date().toLocaleString() + "\r\n" + logStr)
                    .getBytes("gbk"));
            fos.write("\r\n".getBytes("gbk"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fos = null;
            file = null;
        }

    }


    /**
     * 检查日志文件是否存在
     */
    private File checkLogFileIsExist() {
        if (!isSDExist()) {
            return null;
        }
        File file = new File(LOG_SAVE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = sdf.format(new Date());
        file = new File(LOG_SAVE_PATH + dateStr + ".txt");
        if (!isLogExist(file)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sdf = null;
        return file;
    }

    /**
     * 检查当天日志文件是否存在
     *
     * @param file
     * @return
     */
    private boolean isLogExist(File file) {
        File tempFile = new File(LOG_SAVE_PATH);
        File[] files = tempFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[0].getName().trim().equalsIgnoreCase(file.getName())) {
                return true;
            }
        }
        return false;
    }

    private void destroyTracer() {
        if (ifOut != null) {
            try {
                ifOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断SD卡是否存在
     * */
    public boolean isSDExist()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


}