package com.leeyen.crowd.constant;

public class CrowdConstant {

    public static final String ATTR_NAME_EXCEPTION = "exception";

    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入";
    public static final String MESSAGE_ACCESS_FORBIDEN = "请登录后再访问";
    public static final String MESSAGE_STRING_INVALID = "字符串不合法，请不要传入空字符串";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误，登陆账号不唯一";
    public static final String MESSAGE_ACCESS_DENIED = "没有访问权限";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请检查手机号是否正确或重新发送！";
    public static final String MESSAGE_CODE_INVALID = "验证码不正确！";
    public static final String MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE = "抱歉！这个账号已经被使用";
    public static final String MESSAGE_CODE_DETECTION_FAILED = "发送失败";
    public static final String MESSAGE_CODE_RATE_LIMIT_EXCEEDED = "调用频率超出限额";
    public static final String MESSAGE_CODE_INTERNAL_ERROR = "服务器内部错误";

    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";

    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";

    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";

}
