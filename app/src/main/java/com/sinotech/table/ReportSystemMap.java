package com.sinotech.table;

import java.util.HashMap;
import java.util.Map;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2019/1/11  11:57.
 *
 * @desc
 */
class ReportSystemCompareMap {
    public static Map<Integer,String> getSystemCompareMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"当前部门");
        map.put(1,"运单数量");
        map.put(2,"总代收");
        map.put(3,"总运费");
        map.put(4,"外传费");
        map.put(5,"运费");
        map.put(6,"送货费");
        map.put(7,"应收款");
        map.put(8,"保值费");
        return map;
    }
}
