package com.sinotech.table;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sinotech.view.form.core.SmartTable;
import com.sinotech.view.form.core.TableConfig;
import com.sinotech.view.form.data.CellInfo;
import com.sinotech.view.form.data.format.bg.BaseCellBackgroundFormat;
import com.sinotech.view.form.data.format.sequence.BaseSequenceFormat;
import com.sinotech.view.form.data.json.IJsonFormat;
import com.sinotech.view.form.data.json.JsonHelper;
import com.sinotech.view.form.data.style.FontStyle;
import com.sinotech.view.form.data.table.MapTableData;
import com.sinotech.view.form.utils.DensityUtils;
import com.sinotech.view.form.utils.LetterUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {
    private SmartTable table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable) findViewById(R.id.table);
        table.getConfig()
                .setShowTableTitle(true)
                .setFixedYSequence(true)
                .setShowXSequence(false)
                .setYSequenceFormat(new BaseSequenceFormat() {
                    @Override
                    public String format(Integer position) {
                        return position == 1 ? "" : String.valueOf(position - 1);
                    }
                });
        getData(false);

    }

    public void getData(final boolean isFoot){
        String url = "http://61.175.198.122:29997/HNQZ/MobileTMS/Implement/ReportService.svc/GetReportReceive";
        String postJson = "{\"parameter\":{\"BillDeptName\":\"\",\"BillDeptType\":\"所有\",\"CurrentBrandId\":\"1\"," +
                "\"CurrentDeptName\":\"河南旗帜物流\",\"DiscDeptName\":\"\",\"DiscDeptType\":\"所有\"," +
                "\"EndDate\":\"2018-11-07 \",\"SalesType\":0,\"StartDate\":\"2018-11-07 \",\"UserCode\":\"SUPERMAN\"}}";
        OkHttpUtils
                .postString()
                .url(url)
                .tag(this)
                .content(postJson)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MapTableData tableData = getReportDate();
                        MapTableData totalDate = getTotalDate();
                        table.setTableData(tableData,totalDate.getT().get(0));
//                        table.setTableData(tableData);
                    }
                });
    }
    private MapTableData getTotalDate(){
        return MapTableData.create("开票信息统计",
                JsonHelper.setJsonFormat(new IJsonFormat() {
                    @Override
                    public String getKeyName(String key) {
                        return ReportKeyNameUtils.getKeyName(key);
                    }

                    @Override
                    public boolean isShow(String key) {
                        return LetterUtils.isChinese(key);
                    }

                    @Override
                    public String getKeyValue(String key, Object value) {
                        return (String) value;
                    }

                    @Override
                    public List<String> compare(List<String> keys) {
                        return keys;
                    }

                    @Override
                    public int compare(String key, String key1) {
                        return LetterUtils.getStringMax(key,key1);
                    }

                    @Override
                    public boolean countInBottom() {
                        return false;
                    }
                })
                        .jsonToMapList("[" +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"合计\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":888," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":888888," +
                                "            \"TotalAmountTf\":2404," +
                                "            \"TotalAmountXf\":618," +
                                "            \"TotalBzf\":4," +
                                "            \"TotalCod\":68320," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":471," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }]"));
    }
    private MapTableData getReportDate(){
        return MapTableData.create("开票信息统计",
                JsonHelper.setJsonFormat(new IJsonFormat() {
                    @Override
                    public String getKeyName(String key) {
                        return ReportKeyNameUtils.getKeyName(key);
                    }

                    @Override
                    public boolean isShow(String key) {
                        return LetterUtils.isChinese(key);
                    }

                    @Override
                    public String getKeyValue(String key, Object value) {
                        return (String) value;
                    }

                    @Override
                    public List<String> compare(List<String> keys) {
                        return keys;
                    }

                    @Override
                    public int compare(String key, String key1) {
                        return LetterUtils.getStringMax(key,key1);
                    }

                    @Override
                    public boolean countInBottom() {
                        return false;
                    }
                })
                        .jsonToMapList("[" +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"郑邦营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":53," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":3018," +
                                "            \"TotalAmountTf\":2404," +
                                "            \"TotalAmountXf\":618," +
                                "            \"TotalBzf\":4," +
                                "            \"TotalCod\":68320," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":471," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"东建材营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":249," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2972," +
                                "            \"TotalAmountTf\":2957," +
                                "            \"TotalAmountXf\":15," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":12045," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":330," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"六盛二部营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":80," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2856," +
                                "            \"TotalAmountTf\":2741," +
                                "            \"TotalAmountXf\":115," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":67725," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":565," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"六盛营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":63," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2754," +
                                "            \"TotalAmountTf\":2706," +
                                "            \"TotalAmountXf\":48," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":61730," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":532," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"华商汇营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":50," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2620," +
                                "            \"TotalAmountTf\":2505," +
                                "            \"TotalAmountXf\":115," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":70559," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":472," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"总部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":41," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2604," +
                                "            \"TotalAmountTf\":2469," +
                                "            \"TotalAmountXf\":135," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":19917," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":369," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"郭店营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":44," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2495," +
                                "            \"TotalAmountTf\":2502," +
                                "            \"TotalAmountXf\":950," +
                                "            \"TotalBzf\":2," +
                                "            \"TotalCod\":15659," +
                                "            \"TotalOts3\":1045," +
                                "            \"TotalQty\":224," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":50" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"孟庄营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":40," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2325," +
                                "            \"TotalAmountTf\":2325," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":65858," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":301," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"华南城营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":83," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2149," +
                                "            \"TotalAmountTf\":2106," +
                                "            \"TotalAmountXf\":43," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":51697," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":278," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"石家庄\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":7," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":2024," +
                                "            \"TotalAmountTf\":1464," +
                                "            \"TotalAmountXf\":950," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":25," +
                                "            \"TotalOts3\":390," +
                                "            \"TotalQty\":62," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"文兴路营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":46," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1543," +
                                "            \"TotalAmountTf\":1268," +
                                "            \"TotalAmountXf\":275," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":37865," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":175," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"灯饰营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":45," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1426," +
                                "            \"TotalAmountTf\":1426," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":14759," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":242," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"创业园营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":58," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1415," +
                                "            \"TotalAmountTf\":1279," +
                                "            \"TotalAmountXf\":138," +
                                "            \"TotalBzf\":2," +
                                "            \"TotalCod\":47076," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":218," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"白沙营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":28," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1152," +
                                "            \"TotalAmountTf\":962," +
                                "            \"TotalAmountXf\":190," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":10733," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":168," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"香江营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":23," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1098," +
                                "            \"TotalAmountTf\":755," +
                                "            \"TotalAmountXf\":343," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":20274," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":347," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"中部营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":33," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1041," +
                                "            \"TotalAmountTf\":1041," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":5406," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":141," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"新乡营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":7," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":237," +
                                "            \"TotalAmountTf\":237," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":35," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"南阳营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":3," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":183," +
                                "            \"TotalAmountTf\":33," +
                                "            \"TotalAmountXf\":150," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":12," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"濮阳营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":10," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":147," +
                                "            \"TotalAmountTf\":119," +
                                "            \"TotalAmountXf\":28," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":408," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":14," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"关林福拉多营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":3," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":120," +
                                "            \"TotalAmountTf\":8," +
                                "            \"TotalAmountXf\":112," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":30," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"安阳营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":5," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":73," +
                                "            \"TotalAmountTf\":73," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":7," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"商丘营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":6," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":65," +
                                "            \"TotalAmountTf\":65," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":10," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"平顶山营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":3," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":50," +
                                "            \"TotalAmountTf\":50," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":6," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"周口北营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":3," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":38," +
                                "            \"TotalAmountTf\":38," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":5," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"焦作营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":2," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":16," +
                                "            \"TotalAmountTf\":16," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":2," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"项城营业部\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":1," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":8," +
                                "            \"TotalAmountTf\":8," +
                                "            \"TotalAmountXf\":0," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":1," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }," +
                                "        {" +
                                "            \"AmountBzf\":0," +
                                "            \"AmountCod\":0," +
                                "            \"AmountFregiht\":0," +
                                "            \"AmountOts3\":0," +
                                "            \"AmountShf\":0," +
                                "            \"AmountYingShou\":0," +
                                "            \"BalanceDate\":null," +
                                "            \"BarOrderNo\":null," +
                                "            \"BillDeptName\":\"测试分理处\"," +
                                "            \"CurrentDeptName\":null," +
                                "            \"DeptName\":null," +
                                "            \"DiscDeptName\":null," +
                                "            \"ItemDesc\":null," +
                                "            \"LastAmount\":0," +
                                "            \"OrderCount\":1," +
                                "            \"OrderDate\":null," +
                                "            \"OrderNo\":null," +
                                "            \"PaidAmount\":0," +
                                "            \"RemainAmount\":0," +
                                "            \"RetentionQty\":0," +
                                "            \"TotalAmountFreight\":1," +
                                "            \"TotalAmountTf\":0," +
                                "            \"TotalAmountXf\":1," +
                                "            \"TotalBzf\":0," +
                                "            \"TotalCod\":0," +
                                "            \"TotalOts3\":0," +
                                "            \"TotalQty\":1," +
                                "            \"TotalSdk\":0," +
                                "            \"TotalShf\":0" +
                                "        }" +
                                "    ]"));
    }
}
