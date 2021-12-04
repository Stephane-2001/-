package com.leeyen.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.leeyen.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/***
 * @author leeyen
 */
public class CrowdUtil {

    public static ResultEntity<String> sendShortMessage(
            String host,

            String path,

            String method,

            String phoneNum,

            String appcode,

            String template_id
    ) {

        Map<String, String> headers = new HashMap<String, String>();

        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        // 封装其他参数
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        // 生成验证码
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        // 转换为string
        String code = builder.toString();

        // 生成的code
        bodys.put("content", "code:" + code);

        // 传入的电话
        bodys.put("phone_number", phoneNum);

        // 使用的模板代码
        bodys.put("template_id", template_id);


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));

            StatusLine statusLine = response.getStatusLine();


            //400发送失败   401未授权或授权失败    403调用频率超出限额     404请求路径出错    500服务器内部错误  512短信通道暂不可用
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 400) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_DETECTION_FAILED);
            }else if (statusCode == 403){
                return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_RATE_LIMIT_EXCEEDED);
            }else if (statusCode == 500){
                return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_INTERNAL_ERROR);
            }else {
                return ResultEntity.successWithData(code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    /**
     * 判断请求类型
     * @param request
     * @return true=json请求;false=普通页面请求
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (header != null && header.equals("XMLHttpRequest"));
    }

    /***
     *对明文字符串进行md5加密
     * @param source 传入的明文字符串
     * @return 加密的结果
     */
    public static String md5(String source){
        // 1.判断rsource是否有效
        if (source == null || source.length() == 0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALID);
        }
        // 2.获取一个MessageDigest对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 3.获取铭文字符串对应的字节数组
            byte[] input = source.getBytes();
            // 4.执行加密
            byte[] output = messageDigest.digest(input);
            // 5.创建bigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum,output);
            // 6.按照16进制转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
