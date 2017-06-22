package com.seven.library.config;


import com.seven.library.application.LibApplication;

import java.io.File;

/**
 * 基础配置
 *
 * @author seven
 */
public class RunTimeConfig {

    /**
     * 默认路径配置
     */
    public static class PathConfig {

        /**
         * App目录
         */
        public static final String APP_PATH = File.separator + "sdcard" + File.separator + "seven" + File.separator;

        /**
         * 应用的data目录[/data/data/[package]/files]，此目录下的文件只有本应用可访问，用于存放一些重要的配置文件
         */
        public static final String DATA_PATH_INNER = LibApplication.getInstance().getFilesDir().getAbsolutePath() + File.separator;

        /**
         * 文件下载的存放路径
         */
        public static final String DOWN_LOAD_PATH = APP_PATH + "download" + File.separator;

        /**
         * LOG信息保存路径
         */
        public static final String LOG_SAVE_PATH = APP_PATH + "log" + File.separator;

        /**
         * 视频存放的路径
         */
        public static final String VIDEO_PATH = APP_PATH + "video" + File.separator;

        /**
         * 图片存放的路径
         */
        public static final String PICTURE_PATH = APP_PATH + "picture" + File.separator;

        /**
         * 压缩图片临时保存路径
         */
        public static final String IMG_UPLOAD_TEMP_PATH = APP_PATH
                + "Image" + File.separator + "temp" + File.separator;

        /**
         * 缓存存放的路径
         */
        public static final String CACHE_PATH = APP_PATH + "cache" + File.separator;

        /**
         * web view cache
         */
        public static final String CACHE_WEB = CACHE_PATH + "webCache";

        /**
         * 临时文件存放的路径
         */
        public static final String TEMP_PATH = APP_PATH + "temp" + File.separator;

        /**
         * 插件下载的的本地保存路径
         */
        public static final String PLUG_IN_DOWNLOAD_PATH = APP_PATH + "plug_in" + File.separator;

        /**
         * 推荐应用下载的的本地保存路径
         */
        public static final String RECOMMEND_APP_DOWNLOAD_PATH = APP_PATH + "recommend_app" + File.separator;

        /**
         * 升级apk下载保存路径
         */
        public static final String APK_DOWNLOAD_PATH = APP_PATH + "apk" + File.separator;
    }

    /**
     * 默认数据库配置
     */
    public static class DbConfig {

        /**
         * 数据库名称
         */
        public static final String DB_NAME = "seven.db";

        //        public static final String DB_PATH=PathConfig.DATA_PATH_INNER;
        public static final String DB_PATH = PathConfig.APP_PATH;

        /**
         * 数据库版本
         */
        public static final int DB_VERSION = 1;

    }

    /**
     * SharedPreferences 字段
     */
    public static class SharedConfig {

        /**
         * shared 文件名称
         */
        public static final String SHARED_NAME = "seven";

        /**
         * 第一次登录
         */
        public static final String FIRST = "first";


        /**
         * 用户码
         */
        public static final String USER_CODE = "userCode";

        /**
         * 认证提示时间
         */
        public static final String ATTESTATION_TIME = "attestationTime";

    }


    /**
     * intent 参数
     */
    public static class IntentCodeConfig {

        /**
         * 是否结束启动activity
         */
        public static final String FINISH = "isFinish";

        /**
         * 项目id
         */
        public static final String PROJECTID = "projectId";

        /**
         * 数据模型
         */
        public static final String SERIALIZABLE = "Serializable";

        /**
         * 数据个性项模型
         */
        public static final String TERM = "term";

        /**
         * 奖励金模型
         */
        public static final String REWARD = "reward";

        /**
         * 空间信息
         */
        public static final String SPACE_LIST = "spaceList";


        /**
         * 空间模型
         */
        public static final String HOUSE_MODEL = "houseModel";

        /**
         * 注册、忘记密码流程 @link FlowConfig
         */
        public static final String FLOW = "flow";

        /**
         * 城市
         */
        public static final String CITY = "city";

        /**
         * 手机号码
         */
        public static final String MOBILE = "mobile";

        /**
         * 图片选择模式 @link
         */
        public static final String PICTURE_MODEL = "psModel";

        /**
         * 所有动作
         */
        public static final String ACTION_WHAT = "action";

        /**
         * 路径
         */
        public static final String PATH = "path";

        /**
         * 用户认证状态
         */
        public static final String USER_STATUS = "userStatus";

        /**
         * 机构关联状态
         */
        public static final String COMPANY_STATUS = "companyStatus";

        /**
         * 报价是否可编辑
         */
        public static final String OFFER_IS_COMPILE = "isCompile";

    }

    //---------------------------------------------------- library

    /**
     * 通用配置
     */
    public static class CommonConfig {

        /**
         * 相机请求码
         */
        public static final int CAMERA_CODE = 100;

        /**
         * 男
         */
        public static final int SEX_MAN = 1;

        /**
         * 女
         */
        public static final int SEX_WOMAN = 2;

        /**
         * 年
         */
        public static final int DATE_Y = 1;

        /**
         * 年、月、日
         */
        public static final int DATE_YMD = 2;

        /**
         * 年、月、日、时、分
         */
        public static final int DATE_YMDHS = 3;

    }

    /**
     * 请求id配置
     */
    public static class RequestConfig {

        /**
         * 短信验证码
         */
        public static final int SMS = 1001;

        /**
         * 检验验证码
         */
        public static final int SMS_CHECK = 1002;

        /**
         * 注册
         */
        public static final int REGISTER = 1003;

        /**
         * 登录
         */
        public static final int LOGIN = 1004;

        /**
         * 免登录
         */
        public static final int LOGIN_AVOID = 1005;

        /**
         * 忘记密码
         */
        public static final int PASSWORD_FORGET = 1006;

        /**
         * 用户信息
         */
        public static final int USER_INFO = 1007;

        /**
         * 实名认证
         */
        public static final int ID_AUDIT = 1008;

        /**
         * 保存资料
         */
        public static final int DATA_SAVE = 1009;

        /**
         * 获取资料
         */
        public static final int USER_DATA = 1010;

        /**
         * 校验密码
         */
        public static final int PASSWORD_CHECK = 1011;

        /**
         * 重置手机号码
         */
        public static final int RESET_MOBILE = 1012;

        /**
         * 关联机构
         */
        public static final int COMPANY = 1013;

        //===================================================工程

        /**
         * 预约订单
         */
        public static final int ORDER_LIST = 1101;

        /**
         * 拒绝
         */
        public static final int ORDER_REFUSE = 1102;

        /**
         * 接受
         */
        public static final int ORDER_ACCEPT = 1103;

        //=================================================== 报价

        /**
         * 报价-套餐房间
         */
        public static final int OFFER_HOUSE = 1201;

        /**
         * 报价-套餐
         */
        public static final int OFFER_PACKAGE = 1202;

        /**
         * 报价单
         */
        public static final int OFFER_QUOTATION = 1203;

        /**
         * 奖励金信息
         */
        public static final int OFFER_REWARD = 1204;

        /**
         * 保存报价
         */
        public static final int OFFER_SAVE = 1205;

        //=================================================== 资源包

        /**
         * 服务城市
         */
        public static final int RES_CITY = 1501;

        /**
         * 个性项
         */
        public static final int RES_PERSONALITY = 1502;

        //==================================================== 图片


        /**
         * 身份证正面
         */
        public static final int IMG_ID_FRONT = 2001;

        /**
         * 身份证反面
         */
        public static final int IMG_ID_REVERSE = 2002;

    }

    /**
     * 所有动作配置
     */
    public static class ActionConfig {

        /**
         * 选择图片
         */
        public static final String PICTURE_SELECT = "pictureSelect";

        /**
         * 刷新
         */
        public static final String REFRESH = "refresh";

        /**
         * 报价数据、操作动作传递消息
         */
        public static final String OFFER = "offer";

        /**
         * 个性项方量
         */
        public static final String PERSONALITY = "personality";


    }

    /**
     * 单一动作对应的活动
     */
    public static class ActionWhatConfig {

        //-----PICTURE_SELECT
        /**
         * 头像
         */
        public static final int HEADER = 0;

        /**
         * 身份证正面
         */
        public static final int ID_CARD_FRONT = 1;

        /**
         * 身份证反面
         */
        public static final int ID_CARD_REVERSE = 2;

        //--------REFRESH
        public static final int USER_STATUS = 100;

        /**
         * 刷新订单
         */
        public static final int QUOTATION_ORDER = 101;

        /**
         * 刷新订单
         */
        public static final int OFFER_ORDER = 102;

        //--------OFFER

        /**
         * 是否显示添加按钮
         */
        public static final int OFFER_ADD = 200;

        /**
         * 个性项方量
         */
        public static final int PERSONNALITY = 250;
    }

    /**
     * 图片配置
     */
    public static class PictureConfig {

        /**
         * 单张
         */
        public static final int SINGLE = 0;

        /**
         * 多张
         */
        public static final int MULTI = 1;

        /**
         * 相机
         */
        public static final int CAMERA = 0;

        /**
         * 图片
         */
        public static final int PICTURE = 1;

        /**
         * 图片未选中
         */
        public static final int OUT = 0;

        /**
         * 图片选中
         */
        public static final int IN = 1;

        /**
         * 身份证正面
         */
        public static final int ID_FRONT = 302;

        /**
         * 身份证反面
         */
        public static final int ID_REVERSE = 303;

    }

    //---------------------------------------------------- user library

    /**
     * 功能流程配置
     */
    public static class FlowConfig {

        /**
         * 注册流程
         */
        public static final int REGISTER = 1;

        /**
         * 忘记密码流程
         */
        public static final int FORGET_PASSWORD = 2;

        /**
         * 重置手机号码
         */
        public static final int RESET_MOBILE = 3;

        /**
         * 报价是否可编辑 是
         */
        public static final int OFFER_IS_COMPILE = 0;

        /**
         * 报价是否可编辑 否
         */
        public static final int OFFER_IS_COMPILE_NOT = 1;

    }

    /**
     * 状态配置
     */
    public static class StatusConfig {

        //---------------------- user data status
        /**
         * 未认证
         */
        public static final int UNAUTHERIZED = 1;

        /**
         * 认证未通过
         */
        public static final int AUTHERIZED_NOPASS = 2;

        /**
         * 已认证
         */
        public static final int AUTHERIZED_PASS = 3;

        /**
         * 已注销
         */
        public static final int CANCELED = 4;

        /**
         * 认证中
         */
        public static final int AUTHERIZED = 5;

        //----------------------- company status

        /**
         * 未认证
         */
        public static final int NOT_RELEVANCE = 0;

        /**
         * 已认证
         */
        public static final int RELEVANCE = 1;

        //----------------------- order status

        /**
         * 1新预约、2已取消、3待报价、4已报价、4待开工、5已失效
         */
        public static final int ORDER_STATUS_NEW = 1;
        public static final int ORDER_STATUS_CANCEL = 2;
        public static final int ORDER_STATUS_OFFER = 3;
        public static final int ORDER_STATUS_OFFER_OVER = 4;
        public static final int ORDER_STATUS_START = 100;
        public static final int ORDER_STATUS_INVALID = 101;

        /**
         * 操作状态  -1拒绝  1接受
         */
        public static final int ORDER_REFUSE = -1;
        public static final int ORDER_ACCEPT = 1;

    }

    /**
     * 弹框内容配置
     */
    public static class DialogTagConfig {

        /**
         * 拒绝信息
         */
        public static final int TAG_REFUSE = 1;

        /**
         * 接受信息
         */
        public static final int TAG_ACCEPT = 2;

        /**
         * 是否保存报价
         */
        public static final int TAG_QUOTATION = 3;

    }

    /**
     * 地图配置
     */
    public static class MapConfig {

        public static final int TYPE_NORMAL = 0;
        public static final int TYPE_MOON = 1;
        public static final int TYPE_TRAFFIC = 2;
        public static final int TYPE_HEATING = 3;

    }

    /**
     * 模型配置
     */
    public static class ModelConfig {

        //------------------报价-套餐
        public static final int OFFER_PACKAGE_ITEM = 0;
        public static final int OFFER_PACKAGE_TOTAL = 1;

        //------------------报价-个性项
        public static final int OFFER_PERSONALITY_ITEM = 0;
        public static final int OFFER_PERSONALITY_TOTAL = 1;

        //------------------报价单
        public static final int QUOTATION_TITLE = 0;
        public static final int QUOTATION_PACKAGE = 1;
        public static final int QUOTATION_PERSONALITY = 2;
        public static final int QUOTATION_DATE = 3;

        //------------------新报价 空间信息
        public static final int SPACE_TITLE = 0;
        public static final int SPACE_ITEM = 1;

        //------------------新报价 基础项
        public static final int BASE_TITLE = 0;
        public static final int BASE_CONTROL = 1;
        public static final int BASE_ITEM = 2;

        //-------------------
        public static final int TERM_VOLUME_TITLE = 0;
        public static final int TERM_VOLUME_ITEM = 1;
        public static final int TERM_VOLUME_TOTAL = 2;
    }

    /**
     * 房间配置
     */
    public static class HouseConfig {

        //1室 2厅 3厨  4卫  5阳台 6其他
        public static final int ROOM = 1;
        public static final int HALL = 2;
        public static final int KITCHEN = 3;
        public static final int TOILET = 4;
        public static final int BALCONY = 5;
        public static final int OTHER = 6;

    }

    /**
     * 选中状态配置
     */
    public static class SelectConfig {

        public static final int SELECT_NOT = 0;
        public static final int SELECTED = 1;

    }

    /**
     * 报价fragment标记
     */
    public static class OfferFGTagConfig {
        public static final int TAG_PACKAGE = 0;
        public static final int TAG_PERSONALITY = 1;
        public static final int TAG_DATE = 2;
    }

}
