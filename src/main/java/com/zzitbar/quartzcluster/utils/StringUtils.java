package com.zzitbar.quartzcluster.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }

    /**
	 * 转换为Boolean类型
	 * 'true', 'on', 'y', 't', 'yes' or '1' (case insensitive) will return true. Otherwise, false is returned.
	 */
	public static Boolean toBoolean(final Object val){
		if (val == null){
			return false;
		}
		return BooleanUtils.toBoolean(val.toString()) || "1".equals(val.toString());
	}

    /**
     * 转换为字节数组
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

    /**
	 * 如果对象为空，则使用defaultVal值
	 * 	see: ObjectUtils.toString(obj, defaultVal)
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
    public static String toString(final Object obj, final String defaultVal) {
    	 return obj == null ? defaultVal : obj.toString();
    }

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
		return (remoteAddr != null ? remoteAddr : request.getRemoteAddr()).split(",")[0];// 有时会出现以，分割的多个IP
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld"
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }

	/**
	 * 生成随机数
	 * 
	 * @param len 长度
	 */
	public static String random(int len) {
		// 创建一个随机数生成器类
		SecureRandom random = new SecureRandom();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String strRand = String.valueOf(random.nextInt(10));
			// 将产生的随机数组合在一起。
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	/**
	 * 判断是否是app请求
	 * 
	 */
	public static boolean isAppRequest(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		// TODO
		return false;
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}
}