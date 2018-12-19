package com.sinotech.view.form.data.json;

import android.text.TextUtils;
import android.util.Log;

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
/*
    @IntDef({JSON_TYPE_OBJECT,JSON_TYPE_ARRAY,JSON_TYPE_ERROR})
    public @interface JSON_TYPE {
    }
*/
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
                    map.put(getKeyName(key), o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myReflect(map);
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
                    list.add(o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    private static Map<String, Object> myReflect(Map<String, Object> objectMap){
        ArrayList<Map.Entry<String, Object>> list =  new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());
        //从小到大排序（从大到小将o1与o2交换即可）
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {

            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                if (mJsonFormat != null){
                    return mJsonFormat.compare(o1.getKey(),o2.getKey());
                }else {
                    return LetterUtils.getStringMax(o1.getKey(),o2.getKey());
                }
            }

        });
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : list) {
            String key = entry.getKey();
            Object object = entry.getValue();
            if (mJsonFormat != null){
                if (mJsonFormat.isShow(key)){
                    map2.put(key, mJsonFormat.getKeyValue(key,object));
                }
            }else {
                map2.put(key, entry.getValue());
            }
        }
        return map2;
    }

    private static List<Object> myReflectList(List<Object> objectListMap){
        final List<Object> tempObjectListMap = new ArrayList<>();
        for (int i = 0; i < objectListMap.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) objectListMap.get(i);
            final ArrayList<Map.Entry<String, Object>> list =
                    new ArrayList<Map.Entry<String, Object>>(objectMap.entrySet());

            LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
            for (int j = 0; j < list.size(); j++) {
                Map.Entry<String, Object> o1 = list.get(j);
                if ("到达部门".equals(o1.getKey())){
                    map2.put(o1.getKey(), o1.getValue());
                    break;
                }

            }
            for (int j = 0; j < list.size(); j++) {
                Map.Entry<String, Object> o1 = list.get(j);
                if ("运费".equals(o1.getKey())){
                    map2.put(o1.getKey(), o1.getValue());
                    break;
                }

            }
            for (int j = 0; j < list.size(); j++) {
                Map.Entry<String, Object> o1 = list.get(j);
                if ("件数".equals(o1.getKey())){
                    map2.put(o1.getKey(), o1.getValue());
                    break;
                }
            }

            for (Map.Entry<String, Object> entry : list) {
                if (!"运达部门ID".equals(entry.getKey()) && !"件数".equals(entry.getKey()) &&!"运费".equals(entry.getKey())&&!"到达部门".equals(entry.getKey())){
                    map2.put(entry.getKey(), entry.getValue());
                }
            }
            tempObjectListMap.add(map2);
        }
        return tempObjectListMap;
    }
}
