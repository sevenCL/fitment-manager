package com.seven.library.application;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.frankchen.mvc.controller.common.BaseApplication;
import com.frankchen.mvc.model.common.ActionFactory;
import com.seven.library.R;
import com.seven.library.action.OfferAction;
import com.seven.library.action.PersonalityAction;
import com.seven.library.action.PictureSelectAction;
import com.seven.library.action.RefreshAction;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.image.ImageLoadProxy;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * @author seven
 */
public class LibApplication extends BaseApplication {

    public static DbManager.DaoConfig daoConfig;

    public static String token;

    public static long branchId;

    public static int type;

    public static LibApplication getInstance() {
        return (LibApplication) appContextInstance;
    }

    @Override
    public void initOnServiceCore() {

    }

    @Override
    public void initOnAppCore() {

        //注册动作
        ActionFactory.getSingleInstance().registerAction(new PictureSelectAction());
        ActionFactory.getSingleInstance().registerAction(new RefreshAction());
        ActionFactory.getSingleInstance().registerAction(new OfferAction());
        ActionFactory.getSingleInstance().registerAction(new PersonalityAction());

        //初始化图片缓存信息
        ImageLoadProxy.initImageLoader(getInstance(), new File(RunTimeConfig.PathConfig.CACHE_PATH));

        //初始化http db
        x.Ext.init(getInstance());

        daoConfig = new DbManager.DaoConfig()
                // 数据库的名字
                .setDbName(RunTimeConfig.DbConfig.DB_NAME)
                // 保存到指定路径
                .setDbDir(new File(RunTimeConfig.DbConfig.DB_PATH))
                // 数据库的版本号
                .setDbVersion(RunTimeConfig.DbConfig.DB_VERSION)
                // 数据库版本更新监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager arg0, int arg1, int arg2) {
                        LogUtils.println("db update!");
                    }
                });

        //开启日志---打包时关闭
        LogUtils.isDebug = true;

        SDKInitializer.initialize(getInstance());

    }

    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public void onServiceStart(boolean b) {

    }

    //必须绑定服务
    public boolean isBindService() {
        return false;
    }

    /**
     * 隐式跳转到登录界面
     */
    public void login() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(getInstance(), "com.seven.userlibrary.ui.activity.user.LoginActivity");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.getInstance().showToast(ResourceUtils.getInstance().getText(R.string.msg_intent_login));
            LogUtils.println(e + ResourceUtils.getInstance().getText(R.string.msg_intent_login));
        }
    }

}
