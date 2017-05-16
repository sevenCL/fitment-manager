package com.seven.library.http;

/**
 * 请求地址
 *
 * @author seven
 */
public class Urls {

    /**
     * 请求地址
     */
    public static final String SERVER = "http://dev.gateway.51shejihome.com"; //测试服务器

    /**
     * 图片地址
     */
    public static final String IMG_UPLOAD = "http://192.168.60.233:8020/providerFile/designerUpload";

    /**
     * 发送短信验证码
     */
    public static final String SMS = SERVER + "/user/manager/sendCode";

    /**
     * 验证码验证
     */
    public static final String SMS_CHECK = SERVER + "/user/manager/verifyCode";

    /**
     * 注册
     */
    public static final String REGISTER = SERVER + "/user/manager/register";

    /**
     * 登录
     */
    public static final String LOGIN = SERVER + "/user/manager/loginManager";

    /**
     * 免登录
     */
    public static final String LOGIN_AVOID = SERVER + "/user/manager/freeLogin";

    /**
     * 忘记密码
     */
    public static final String PASSWORD_FORGET = SERVER + "/user/manager/resetPassword";

    /**
     * 用户信息
     */
    public static final String USER_INFO = SERVER + "/designer/getInfo";

    /**
     * 实名认证
     */
    public static final String ID_AUDIT = SERVER + "/idCardApi/verifyIdCard";

    /**
     * 保存资料
     */
    public static final String DATA_SAVE = SERVER + "/designer/saveData";

    /**
     * 获取用户资料
     */
    public static final String USER_DATA = SERVER + "/designer/getData";

    /**
     * 校验密码
     */
    public static final String PASSWORD_CHECK = SERVER + "/designer/verifyPassword";

    /**
     * 重置手机号码
     */
    public static final String RESET_MOBILE = SERVER + "/designer/resetMobile";

    /**
     * 关联机构
     */
    public static final String COMPANY = SERVER + "/designer/getRelation";

    /**
     * 预约订单
     */
    public static final String ORDER_LIST = SERVER + "/reservation/reservationOrderAgent/app/getOrderAgent";

    /**
     * 接受、拒绝
     */
    public static final String ORDER_STATUS = SERVER + "/reservation/reservationOrderAgent/app/assignResponse";

    //===================================================================== 报价

    /**
     * 报价-房间
     */
    public static final String OFFER_HOUSE = SERVER + "/quotation/quotationPlanSpace/list";

    /**
     * 套餐信息
     */
    public static final String OFFER_PACKAGE = SERVER + "/quotation/quotationPlan/getInfo";

    /**
     * 发送报价单
     */
    public static final String OFFER_QUOTATION = SERVER + "/quotation/quotationProjectOrder/save";

    //===================================================================== 资源包

    /**
     * 服务城市
     */
    public static final String RES_CITY = SERVER + "/user/region/queryCities";

    /**
     * 个性项
     */
    public static final String RES_PERSONALITY = SERVER + "/quotation/item/getMaterialInfoCategoryItemsByBranchId";

}
