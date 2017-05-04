package com.seven.library.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.seven.library.R;

import java.io.File;

/**
 *
 * 图片加载类
 *
 * @author seven
 */
public class ImageLoadProxy {

    private static final int MAX_DISK_CACHE = 1024 * 1024 * 50;
    private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;

    private static boolean isShowLog = false;

    private volatile static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {

        if (imageLoader == null) {
            synchronized (ImageLoadProxy.class) {
                imageLoader = ImageLoader.getInstance();
            }
        }

        return imageLoader;
    }

    public static void initImageLoader(Context context, File cacheDir) {
        ImageLoaderConfiguration.Builder build = new ImageLoaderConfiguration.Builder(context);
        build.tasksProcessingOrder(QueueProcessingType.LIFO);
        build.diskCacheSize(MAX_DISK_CACHE);
        build.memoryCacheSize(MAX_MEMORY_CACHE);
        build.memoryCache(new LruMemoryCache(MAX_MEMORY_CACHE));
        build.diskCache(new UnlimitedDiskCache(cacheDir));

        if (isShowLog) {
            build.writeDebugLogs();
        }
        getImageLoader().init(build.build());
    }

    /**
     * 自定义Option
     *
     * @param url
     * @param target
     * @param options
     */
    public static void displayImage(String url, ImageView target, DisplayImageOptions options) {
        imageLoader.displayImage(url, target, options);
    }


    /**
     * 头像专用
     *
     * @param url
     * @param target
     */
    public static void displayHeadIcon(String url, ImageView target) {

        imageLoader.displayImage(url, target, getOptions4Header());

    }

    /**
     * 图片详情页专用
     *
     * @param url
     * @param target
     * @param loadingListener
     */
    public static void displayImage4Detail(String url, ImageView target, SimpleImageLoadingListener loadingListener) {
        imageLoader.displayImage(url, target, getOption4ExactlyType(), loadingListener);
    }

    /**
     * 图片列表页专用
     *
     * @param url
     * @param target
     * @param loadingResource
     * @param loadingListener
     * @param progressListener
     */
    public static void displayImageList(String url, ImageView target, int loadingResource, int loadingFail,
                                        SimpleImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource, loadingFail), loadingListener, progressListener);
    }

    /**
     * 自定义加载中图片
     *
     * @param url
     * @param target
     * @param loadingResource
     */
    public static void displayImageWithLoadingPicture(String url, ImageView target, int loadingResource, int loadingFail) {
        imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource, loadingFail));
    }

    /**
     * 当使用WebView加载大图的时候，使用本方法现下载到本地然后再加载
     *
     * @param url
     * @param loadingListener
     */
    public static void loadImageFromLocalCache(String url, SimpleImageLoadingListener loadingListener) {
        imageLoader.loadImage(url, getOption4ExactlyType(), loadingListener);
    }

    /**
     * 设置图片放缩类型为模式EXACTLY，用于图片详情页的缩放
     *
     * @return
     */
    public static DisplayImageOptions getOption4ExactlyType() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    /**
     * 加载头像专用Options，默认加载中、失败和空url为 ic_loading_small
     *
     * @return
     */
    public static DisplayImageOptions getOptions4Header() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.drawable.ic_loading_small)
                .showImageOnFail(R.drawable.ic_loading_small)
                .showImageOnLoading(R.drawable.ic_loading_small)
                .displayer(new RoundedBitmapDisplayer(360))
                .build();
    }

    /**
     * 加载图片列表专用，加载前会重置View
     * {@link DisplayImageOptions.Builder#resetViewBeforeLoading} = true
     *
     * @param loadingResource
     * @return
     */
    public static DisplayImageOptions getOptions4PictureList(int loadingResource, int loadingFail) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(loadingResource)
                .showImageForEmptyUri(loadingResource)
                .showImageOnFail(loadingFail)
                .build();
    }

}
