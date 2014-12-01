package com.girlassistant.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * 字符串处理工具类
 * @author gaoj
 *
 */
public class StringUtils {

	/**
	 * 判断邮箱
	 */
	public static boolean isEmailRight(String email) {
		// 邮箱不能为空，要是一个合法邮箱
		if (email == null || email.trim().equals("")) {
			return false;
		} else {
			// aa@sina.com aa@sina.com.cn
			// \\w+@\\w+(\\.\\w+)+
			// ^([a-z0-9][a-z0-9_\\-\\.\\+]*)@([a-z0-9][a-z0-9\\.\\-]{0,63}\\.(com|org|net|biz|info|name|net|pro|aero|coop|museum|[a-z]{2,4}))$
			return (email.matches("^([a-z0-9][a-z0-9_\\-\\.\\+]*)@([a-z0-9][a-z0-9\\.\\-]{0,63}\\.(com|org|net|biz|info|name|net|pro|aero|coop|museum|[a-z]{2,4}))$"));
		}
	}

	/**
	 * 判断手机号
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean isPhoneNumRight(String phoneNum) {
		String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
		String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
		String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
		/**
		 * flag = 1 YD 2 LT 3 DX
		 */
		// 判断手机号码是否是11位
		if (phoneNum.length() == 11) {
			// 判断手机号码是否符合中国移动的号码规则
			if (phoneNum.matches(YD)) {
				return true;
			}
			// 判断手机号码是否符合中国联通的号码规则
			else if (phoneNum.matches(LT)) {
				return true;
			}
			// 判断手机号码是否符合中国电信的号码规则
			else if (phoneNum.matches(DX)) {
				return true;
			}
			// 都不合适 未知
			else {
				return false;
			}
		}
		// 不是11位
		else {
			return false;
		}
	}

	/**
	 * 判断密码
	 */
	public static boolean isPhoneRight(String phone) {
		if (CheckUtils.isEmptyStr(phone)) {
			return false;
		} else {
			Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
			Matcher m = p.matcher(phone);
			return m.matches();
		}
	}

	/**
	 * 判断密码
	 */
	public static boolean isPasswordRight(String password) {
		if (password == null || password.trim().equals("")) {
			return false;
		} else {
			if (password.length() <= 5 || password.length() > 20) {
				return false;
			}
			return true;
		}
	}

	/**判断两次密码是否匹配
	 * @param password
	 * @param rePassword
	 * @return
	 */
	public static boolean isRePasswordRight(String password, String rePassword) {
		if (rePassword.trim().length() > 0 && password.trim().length() > 0) {
			return password.equals(rePassword) ? true : false;
		}
		return false;
	}

	/**
	 * 判断用户名
	 */
	public static boolean isNameRight(String name) {
		int num = 0;
		for (int i = 0; i < name.length(); i++) {
			String ss = String.valueOf(name.charAt(i));
			if (ss.matches("[^\\x00-\\xff]")) {
				num = num + 2;
			} else {
				num++;
			}
		}
		if (num >= 4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * URL编码
	 * 
	 * @param url
	 */
	public static String encodeURL(String url) {
		if (null == url) {
			return "";
		}
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return url;
	}

	/**
	 * 读取JSON String类型值 如果某属性为null，JSONObject会得到"null"字符串，此方法统一处理这类问题。
	 * 
	 * @param jsonObj
	 * @param key
	 */
	public static String getJsonString(JSONObject jsonObj, String key) {
		if (jsonObj.isNull(key)) {
			return null;
		}
		String value = jsonObj.optString(key, null);
		if (value == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(value);
		return sb.toString();
	}

	public static String getTextFromHtml(JSONObject jsonObj, String key) {
		String s = getJsonString(jsonObj, key);
		if (s != null) {
			s = Html.fromHtml(s).toString();
		}
		return s;
	}

	/**
	 * 读取assets目录下的文本文件
	 * 
	 * @return String fileName 文件内容
	 * @author ZhengTao Date:2011-02-17
	 */

	public static String readFromAssets(Context context, String fileName) {
		InputStream is;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = context.getAssets().open(fileName);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * @param context
	 *            上下文
	 * @param resId
	 *            字符串资源id
	 * @param foregroundColorSpan
	 *            要设置的字体前景色
	 * @return 设置后的字体颜色
	 */
	public static SpannableString setPreferenceFontColor(Context context, int resId, ForegroundColorSpan foregroundColorSpan) {
		SpannableString spannableString = new SpannableString(context.getResources().getString(resId));
		spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), 0);
		return spannableString;
	}

	public static SpannableString setPreferenceFontColor(String str, ForegroundColorSpan foregroundColorSpan) {
		SpannableString spannableString = new SpannableString(str);
		spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), 0);
		return spannableString;
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符 ...
	 * 
	 * 
	 * */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");

		}
		return dest;
	}

	public static String toGBK(String str) {

		if (CheckUtils.isNoEmptyStr(str)) {
			try {
				byte[] bytes = str.getBytes();
				str = new String(bytes, "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return str;
	}

	/**
	 * 白问正文处理
	 * 
	 * */
	public static String replaceAskContent(String str) {

		if (CheckUtils.isNoEmptyStr(str)) {
			str = str.replaceAll("\r", "\\\\r");
			str = str.replaceAll("\n", "\\\\n");
		}
		return str;
	}

}
