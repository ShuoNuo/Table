package com.sinotech.view.form.data.parse;

import java.util.List;
import java.util.Map;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2020/9/10  10:33.
 *
 * @desc 数据解析类
 */
public interface  IJsonParseFormat {

    /**
     * 用于接卸替换 表头
     * @param key
     * @return
     */
    String getKeyName(String key);

    /**
     * 用于过滤数据  是否显示该列
     * @param key
     * @return
     */
    boolean isShow(String key);

    /**
     * 可根据 key value值 去格式化值
     * @param key
     * @param value
     * @return
     */
    String getKeyValue(String key,Object value);

    /**
     * 列排序
     * @param keys
     * @return
     */
    List<String> compare(List<String> keys);

    /**
     * 列排序
     * @param key
     * @param key1
     * @return
     */
    int compare(String key, String key1);

    /**
     * 是否系统自动排列
     * @return
     */
    boolean isSystemCompare();

    /**
     * 或取系统排列的 map  由报表中类统一维护
     * @return
     */
    Map<Integer,String> getSystemCompareMap();

    /**
     * 获取需添加参数数据
     * @return
     */
    List<String> getParamKeyList();
}
