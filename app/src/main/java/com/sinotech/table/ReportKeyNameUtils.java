package com.sinotech.table;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2019/1/10  14:35.
 *
 * @desc
 */
public class ReportKeyNameUtils {
    public static String getKeyNameTest(String key){
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
    public static String getKeyName(String key){
        switch (key){
            case "orderNo":
                return "运单号码";
            case "prepayAmount":
                return "提付应收合计";
            case "arriveTime":
                return "到货时间";
            case "orderDate":
                return "开票日期";
            case "discStockDays":
                return "滞留天数";
            case "billDeptName":
                return "开票部门";
            case "currentDeptName":
                return "当前部门";
            case "discDeptName":
                return "到达部门";
            case "destDeptName":
                return "转货地";
            case "shipper":
                return "发货人";
            case "shipperMobile":
                return "发货手机";
            case "shipperAddr":
                return "发货地址";
            case "consignee":
                return "收货人";
            case "consigneeMobile":
                return "收货手机";
            case "consigneeAddr":
                return "收货地址";
            case "orderRefNo":
                return "关联单号";
            case "itemDesc":
                return "货物名称";
            case "orderCount":
                return "运单数量";
            case "itemQty":
                return "件数";
            case "itemKgs":
                return "重量";
            case "itemCbm":
                return "体积";
            case "itemName":
                return "货号";
            case "contractNo":
                return "会员编号";
            case "amountCod":
                return "代收款";
            case "amountCodFreight":
                return "代收运费";
            case "amountTf":
                return "提付运费";
            case "amountXf":
                return "现付运费";
            case "amountXfyj":
                return "现付月结运费";
            case "amountTfyj":
                return "提付月结运费";
            case "amountHdf":
                return "回单运费";
            case "amountKf":
                return "扣付运费";
            case "amountFreight":
                return "运费";
            case "amountFreightPtValue":
                return "运费结算方式";
            case "amountBzf":
                return "保值费";
            case "amountBzfTf":
                return "提付保值费";
            case "amountBzfXf":
                return "现付保值费";
            case "amountBzfXfyj":
                return "月结保值费";
            case "amountBzfHdf":
                return "回单付保值费";
            case "amountDff":
                return "垫付费";
            case "amountDffXf":
                return "现返垫付费";
            case "amountDffQf":
                return "欠返垫付费";
            case "amountJhf":
                return "接货费";
            case "amountJhfPt":
                return "接货费结算方式";
            case "amountJhfTf":
                return "提付接货费";
            case "amountJhfXf":
                return "现付接货费";
            case "amountJhfXfyj":
                return "月结接货费";
            case "amountJhfHdf":
                return "回单付接货费";
            case "amountShf":
                return "送货费";
            case "amountShfPt":
                return "送货费结算方式";
            case "amountShfTf":
                return "提付送货费";
            case "amountShfXf":
                return "现付送货费";
            case "amountShfXfyj":
                return "月结送货费";
            case "amountShfXfYj":
                return "月结送货费";
            case "amountShfHdf":
                return "回单付送货费";
            case "amountTransfer":
                return "中转费";
            case "amountTransferPt":
                return "中转费结算方式";
            case "amountTransferTf":
                return "提付中转费";
            case "amountTransferXf":
                return "现付中转费";
            case "amountTransferXfyj":
                return "月结中转费";
            case "amountTransferXfYj":
                return "月结中转费";
            case "amountTransferHdf":
                return "回单付中转费";
            case "amountYj":
                return "佣金";
            case "amountYjPt":
                return "佣金结算方式";
            case "amountYjXf":
                return "现返佣金";
            case "amountYjQf":
                return "欠返佣金";
            case "amountOts2":
                return "保费";
            case "amountOts3":
                return "短途费";
            case "totalAmountTf":
                return "提付合计";
            case "totalAmountXf":
                return "现付合计";
            case "totalAmountXfyj":
                return "现付月结合计";
            case "totalAmountTfyj":
                return "提付月结";
            case "totalAmountHdf":
                return "回单合计";
            case "totalAmountKf":
                return "扣付合计";
            case "totalAmount":
                return "费用合计";
            case "totalRev":
                return "总值";
            case "amountOts1":
                return "工本费";
            case "forTransferValue":
                return "是否转货";
            case "forDelivery":
                return "是否送货";
            case "forHd":
                return "是否带回单";
            case "orderSales":
                return "业务员";
            case "orderInsUser":
                return "录单员";
            case "customerRemark":
                return "客户备注";
            case "paidDeptName":
                return "网点名称";
            case "datea":
                return "回款日期";
            case "dayIncome":
                return "当日收入";
            case "dayOut":
                return "当日支出";
            case "dayReturn":
                return "当日应回";
            case "cashInCashCount":
                return "现付收入";
            case "collectAndPayCount":
                return "提付收入";
            case "incomeFromCollection":
                return "代收款收入";
            case "freightCollect":
                return "代收运费收入";
            case "advancePaymentIncome":
                return "垫付费收入";
            case "monthlyIncome":
                return "月结收入";
            case "raiseMonthlyIncome":
                return "提付月结收入";
            case "returnPayment":
                return "回单付收入";
            case "dailyExpenditure":
                return "日常支出";
            case "commissionExpenditure":
                return "佣金支出";
            case "transferFee":
                return "中转费支出";


            case "deptName":
                return "到达部门";
            case "total":
                return "总票数";
            case "totalQty":
                return "总件数";
            case "billRetentionAmount":
                return "总运费";
            case "discTotal":
                return "到货票数";
            case "discTotalQty":
                return "到货件数";
            case "discTotalAmount":
                return "到货运费";
            case "billRetention":
                return "开票网店滞留票数";
            case "billRetentionQty":
                return "开票网店滞留件数";
            case "areaRetention":
                return "库区滞留票数";
            case "areaRetentionQty":
                return "库区滞留件数";
            case "areaRetentionAmount":
                return "库区滞留运费";
            case "onwayRetention":
                return "分拨在途票数";
            case "onwayRetentionQty":
                return "分拨在途件数";
            case "onwayRetentionAmount":
                return "分拨在途运费";










            default:
                return key;
        }
    }
}
