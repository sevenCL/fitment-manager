package com.seven.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * 权限配置类
 *
 * @author seven
 */
public class PermissionUtils {

    private static final int REQUEST_PERMISSION = 0;

    private static volatile PermissionUtils permissionUtils;

    private PackageManager pkgManager;

    private PermissionUtils(PackageManager manager) {
        this.pkgManager = manager;
    }

    public static PermissionUtils getInstance(PackageManager manager) {

        if (permissionUtils == null) {

            synchronized (PermissionUtils.class) {

                permissionUtils = new PermissionUtils(manager);

            }
        }

        return permissionUtils;
    }

    /**
     * 读写权限和设备信息
     *
     * @param activity
     */
    public void sdcardPermission(Activity activity) {

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, activity.getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, activity.getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PERMISSION);
        }
    }


}
