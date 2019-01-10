package com.sinotech.table;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2019/1/10  14:35.
 *
 * @desc
 */
public class ReportKeyNameUtils {
    public static String getKeyName(String key){
        switch (key){
            case "AmountBzf":
                return "保值费";
            case "TotalCod":
                return "总代收";
            case "AmountFreight":
                return "运费";
            case "AmountOts3":
                return "外传费";
            case "AmountShf":
                return "送货费";
            case "AmountYingShou":
                return "应收款";
            case "BillDeptName":
                return "当前部门";
            case "OrderCount":
                return "运单数量";
            case "TotalAmountFreight":
                return "总运费";
            default:
                return key;
        }
    }
}
