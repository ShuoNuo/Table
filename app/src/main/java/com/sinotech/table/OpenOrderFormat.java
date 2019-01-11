package com.sinotech.table;

import com.sinotech.view.form.data.json.IJsonFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2019/1/7  10:47.
 *
 * @desc 开票信息格式化
 */
public class OpenOrderFormat implements IJsonFormat {
    @Override
    public String getKeyName(String key) {
        switch (key){
            case "discDeptName":
                return "到达部门";
            case "totalAmount":
                return "运费";
            case "itemQty":
                return "件数";
            case "disc_dept_id":
                return "运达部门ID";
            default:
                return key;
        }
    }

    @Override
    public boolean isShow(String key) {
        return !"运达部门ID".equals(key) ;
    }

    @Override
    public String getKeyValue(String key, Object value) {
        return value.toString();
    }

    @Override
    public List<String> compare(List<String> keys) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("到达部门".equals(key) && isShow(key)){
                list.add(key);
                break;
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("运费".equals(key) && isShow(key)){
                list.add(key);
                break;
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("运费".equals(key) && isShow(key)){
                list.add(key);
                break;
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("件数".equals(key) && isShow(key)){
                list.add(key);
                break;
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (isShow(key)&& !"件数".equals(key) &&!"运费".equals(key)&&!"到达部门".equals(key)){
                list.add(key);
            }
        }
        return list;
    }

    @Override
    public int compare(String key, String key1) {
        int value = 0;

        switch (key){
            case "到达部门":
                value = -1;
            case "运费":
                switch (key1){
                    case "到达部门":
                    case "运费":
                        value = -1;
                    default:
                        value = 1;
                }
            case "件数":
                switch (key1){
                    case "到达部门":
                    case "运费":
                        value = -1;
                    default:
                        value = 1;
                }
            default:
                switch (key1){
                    case "到达部门":
                    case "运费":
                    case "件数":
                        value = -1;
                    default:
                        value = 1;
                }
        }
        if (key.equals(key1)){
            value = 0;
        }
        return value;
    }

    @Override
    public boolean isSystemCompare() {
        return false;
    }

    @Override
    public Map<Integer, String> getSystemCompareMap() {
        return null;
    }

}
