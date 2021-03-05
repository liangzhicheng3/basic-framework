package com.liangzhicheng.common.constant;

/**
 * @description 常量统一定义
 * @author liangzhicheng
 * @since 2020-07-28
 */
public abstract class Constants {

    public static final String KEY_SESSION_SYS_USER_INFO = "KEY_SESSION_SYS_USER_INFO";

    public static final String KEY_SESSION_SYS_USER_MENU = "KEY_SESSION_SYS_USER_MENU";

    public static final String KEY_SESSION_SYS_USER_ROLES = "KEY_SESSION_SYS_USER_ROLES";

    public static final String KEY_SESSION_SYS_USER_PERMS = "KEY_SESSION_SYS_USER_PERMS";

    /**
     * 将用户id放入到session中
     */
    public static final String LOGIN_USER_ID = "login_user_id";

    //登录令牌
    public static final String LOGIN_TOKEN_SERVER = "login_token_server";

    //登录有效期
    public static final Long LOGIN_TOKEN_EXPIRE_TIME_SERVER = 1L;

    /**
     * 初始化相关常量
     */
    public static final String INIT_PHONE_SERVICE = "400-0000-0000"; //电话客服
    public static final String INIT_COOPERATION_AISLE = "13856985541"; //合作专属通道

    /**
     * 工具累相关常量
     */
    public static final String CONTENT_FILTER_FILEPATH = "D:\\censorwords.txt"; //本地敏感词文件地址
    //public static final String CONTENT_FILTER_FILEPATH = "/usr/workspace/xxx/content-filter/censorwords.txt"; //线上敏感词文件地址

    /**
     * WeChat小程序获取用户信息相关常量
     */
    public static final String WECHAT_MINI_URL = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String WECHAT_MINI_APP_ID = "wx02bcf8496490a484";
    public static final String WECHAT_MINI_APP_SECRET = "24c6340e778c34249869f21203a88b3c";
    public static final String WECHAT_MINI_GRANT_TYPE = "authorization_code";

    /**
     * APP获取用户信息相关常量
     */
    public static final String WECHAT_APP_URL_USER = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    public static final String WECHAT_APP_APP_ID_USER = "wxf9e92651a1e6fa96";
    public static final String WECHAT_APP_APP_SECRET_USER = "590cb4cc409a0eb870435346ddddb983";
    public static final String WECHAT_APP_GRANT_TYPE_USER = "authorization_code";

    /**
     * WeChat支付相关常量
     * 备注：1.微信的扫码支付和公众号jsapi支付，拥有微信公众号就可以申请
     *       2.微信的APP支付需要拥有微信开放平台才可以申请
     */
    /**
     * WeChat小程序支付
     */
    //微信支付申请成功后在邮件中的APPID：WECHAT_MINI_APP_ID
    public static final String WECHAT_MINI_MCH_ID = ""; //微信支付商户号
    //小程序密钥(在商户平台设置，登录后——账户中心——账户设置——API安全中设置)：WECHAT_MINI_APP_SECRET
    public static final String MINI_NOTIFY_URL = "http://xxx/client/payClientController/weChatMiniPayNotify"; //异步通知支付结果Url
    /**
     * WeChat APP支付
     */
    public static final String WECHAT_APP_APP_ID = ""; //微信支付申请成功后在邮件中的APPID
    public static final String WECHAT_APP_MCH_ID = ""; //微信支付商户号
    public static final String WECHAT_APP_SECRET = ""; //密钥(在商户平台设置，登录后——账户中心——账户设置——API安全中设置)
    public static final String APP_NOTIFY_URL = "http://xxx/client/payClientController/weChatAppPayNotify"; //异步通知支付结果Url
    /**
     * WeChat扫码支付
     */
    public static final String WECHAT_SCAN_APP_ID = ""; //微信支付申请成功后在邮件中的APPID
    public static final String WECHAT_SCAN_MCH_ID = ""; //微信支付商户号
    public static final String WECHAT_SCAN_SECRET = ""; //密钥(在商户平台设置，登录后——账户中心——账户设置——API安全中设置)
    public static final String SCAN_NOTIFY_URL = "http://xxx/client/payClientController/weChatScanPayNotify"; //异步通知支付结果Url
    /**
     * WeChat公众号支付
     */
    public static final String WECHAT_PUB_APP_ID = ""; //微信支付申请成功后在邮件中的APPID
    public static final String WECHAT_PUB_MCH_ID = ""; //微信支付商户号
    public static final String WECHAT_PUB_SECRET = ""; //密钥(在商户平台设置，登录后——账户中心——账户设置——API安全中设置)
    public static final String PUB_NOTIFY_URL = "http://xxx/client/payClientController/weChatPubPayNotify"; //异步通知支付结果Url
    /**
     * WeChat退款
     */
    //退款证书的路径：登录微信商户平台——账户中心——账户设置——API安全——下载证书，java用apiclient_cert.p12这个文件
    public static final String PATH_CERT = "/opt/ideaShow/cert/apiclient_cert.p12"; //linux
    //public static final String PATH_CERT = "D:/apiclient_cert.p12";//window

    /**
     * alipay支付相关常量
     */
    //支付请求Url
    public static final String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
    //开放平台——右上角主账号——密钥管理
    public static final String ALIPAY_APP_APP_ID = "";
    //开放平台——右上角主账号——密钥管理——左上角账户账户管理
    public static final String ALIPAY_APP_PARTNER = "";
    //收款支付宝账号
    public static final String ALIPAY_APP_PAYEE_EMAIL = "";
    //商户的安全校验码（合作伙伴密钥管理）
    public static final String ALIPAY_APP_KEY = "";
    //即时到帐支付成功后的跳转页面
    public static final String RETURN_URL_WEB = "http://www.ttocz.com";
    //即时到帐商品展示页面
    public static final String SHOW_URL_WEB = "http://www.ttocz.com";
    //调试用，创建TXT日志文件夹路径
    public static final String LOG_PATH = "/usr/alipay/";
    //字符格式
    public static final String INPUT_FORMAT = "json";
    //字符编码格式，目前支持GBK或UTF-8
    public static final String INPUT_CHARSET = "UTF-8";
    //签名方式，不需修改
    public static final String SIGN_TYPE_WEB = "MD5";
    public static final String SIGN_TYPE_APP = "RSA2";
    //支付宝的密钥，生成方式文档：https://docs.open.alipay.com/291/105971
    //https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static final String ALIPAY_PRIVATE_KEY = "";
    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static final String ALIPAY_PUBLIC_KEY = "";
    //异步通知支付结果Url
    public static final String ALIPAY_APP_NOTIFY_URL = "http://xxx/client/payClientController/alipayAppNotify";

    /**
     * JSON Web Token相关常量
     */
    public static final String USER_ID_SUFFIX_APP = "-APP";
    public static final String USER_ID_SUFFIX_MINI = "-MINI";
    public static final String TOKEN_KEY_MAP = "token_key_map";
    public static final String JWT_SECRET = "3MZq0BYyGcXYoXjhS4QbAM+2YdlLCwKRr2gvVJOJ+LIANGZHICHENG"; //JWT生成token时加密密文

    /**
     * 云之讯短信相关常量
     */
    //用户的账号唯一标识；位置：开发者控制台——短信——API接口对接
    public static final String SMS_ACCOUNT_SID = "";
    //用户密钥；位置：开发者控制台——短信——API接口对接
    public static final String SMS_AUTH_TOKEN = "";
    //创建应用时系统分配的唯一标示；位置：开发者控制台——短信——API接口对接 或者 获取路径后台→应用管理→点击需要对接应用，查看appId
    public static final String SMS_APP_ID = "";
    /**
     * 尊敬的用户，您本次的验证码为：{1}，如非本人操作请忽略本信息。
     * 位置：后台短信产品——选择接入的应用——短信模板——模板ID，查看该模板ID
     */
    public static final String SMS_TEMPLATE_ID = "";
    //请求地址
    public static final String SMS_URL = "https://open.ucpaas.com/ol/sms/sendsms";

    /**
     * AES加密、解密算法相关常量
     */
    public static final String AES_KEY = "104982ebf97f44b5"; //密钥(需要前端和后端保持一致)
    public static final String AES_ALGORITHMSTR = "AES/ECB/PKCS5Padding"; //算法

    /**
     * 七牛存储相关常量
     */
    public static final String QINIU_APP_KEY = "hJi-pUDYofUcsMDoi3Mp4yOtTGiZax24EEmrzqTY";
    public static final String QINIU_APP_SECRECT = "tIA8k-zn2hyilu4_X3mLDjJcE5VINeKl4gRvLnpc";
    public static final String QINIU_PREFIX_IMAGE = "http://xxx.com/"; //图片访问前缀
    public static final String QINIU_PREFIX_VIDEO = "http://xxx.com/"; //视频访问前缀
    public static final String QINIU_BUCKET_IMAGE = "ideashow-image"; //图片空间名
    public static final String QINIU_BUCKET_VIDEO = "ideashow-video"; //视频空间名

    /**
     * 邮箱验证相关常量
     */
    public static final String EMAIL_HOST_NAME = "smtp.163.com"; //邮箱服务器地址
    public static final String EMAIL_SEND_USER_ADDRESS = "ideashow0769@163.com"; //发送用户
    public static final String EMAIL_SEND_USER_NAME = "靓彩孵化投资"; //发送用户名称
    public static final String EMAIL_AUTH_CODE = "KSLJNROHQPZMIDQF"; //授权码
    public static final String EMAIL_NAME = "邮箱验证码"; //邮件名称

    /**
     * Xinge推送相关常量
     */
    public static final String ACCESS_ID_IOS = "1600014630";
    public static final String SECRET_KEY_IOS = "0602e3678344a2a548cc484ad6c0cc74";
    public static final String ACCESS_ID_ANDROID = "1500014629";
    public static final String SECRET_KEY_ANDROID = "6c5ca0cc14b911d86c0df596079ef961";
    public static final String XINGE_PUSH_TOKEN_KEY_MAP = "xinge_push_token_key_map"; //推送token map
    public static final String XINGE_PUSH_TYPE_KEY_MAP = "xinge_push_type_key_map"; //推送设备 map

}
