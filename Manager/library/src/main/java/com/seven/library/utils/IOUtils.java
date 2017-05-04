package com.seven.library.utils;

import android.text.TextUtils;

import com.seven.library.application.LibApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件读写工具辅助类
 *
 * @author seven
 */
public class IOUtils {

    private static volatile IOUtils ioUtils;

    private IOUtils() {

    }

    public static IOUtils getInstance() {

        if (ioUtils == null) {

            synchronized (IOUtils.class) {

                ioUtils = new IOUtils();

            }

        }

        return ioUtils;

    }


    /**
     * @param dirsPath 文件夹路径
     * @return 返回File
     */
    public File checkDirs(String dirsPath) {

        if (TextUtils.isEmpty(dirsPath))
            return null;

        File file = new File(dirsPath);

        if (!file.exists())
            file.mkdirs();

        return file;
    }

    /**
     * 删除文件夹所有内容
     */
    public void deleteFile(File file) {

        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            //
        }
    }

    /**
     * 复制文件
     *
     * @param rawId    资源文件夹raw下的文件id
     * @param savePath copy的路径
     * @param name     保存的文件名
     * @param coverage 文件存在的情况下是否覆盖,true-覆盖  false-不覆盖
     * @return 返回boolean
     */
    public boolean rawCopy(int rawId, String savePath, String name, boolean coverage) {

        boolean succeed = true;

        File file = new File(checkDirs(savePath), name);

        if (file.exists() && !coverage)
            return succeed;

        InputStream in = null;
        FileOutputStream os = null;
        try {
            in = LibApplication.getInstance().getResources().openRawResource(rawId);

            os = new FileOutputStream(file);

            int temp = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((temp = in.read(buffer)) != -1) {
                os.write(buffer, 0, temp);
                os.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            succeed = false;
            LogUtils.println("RawIO rawCopy!" + e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (os != null)
                    os.close();

            } catch (Exception e) {
                e.printStackTrace();
                succeed = false;
                LogUtils.println("RawIO rawCopy close!" + e);
            }
        }

        return succeed;
    }

}
