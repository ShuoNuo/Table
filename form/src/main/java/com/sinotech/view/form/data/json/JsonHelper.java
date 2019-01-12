package com.sinotech.view.form.data.json;

import android.text.TextUtils;
import android.util.Log;

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
 * Json转换Map-List集合辅助类
 * jsonArray 转换成List
 * JsonObject 转换成Map
 * Created by huangYanbin on 2018/2/8.
 */

public class JsonHelper {
    /**JSONObject*/
    public static final int JSON_TYPE_OBJECT = 1;
    /**JSONArray*/
    public static final int JSON_TYPE_ARRAY = 2;
    /**不是JSON格式的字符串*/
    public static final int JSON_TYPE_ERROR = 3;

    private static IJsonFormat mJsonFormat;

    private static JsonHelper mInstance;

    public static boolean isFixCount = false;

    private JsonHelper(){

    }
    public static JsonHelper setJsonFormat (IJsonFormat jsonFormat){
        if (mInstance == null){
            mInstance = new JsonHelper();
        }
        mJsonFormat = jsonFormat;
        return mInstance;
    }
    /**
     * json 转换成Map-List集合
     * @param json json
     * @return Map-List集合
     */
    public  static List<Object> jsonToMapList(String json){
        List<Object> mapList = null;
        try {
            if(getJSONType(json) == JSON_TYPE_OBJECT){
                JSONObject jsonObject = new JSONObject(json);
                Map<String,Object> objects = JsonHelper.reflect(jsonObject);
                mapList = new ArrayList<>();
                mapList.add(objects);
            }else if(getJSONType(json) == JSON_TYPE_ARRAY){
                JSONArray jsonArray = new JSONArray(json);
                mapList = JsonHelper.reflect(jsonArray);//JsonHelper.myReflectList();
            }else{
                Log.e("smartTable","json异常");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    /**
     * json 转换成Map-List集合
     * @param json json
     * @return Map-List集合
     */
    public  static List<Object> jsonReportDateToMapList(String json) {
        List<Object> mapList = null;
        if(getJSONType(json) == JSON_TYPE_ARRAY){
            try {
                JSONArray jsonArray = new JSONArray(json);
                mapList = myNewReflectList(JsonHelper.reflect(jsonArray));//JsonHelper.myReflectList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            throw new TableException("报表数据必须为 jsonArray");
        }
        return mapList;
    }
    /**
     * json 转换成Map-List集合
     * @param json json
     * @return Map-List集合
     */
    public  static List<Object> jsonReportTotalToMapList(String json) {
        List<Object> mapList = null;
        if(getJSONType(json) == JSON_TYPE_OBJECT){
            try {
                json = "[" + json + "]";
                JSONArray jsonArray = new JSONArray(json);
                mapList = myNewReflectList(JsonHelper.reflect(jsonArray));//JsonHelper.myReflectList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            throw new TableException("报表合计必须为 jsonObject");
        }
        return mapList;
    }

    /***
     *
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
        }
        else if (firstChar == '[') {
            return JSON_TYPE_ARRAY;
        } else {
            return JSON_TYPE_ERROR;
        }
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * @param json
     * @return
     */
    public static Map<String, Object> reflect(JSONObject json){
        HashMap<String, Object> map = new LinkedHashMap<>();
        Iterator<String> keys = json.keys();
        try {
            for ( ;keys.hasNext();) {
                String key =  keys.next();
                Object o = json.get(key);
                if(o instanceof JSONArray)
                    map.put( key, reflect((JSONArray) o));
                else if(o instanceof JSONObject){
                    map.put(key, reflect((JSONObject) o));
                }
                else
                    map.put(getKeyName(key), o.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        myReflect(map);
        return map;
    }
    private static String getKeyName(String key ){
        return mJsonFormat == null ?  key : mJsonFormat.getKeyName(key);
    }

    /**
     * 将JSONArray对象转换成Map-List集合
     * @param json
     * @return
     */
    public static List<Object> reflect(JSONArray json){
        List<Object> list = new ArrayList<>();
        try {
            for(int i = 0;i <json.length();i++){
                Object o = json.get(i);
                if(o instanceof JSONArray)
                    list.add(reflect((JSONArray) o));
                else if(o instanceof JSONObject)
                    list.add(reflect((JSONObject) o));
                else
                    list.add(o.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    private static Map<String, Object> myReflect(Map<String, Object> objectMap){
        ArrayList<Map.Entry<String, Object>> list =  new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
        List<String> keyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            keyList.add(list.get(i).getKey());
        }
        if (mJsonFormat != null){
            keyList = mJsonFormat.compare(keyList);
        }
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            Object object = getKeyValue(key,list);
            if (mJsonFormat != null){
                if (mJsonFormat.isShow(key)){
                    map2.put(key,object);
                }else {
                    map2.put(key,object);
                }
            }
        }

        return map2;
    }
    private static Object getKeyValue(String key ,ArrayList<Map.Entry<String, Object>> list){
        Object obj = null;
        for (Map.Entry<String, Object> entry : list) {
            String entryKey = entry.getKey();
            if (key.equals(entryKey)){
                if (mJsonFormat != null){
                    obj = mJsonFormat.getKeyValue(key,entry.getValue());
                }else {
                    obj = entry.getValue();
                }
                break;
            }
        }
        return obj;
    }

    /**
     * 该方法存在缺陷  废弃
     * @param objectListMap
     * @return
     */
    private static List<Object> myReflectList(List<Object> objectListMap){
        final List<Object> tempObjectListMap = new ArrayList<>();

        int maxKeySizeIndex = 0;
        int mxaKeySize = 0;
        /**
         * 获取 key  最多的 bean
         */
        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            int tempKeySize = objectMap.size();
            maxKeySizeIndex = tempKeySize > mxaKeySize ? i : maxKeySizeIndex;
            mxaKeySize = mxaKeySize > tempKeySize ? mxaKeySize : tempKeySize;
        }
        /**
         * 多循环一次  先解析key 最多的一组数据   并跳过 maxKeySizeIndex
         */
        for (int i = -1; i < objectListMap.size(); i++) {
            if (i != maxKeySizeIndex){
                Map<String, Object> objectMap;
                ArrayList<Map.Entry<String, Object>> list;
                if (i == -1){
                    objectMap = (Map<String, Object>) objectListMap.get(maxKeySizeIndex);
                    list = new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
                }else {
                    objectMap = (Map<String, Object>) objectListMap.get(i);
                    list = new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
                }


                List<String> keyList = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    Map.Entry<String, Object> o1 = list.get(j);
                    keyList.add(o1.getKey());
                }

                if (mJsonFormat != null){
                    if (mJsonFormat.isSystemCompare()){//首先判断是否系统自动排序
                        if (mJsonFormat.getSystemCompareMap()!= null && mJsonFormat.getSystemCompareMap().size() > 0){//判断是否有用户自定义 Map 顺序；
                            keyList = getMapKeyList(mJsonFormat.getSystemCompareMap(),keyList);
                        }else {// 无自定义顺序   根据汉字的顺序来排列
                            Collections.sort(keyList, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return LetterUtils.getStringMax(o1,o2);
                                }
                            });
                        }
                    }else {//非系统自定义排序
                        if ( mJsonFormat.compare(keyList) != null && mJsonFormat.compare(keyList).size()> 0){
                            keyList = mJsonFormat.compare(keyList);//用户自定义List列顺序
                        }else {
                            Collections.sort(keyList, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return mJsonFormat.compare(o1,o2);//用户自定义比较规则
                                }
                            });
                        }
                    }
                }
                LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
                for (int j = 0; j < keyList.size(); j++) {
                    String key = keyList.get(j);
                    Object object = getKeyValue(key,list);
                    if (mJsonFormat != null){
                        if (mJsonFormat.isShow(key)){//根据 key 判断是否显示该列
                            map2.put(key,object);
                        }
                    }else {
                        map2.put(key,object);
                    }
                }
                tempObjectListMap.add(map2);
            }
        }
        return tempObjectListMap;
    }

    private static List<Object> myNewReflectList(List<Object> objectListMap){
        final List<Object> tempObjectListMap = new ArrayList<>();
        List<String> keys = getAllKeys(objectListMap);
        /**
         * 多循环一次  先解析key 最多的一组数据   并跳过 maxKeySizeIndex
         */
        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
            List<String> keyList = new ArrayList<>();
            if (i == 0){
                keyList = keys;
            }else {
                for (int j = 0; j < list.size(); j++) {
                    Map.Entry<String, Object> o1 = list.get(j);
                    keyList.add(o1.getKey());
                }
            }
            if (mJsonFormat != null){
                if (mJsonFormat.isSystemCompare()){//首先判断是否系统自动排序
                    if (mJsonFormat.getSystemCompareMap()!= null && mJsonFormat.getSystemCompareMap().size() > 0){//判断是否有用户自定义 Map 顺序；
                        keyList = getMapKeyList(mJsonFormat.getSystemCompareMap(),keyList);
                    }else {// 无自定义顺序   根据汉字的顺序来排列
                        Collections.sort(keyList, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return LetterUtils.getStringMax(o1,o2);
                            }
                        });
                    }
                }else {//非系统自定义排序
                    if ( mJsonFormat.compare(keyList) != null && mJsonFormat.compare(keyList).size()> 0){
                        keyList = mJsonFormat.compare(keyList);//用户自定义List列顺序
                    }else {
                        Collections.sort(keyList, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return mJsonFormat.compare(o1,o2);//用户自定义比较规则
                            }
                        });
                    }
                }
            }
            LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
            for (int j = 0; j < keyList.size(); j++) {
                String key = keyList.get(j);
                Object object = getKeyValue(key,list);
                if (mJsonFormat != null){
                    if (mJsonFormat.isShow(key)){//根据 key 判断是否显示该列
                        map2.put(key,object);
                    }
                }else {
                    map2.put(key,object);
                }
            }
            tempObjectListMap.add(map2);
        }
        return tempObjectListMap;
    }



    private static List<String> getMapKeyList (Map<Integer,String> map, List<String> keys){
        List<String> tempKeys = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            String key = map.get(i);
            if (keys.size()>0){
                for (int j = 0; j < keys.size(); j++) {
                    if (key.equals(keys.get(j))){
                        tempKeys.add(key);
                        keys.remove(j);
                        break;
                    }
                }
            }
        }
        if (keys.size() > 0){
            tempKeys.addAll(keys);
        }
        return tempKeys;
    }

    /**
     * 遍历循环获取所有的  key
     * @param objectListMap
     * @return
     */
    private static List<String> getAllKeys(List<Object> objectListMap){
        List<String> tempAllKeys = new ArrayList<>();
        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            ArrayList<Map.Entry<String, Object>> list =new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());;
            for (int j = 0; j < list.size(); j++) {
                Map.Entry<String, Object> o1 = list.get(j);
                String key = o1.getKey();
                boolean isExitkey = false;
                for (int k = 0; k < tempAllKeys.size(); k++) {
                    if (key.equals(tempAllKeys.get(k))){
                        isExitkey = true;
                        break;
                    }
                }
                if (!isExitkey){
                    tempAllKeys.add(key);
                }
            }
        }
        return tempAllKeys;
    }
}