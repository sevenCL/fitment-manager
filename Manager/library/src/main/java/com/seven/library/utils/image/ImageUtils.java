package com.seven.library.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import com.seven.library.config.RunTimeConfig;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 *
 * 图片工具辅助类
 *
 * @author seven
 */
public class ImageUtils {

    private static volatile ImageUtils imageUtil;

    public ImageUtils() {

    }

    public static ImageUtils getInstance() {

        if (imageUtil == null) {

            synchronized (ImageUtils.class) {
                imageUtil = new ImageUtils();
            }

        }

        return imageUtil;
    }

    /**
     * 加载本地图片
     *
     * @param path
     * @return
     */
    public Bitmap getLocalBitmap(String path) {
        String buff = path.replace("file://", "");
        // 进一步判断文件是否存在
        File check = new File(buff);
        // 本地图片路径不存在，返回null
        if (!check.exists()) {
            return null;
        }
        // 读取图片
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = false;
            // 表示16位位图,565代表对应三原色占的位数
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            newOpts.inInputShareable = true;
            newOpts.inPurgeable = true;// 设置图片可以被回收
            return BitmapFactory.decodeFile(buff, newOpts);
        } catch (Exception e) {
            e.printStackTrace();
            // 读取图片出错时返回null
            return null;
        }
    }

    /**
     * 保存图片到本地
     *
     * @param mPath
     * @param bitmap
     * @return
     */
    public boolean savePicture(String mPath, Bitmap bitmap) {
        boolean saveComplete = true;

        File f = new File(mPath);

        try {
            f.createNewFile();

            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);

            bitmap.compress(Bitmap.CompressFormat.PNG, 40, fOut);

            fOut.flush();
            fOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveComplete;
    }

    /**
     * 保存图片到本地
     *
     * @param mPath
     * @param mBitmap
     * @param screenWidth
     * @return
     * @throws IOException
     */
    public boolean savePicture(String mPath, Bitmap mBitmap, int screenWidth) {
        boolean saveComplete = true;

        File f = new File(mPath);

        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            // 计算缩放的比例
            int finalWidth = screenWidth;
            int finalHeight = (int) ((float) finalWidth * ((float) height / (float) width));
            double x = width * finalHeight;
            double y = height * finalWidth;

            if (x > y) {
                finalHeight = (int) (y / (double) width);
            } else if (x < y) {
                finalWidth = (int) (x / (double) height);
            }

            if (finalWidth > width && finalHeight > height) {
                finalWidth = width;
                finalHeight = height;
            }
            Matrix matrix = new Matrix();
            matrix.reset();
            // 计算宽高缩放率
            float scaleWidth = ((float) finalWidth) / (float) width;
            float scaleHeight = ((float) finalHeight) / (float) height;
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            saveComplete = false;
            e.printStackTrace();
        } catch (IOException e) {
            saveComplete = false;
            e.printStackTrace();
        } catch (IllegalStateException e) {
            saveComplete = false;
            e.printStackTrace();
        }
        return saveComplete;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param path
     */
    public void isExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获得图片保存路径
     *
     * @return
     */
    public String getSavePath() {

        String dir = RunTimeConfig.PathConfig.TEMP_PATH;
        isExists(dir);

        return dir + System.currentTimeMillis() + ".png";
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param max
     * @return
     */
    public Bitmap compressImage(Bitmap image, int max) {

        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        while (baos.toByteArray().length / 1024 > max) {  //循环判断如果压缩后图片是否大于max kb,大于继续压缩
            if (options == 10)
                break;
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param max
     * @return
     */
    public Bitmap radioImage(Bitmap image, int max) {

        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int radio = (int) ((((double) max / (baos.toByteArray().length / 1024.00))) * 100.00);

        if (radio > 0 && radio <= 100) {
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, radio, baos);//这里压缩options%，把压缩后的数据存放到baos中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

            return bitmap;
        }
        return image;
    }


    /**
     * 像素压缩
     *
     * @param srcPath
     * @return
     */
    public Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
        float hh = 1280f;//这里设置高度为1280f
        float ww = 720f;//这里设置宽度为720f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 创建图片
     *
     * @param path
     * @return
     */
    public Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        FileInputStream is = null;
        Bitmap bitmap = null;
        try {
            is = new FileInputStream(path);
            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }


    //压缩图片尺寸
    public Bitmap compressBySize(String pathName, int targetWidth,
                                 int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

        // 得到图片的宽度、高度；
        float imgWidth = options.outWidth;
        float imgHeight = options.outHeight;

        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        options.inSampleSize = 1;

        // 如果尺寸接近则不压缩，否则进行比例压缩
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                options.inSampleSize = widthRatio;
            } else {
                options.inSampleSize = heightRatio;
            }
        }

        //设置好缩放比例后，加载图片进内容；
        options.inJustDecodeBounds = false; // 这里一定要设置false
        bitmap = BitmapFactory.decodeFile(pathName, options);
        return bitmap;
    }

    public Bitmap compressBySizeBitmap(String pathName, int targetWidth,
                                       int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

        // 得到图片的宽度、高度；
        float imgWidth = options.outWidth;
        float imgHeight = options.outHeight;

        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        options.inSampleSize = 1;

        // 如果尺寸接近则不压缩，否则进行比例压缩
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                options.inSampleSize = widthRatio;
            } else {
                options.inSampleSize = heightRatio;
            }
        }

        //设置好缩放比例后，加载图片进内容；
        options.inJustDecodeBounds = false; // 这里一定要设置false
        bitmap = BitmapFactory.decodeFile(pathName, options);
        return bitmap;
    }

    /**
     * 根据路径压缩图片并保持
     *
     * @param savePath
     * @param srcPath
     * @return
     */
    public boolean setImage(String savePath, String srcPath,int height,int width) {

        boolean saveComplete = true;

        File file = new File(savePath);

        try {
            // 压缩图片
            Bitmap bitmap = compressBySize(srcPath, height, width);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return saveComplete;

    }

    public String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录
        } else {
            context.getCacheDir().getAbsolutePath(); // 获取内置内存卡目录
        }
        return sdDir.toString();
    }

    /**
     * 根据bitmap压缩图片并保存
     *
     * @param savePath
     * @param bitmap
     * @return
     */
    public boolean setImage(String savePath, Bitmap bitmap) {

        boolean saveComplete = true;

        File file = new File(savePath);

        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return saveComplete;
    }

    /**
     * 根据路径加载bitmap
     *
     * @param path 路径
     * @param w    宽
     * @param h    长
     * @return
     */
    public final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            // 返回为空
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                // 缩放
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
