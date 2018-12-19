package com.sinotech.view.form.data.json;

public interface IJsonFormat {
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
     * @param key
     * @param key1
     * @return
     */
    int compare(String key, String key1);
}
