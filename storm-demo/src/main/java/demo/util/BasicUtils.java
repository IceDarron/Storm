package demo.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.shade.org.eclipse.jetty.util.ajax.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class BasicUtils {

	// json域数据说明
	private static final String EMPTY_VALUE = "";

	private static final String NULL_VALUE = "null";

	/**
	 * <p>
	 * Discription:[判定字符串是否为空——null、""、"null"均为空]
	 * </p>
	 * 
	 * @param strJsonData
	 * @return
	 */
	public static boolean isNULL(String strJsonData) {
		return strJsonData == null || EMPTY_VALUE.equals(strJsonData) || NULL_VALUE.equalsIgnoreCase(strJsonData);
	}

	/**
	 * <p>
	 * Discription:[判定字符串是否为空——null、""、"null"均为空]
	 * </p>
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNULL(Double d) {
		return d == null || EMPTY_VALUE.equals(d) || NULL_VALUE.equals(d);
	}

	/***
	 * 取传入字符串的后nums位，如果不到nums位，前面加相应位数的0补齐
	 * 
	 * @param strParam
	 * @param nums
	 * @return
	 */
	public static String getRightSubString(String strParam, int nums) {
		String strResult = "";
		int i = strParam.length();
		if (i > nums) {
			strResult = strParam.substring(i - nums, i);
		} else if (i < nums) {
			for (int k = 0; k < nums - i; k++) {
				strResult = "0" + strParam;
				strParam = strResult;
			}
		} else {
			strResult = strParam;
		}
		return strResult;
	}

	/***
	 * 取传入字符串的前nums位，如果不到nums位，后面加相应位数的0补齐
	 * 
	 * @param strParam
	 * @param nums
	 * @return
	 */
	public static String getLeftSubString(String strParam, int nums) {
		String strResult = "";
		int i = strParam.length();
		if (i > nums) {
			strResult = strParam.substring(0, nums);
		} else if (i < nums) {
			for (int k = 0; k < nums - i; k++) {
				strResult = strParam + "0";
				strParam = strResult;
			}
		} else {
			strResult = strParam;
		}
		return strResult;
	}

	/**
	 * redis中型如{222222222=02, 111111111=01, 333333333=01}的string转Map
	 * 
	 * @param strValue
	 * @return
	 */
	public static Map<String, String> transStringToMap(String strValue) {
		Map<String, String> map = new HashMap<String, String>();
		strValue = strValue.replace("{", "").replace("}", "").trim();
		String[] arrys = strValue.split(",");
		for (String str : arrys) {
			map.put(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1));
		}
		return map;
	}

	/**
	 * 根据发送时段确定短发发送REQ_TIME 策略为：1、如果当前小时数处在发送时段中即取当前小时数；2.如果当前小时数小于发送时段上限，即取上限
	 * 大于即取下限
	 * 
	 * @param sendTs
	 * @return
	 */
	public static String getSendRequireTime(String sendTs) {
		String req_time = DateUtils.currentTime(DateUtils.DATE_YYYY_MM_DD_HH);
		if (!isNULL(sendTs)) {
			try {
				int startHour = 0;
				int endHour = 0;
				int currentHour = Integer.parseInt(req_time);
				startHour = Integer.parseInt(sendTs.substring(0, sendTs.indexOf("-")));
				endHour = Integer.parseInt(sendTs.substring(sendTs.indexOf("-") + 1));
				if (startHour < endHour) {
					// 当前小时处在发送时段以内
					if (currentHour > startHour && currentHour < endHour) {
						return req_time;
					} else if (startHour == endHour) {
						return req_time;
					} else if (currentHour < startHour)// 小于上限即取上限时段
					{
						return String.valueOf(startHour);
					} else if (currentHour > endHour)// 大于下限即取下限时段
					{
						return String.valueOf(endHour);
					} else {
						return req_time;
					}
				} else if (startHour > endHour) {
					// 当前小时处在发送时段以内
					if (currentHour > endHour && currentHour < startHour) {
						return String.valueOf(startHour);
					} else if (currentHour > startHour || currentHour < endHour)// 小于上限即取上限时段
					{
						return req_time;
					} else {
						return req_time;
					}
				} else {
					return req_time;
				}
			} catch (Exception e) {
				return req_time;
			}
		}
		return req_time;
	}

	/**
	 * 获取java序列
	 * 
	 * @return
	 */
	public static String getJavaSeq() {
		Date dt = new Date();
		long timestap = dt.getTime();
		String seq = getRightSubString(String.valueOf(timestap), 7);
		return seq;
	}

	public static void main(String[] args) {
		System.out.println(getJavaSeq());
	}

	// public static String getJavaSeq()
	// {
	// String strKey = "";
	// List<Long> listKey = new ArrayList<Long>();
	// long nextSeq = 0;
	// String currentSeq = "";
	// synchronized (read_lock)
	// {
	// if(listKey.isEmpty())
	// {
	// listKey.clear();
	// PathInit.initSysPath();
	// try
	// {
	// currentSeq = CacheXfkUtils.getValue(Const.CacheConst.JAVA_SEQUENCE+"test",
	// Const.CacheConst.JAVA_SEQ_CONS);
	// }
	// catch(Exception e)
	// {
	// currentSeq = "";
	// }
	// if(isNULL(currentSeq))
	// {
	// currentSeq = "1000000";
	// CacheXfkUtils.cache(Const.CacheConst.JAVA_SEQUENCE,
	// Const.CacheConst.JAVA_SEQ_CONS, "1000000");
	// }
	// else
	// {
	// if(Long.valueOf(currentSeq)>=9999999)
	// {
	// currentSeq = "1000000";
	// CacheXfkUtils.cache(Const.CacheConst.JAVA_SEQUENCE,
	// Const.CacheConst.JAVA_SEQ_CONS, "1000000");
	// }
	// }
	// for(int k=0; k<CacheSize; k++)
	// {
	// listKey.add(k,Long.valueOf(currentSeq)+k);
	// }
	// nextSeq = Long.valueOf(currentSeq)+CacheSize;
	// CacheXfkUtils.cache(Const.CacheConst.JAVA_SEQUENCE,
	// Const.CacheConst.JAVA_SEQ_CONS, String.valueOf(nextSeq));
	// }
	// strKey = listKey.get(0).toString();
	// listKey.remove(0);
	// }
	// return strKey;
	// }
	//
	/**
	 * 翻转字符串
	 * 
	 */
	public static String strReverse(String str) {
		StringBuffer sb = new StringBuffer(str);
		return sb.reverse().toString();
	}

	/**
	 * 获取json中key的value
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getJsonValue(JSONObject json, String key) {
		String value = "";
		try {
			value = json.getString(key);
			if (isNULL(value)) {
				return "";
			} else {
				return value;
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取json中key的value，在没有key或者空值的情况下赋予默认值
	 * 
	 * @param json
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getJsonValue(JSONObject json, String key, String defaultValue) {
		String value = "";
		try {
			value = json.getString(key);
			if (isNULL(value)) {
				return defaultValue;
			} else {
				return value;
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 将传入字符串转成org.json.JSONObject返回，如果传入字符非法，则返回new org.json.JSONObject()
	 * 
	 * @param strJson
	 * @return
	 */
	public static JSONObject StringToJson(String strJson) {
		try {
			JSONObject json = JSONObject.parseObject(strJson);
			return json;
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	public static Long getLong(Properties properties, String str, Long long1) {
		if (BasicUtils.isNULL(properties.getProperty(str))) {
			Long long2 = Long.parseLong(properties.getProperty(str));
			long1 = long2;
		}
		return long1;
	}

	public static int getInt(Properties properties, String str, int i) {
		if (!BasicUtils.isNULL(properties.getProperty(str))) {
			int i2 = Integer.parseInt(properties.getProperty(str));
			i = i2;
		}
		return i;
	}

	public static boolean getBoolean(Properties properties, String str, Boolean bool) {
		if (!BasicUtils.isNULL(properties.getProperty(str))) {
			bool = properties.getProperty("testOnBorrow") == "false" ? false : true;
		}
		return bool;
	}

	public static String arrayToString(Object[] array) {
		if ((null == array) || (array.length <= 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object s : array) {
			if (null == s)
				continue;
			sb.append(s.toString()).append(",");
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String listToString(List<?> list) {
		if ((null == list) || (list.size() <= 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Iterator<?> localIterator = list.iterator(); localIterator.hasNext();) {
			Object s = localIterator.next();
			if (null != s) {
				sb.append(s.toString()).append(",");
			}
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 如果目标值为空则返回默认值
	 * 
	 * @param target
	 *            目标值
	 * @param defaultValue
	 *            默认是
	 * 
	 * @return 标准结果
	 */
	public static String isEmptyAndDefaultValue(String target, String defaultValue) {
		if (BasicUtils.isNULL(target)) {
			target = defaultValue;
		}
		return target;
	}
}
