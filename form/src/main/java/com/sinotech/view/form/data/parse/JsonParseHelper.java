package com.sinotech.view.form.data.parse;

import android.text.TextUtils;

import com.sinotech.view.form.exception.TableException;
import com.sinotech.view.form.utils.LetterUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2020/9/10  10:27.
 *
 * @desc 数据解析类
 */
public class JsonParseHelper {
    /**
     * JSONObject
     */
    public static final int JSON_TYPE_OBJECT = 1;
    /**
     * JSONArray
     */
    public static final int JSON_TYPE_ARRAY = 2;
    /**
     * 不是JSON格式的字符串
     */
    public static final int JSON_TYPE_ERROR = 3;

    private static IJsonParseFormat mJsonParseFormat;

    private static JsonParseHelper mInstance;

    private static boolean haveParaKeyList = false;

    private JsonParseHelper() {

    }

    public static JsonParseHelper setJsonParseFormat(IJsonParseFormat jsonParseFormat) {
        if (mInstance == null) {
            mInstance = new JsonParseHelper();
        }
        mJsonParseFormat = jsonParseFormat;
        haveParaKeyList = (mJsonParseFormat.getParamKeyList() != null && mJsonParseFormat.getParamKeyList().size() > 0);
        return mInstance;
    }

    /**
     * 解析报表数据
     *
     * @param json json
     * @return Map-List集合
     */
    public static List<Object> jsonReportDateToMapList(String json) {
        if (mJsonParseFormat == null) {
            throw new TableException("数据解析类 is null");
        }
        List<Object> mapList = null;
        if (getJSONType(json) == JSON_TYPE_ARRAY) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                mapList = reflectList(reflect(jsonArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            throw new TableException("报表数据必须为 jsonArray");
        }
        return mapList;
    }

    /**
     * 解析报表合计数据
     * json 转换成Map-List集合
     *
     * @param json json
     * @return Map-List集合
     */
    public static List<Object> jsonReportTotalToMapList(String json) {
        if (mJsonParseFormat == null) {
            throw new TableException("数据解析类 is null");
        }
        List<Object> mapList = null;
        if (getJSONType(json) == JSON_TYPE_OBJECT) {
            try {
                json = "[" + json + "]";
                JSONArray jsonArray = new JSONArray(json);
                mapList = reflectList(reflect(jsonArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            throw new TableException("报表合计必须为 jsonObject");
        }
        return mapList;
    }

    /**
     * @param json
     * @return [{{运单号,123456},{billDeptId,123456},{discDeptId,123456}},
     * {{重量,0.1},{billDeptId,123456},{discDeptId,123456}}]
     */
    public static List<Object> reflect(JSONArray json) {
        List<Object> list = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                Object o = json.get(i);
                if (o instanceof JSONArray)
                    list.add(reflect((JSONArray) o));
                else if (o instanceof JSONObject)
                    list.add(reflect((JSONObject) o));
                else
                    list.add(LetterUtils.isNumber(o) ? String.valueOf(o.toString()) : o.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return {{运单号,123456},{billDeptId,123456},{discDeptId,123456}}
     * || {{重量,0.1},{billDeptId,123456},{discDeptId,123456}}
     */
    public static Map<String, Object> reflect(JSONObject json) {
        HashMap<String, Object> map = new LinkedHashMap<>();
        Iterator<String> keys = json.keys();
        try {
            for (; keys.hasNext(); ) {
                String key = keys.next();
                Object o = json.get(key);
                if (o instanceof JSONArray)
                    map.put(key, reflect((JSONArray) o));
                else if (o instanceof JSONObject) {
                    map.put(key, reflect((JSONObject) o));
                } else
                    map.put(getKeyName(key), LetterUtils.isNumber(o) ? String.valueOf(o.toString()) : o.toString());
                    if (haveParaKeyList){
                        if (!getKeyName(key).equals(key)&& mJsonParseFormat.getParamKeyList().contains(key)){
                            map.put(key, LetterUtils.isNumber(o) ? String.valueOf(o.toString()) : o.toString());
                        }
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 对表头进行数据格式化处理
     */
    private static String getKeyName(String key) {
        return mJsonParseFormat.getKeyName(key);
    }

    /**
     * 数据解析及排序处理
     *
     * @param objectListMap {{运单号,123456},{billDeptId,123456},{discDeptId,123456}},
     *                      {{重量,0.1},{billDeptId,123456},{discDeptId,123456}}]
     * @return
     */
    private static List<Object> reflectList(List<Object> objectListMap) {
        final List<Object> tempObjectListMap = new ArrayList<>();
        List<String> keys = getAllKeys(objectListMap);  //[运单号,billDeptId,discDeptId,重量]

        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());//[运单号,billDeptId,discDeptId] ||[billDeptId,discDeptId,重量]
            List<String> keyList = new ArrayList<>();
            if (i == 0) { //i = 0 表头  [运单号,billDeptId,discDeptId,重量]
                keyList = keys;
            } else { // 表体数据  [运单号,billDeptId,discDeptId] ||[billDeptId,discDeptId,重量]
                for (int j = 0; j < list.size(); j++) {
                    Map.Entry<String, Object> o1 = list.get(j);
                    keyList.add(o1.getKey());
                }
            }
            if (mJsonParseFormat.isSystemCompare()) {//首先判断是否系统自动排序
                if (mJsonParseFormat.getSystemCompareMap() != null && mJsonParseFormat.getSystemCompareMap().size() > 0) {//判断是否有用户自定义 Map 顺序；
                    keyList = getMapKeyList(mJsonParseFormat.getSystemCompareMap(), keyList);
                } else {// 无自定义顺序   根据汉字的顺序来排列
                    Collections.sort(keyList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return LetterUtils.getStringMax(o1, o2);
                        }
                    });
                }
            } else {//非系统自定义排序
                if (mJsonParseFormat.compare(keyList) != null && mJsonParseFormat.compare(keyList).size() > 0) {
                    keyList = mJsonParseFormat.compare(keyList);//用户自定义List列顺序
                } else {
                    Collections.sort(keyList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return mJsonParseFormat.compare(o1, o2);//用户自定义比较规则
                        }
                    });
                }
            }
            LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
            for (int j = 0; j < keyList.size(); j++) {
                String key = keyList.get(j);
                Object object = getKeyValue(key, list);
                if (mJsonParseFormat.isShow(key)) {//根据 key 判断是否显示该列
                    map2.put(key, object);
                } else if (haveParaKeyList) {
                    if (mJsonParseFormat.getParamKeyList().contains(key)){
                        map2.put(key, object);
                    }
                }

            }
            tempObjectListMap.add(map2);
        }
        return tempObjectListMap;
    }

    /**
     * 对数据值 进行格式化处理
     * @return
     */
    private static Object getKeyValue(String key, ArrayList<Map.Entry<String, Object>> list) {
        Object obj = null;
        for (Map.Entry<String, Object> entry : list) {
            String entryKey = entry.getKey();
            if (key.equals(entryKey)) {
                obj = mJsonParseFormat.getKeyValue(key, entry.getValue());
                break;
            }
        }
        return obj;
    }

    /**
     * 根据表头解析map 和  遍历出来的 keys 对比排序
     * map[{1.运单号}，{2.重量}]   keys[运单号,billDeptId,discDeptId,重量]
     *
     * @return MapKeyList  [运单号,重量,billDeptId,discDeptId]
     */
    private static List<String> getMapKeyList(Map<Integer, String> map, List<String> keys) {
        List<String> tempKeys = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            String key = map.get(i);
            if (keys.size() > 0) {
                for (int j = 0; j < keys.size(); j++) {
                    if (key.equals(keys.get(j))) {
                        tempKeys.add(key);
                        keys.remove(j);
                        break;
                    }
                }
            }
        }
        if (keys.size() > 0) {
            tempKeys.addAll(keys);
        }
        return tempKeys;
    }

    /**
     * 遍历循环获取所有的  key
     *
     * @param objectListMap{{运单号,123456},{billDeptId,123456},{discDeptId,123456}}, {{重量,0.1},{billDeptId,123456},{discDeptId,123456}}]
     * @return [运单号, billDeptId, discDeptId, 重量]
     */
    private static List<String> getAllKeys(List<Object> objectListMap) {
        List<String> tempAllKeys = new ArrayList<>();
        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
            for (int j = 0; j < list.size(); j++) {
                Map.Entry<String, Object> o1 = list.get(j);
                String key = o1.getKey();
                boolean isExitKey = false;
                for (int k = 0; k < tempAllKeys.size(); k++) {
                    if (key.equals(tempAllKeys.get(k))) {
                        isExitKey = true;
                        break;
                    }
                }
                if (!isExitKey) {
                    tempAllKeys.add(key);
                }
            }
        }
        return tempAllKeys;
    }


    /***
     * 获取JSON类型
     * 判断规则
     */
    private static int getJSONType(String str) {
        if (TextUtils.isEmpty(str)) {
            return JSON_TYPE_ERROR;
        }
        char[] strChar = str.substring(0, 1).toCharArray();
        char firstChar = strChar[0];

        if (firstChar == '{') {
            return JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE_ERROR;
        }
    }
}
