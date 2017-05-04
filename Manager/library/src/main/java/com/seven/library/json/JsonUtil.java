package com.seven.library.json;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * json解析工具类
 * @author seven
 *
 */
public final class JsonUtil {

	private static String TAG = "FastJson";

	public static boolean isSuccess(String jsonString) {
		JSONObject json;
		boolean flag = false;
		try {
			json = new JSONObject(jsonString);
			flag = json.getBoolean("result");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static String getArrayString(String jsonString, String key) {
		String value = "";
		JSONObject json;
		try {
			json = new JSONObject(jsonString);
			value = json.getJSONArray(key).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String convertObjectToJson(Object o) {
		SerializerFeature[] features = { SerializerFeature.QuoteFieldNames,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullBooleanAsFalse,
				SerializerFeature.WriteSlashAsSpecial,
				SerializerFeature.BrowserCompatible,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat };

		try {
			Log.d(TAG, JSON.toJSONString(o, features));
			return JSON.toJSONString(o, features);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return "";
		}
	}

	public static <T> T convertJsonToObject(String json, Class<T> clazz) {
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return null;
		}
	}

	public static <T> List<T> convertJsonToList(String json, Class<T> clazz) {
		try {
			List<T> list= JSON.parseArray(json, clazz);
			if (list==null) {
				list=new ArrayList<T>();
			}
			return list;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return new ArrayList<T>();
		}
	}
}