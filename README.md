## 1.用法

使用前，对于Android Studio的用户，可以选择添加:
在项目的build.gradle添加JitPack仓库
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
```java
	implementation 'com.github.ShuoNuo:Table:v1.0'  
```
可以使用  SmartRefreshLayout 配合上拉加载 下拉刷新

```java
	implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.1.0-alpha-1'
```
SmartRefreshLayout 链接地址：[https://github.com/scwang90/SmartRefreshLayout]
## 2.功能和参数含义

### TableConfig 控制图表的样式

|配置参数|参数含义|
|:--:|--|
|setShowTableTitle|是否显示标题 true/false 默认显示当不显示时，合计滑动有bug|
|setTableTitleStyle|设置标题字体样式|
|setFixedYSequence|是否固定表格 Y 列 |
|setFixedXSequence|是否固定表格 X 列 |
|setShowXSequence setShowYSequence|是否显示 X Y 列|
|setYSequenceFormat|格式化 Y 列显示值，可根据实际需要格式化显示值|
|setLeftAndTopBackgroundColor|设置坐上角表格背景颜色|
|setContentCellBackgroundFormat|设则内容区域表格背景颜色|
|setColumnTitleBackground|设置列标题背景颜色|
|注意|无论是标题、列标题、X、Y序列、内容 的背景、字体、是否固定、是否显示、都有具体API可详细阅读类 TableConfig|

## 3.代码参考

更多使用，请参考Sino_Tech_TMS_Main  modulereport源代码

1. 首先初始化表格  并设置表格的样式
```java
mReportTable = findViewById(R.id.report_activity_report_table);

mReportTable.getConfig()
                .setShowTableTitle(true)
                .setTableTitleStyle(mTableTextStyle)
                .setFixedYSequence(true)
                .setShowXSequence(true)
                .setFixedXSequence(true)
                .setXSequenceBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.report_xy_bg)))
                .setYSequenceBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.report_xy_bg)))
                .setLeftAndTopBackgroundColor(ContextCompat.getColor(getContext(), R.color.report_xy_bg))
                .setXSequenceStyle(mTableTextStyle)
                .setYSequenceStyle(mTableTextStyle)
                .setYSequenceCellBgFormat(new BaseCellBackgroundFormat<Integer>() {
                    @Override
                    public int getBackGroundColor(Integer integer) {
                        return ContextCompat.getColor(getContext(), R.color.report_xy_bg);
                    }
                })
                .setXSequenceCellBgFormat(new BaseCellBackgroundFormat<Integer>() {
                    @Override
                    public int getBackGroundColor(Integer integer) {
                        return ContextCompat.getColor(getContext(), R.color.report_xy_bg);
                    }
                })
                .setCountBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.report_xy_bg)))
                .setContentStyle(mTableTextStyle)
                .setColumnTitleBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.color_navigation)))
                .setColumnTitleStyle(new FontStyle(DensityUtils.sp2px(getContext(), 16), ContextCompat.getColor(getContext(), R.color.white)))
		.setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
		    @Override
		    public int getBackGroundColor(CellInfo cellInfo) {
			if (cellInfo.row % 2 == 1) {
			    return ContextCompat.getColor(getContext(), R.color.report_content_bg);
			}
			return TableConfig.INVALID_COLOR;
		    }

		})
                .setCountStyle(mTableTextStyle)
                .setYSequenceFormat(new BaseSequenceFormat() {
                    @Override
                    public String format(Integer position) {
                        return position == 1 ? "" : String.valueOf(position - 1);
                    }
                });
```

2. 数据处理，可通过注解加类解析后直接生成表格数据
###第一类数据
@SmartTable(name="提货统计",count = true)
name  为表名  count  是否开启自动统计   该统计为界面显示数据的合，需要自定义合计后面讲解
@SmartColumn(id =1,name = "到达部门",fixed = true,autoBottomName = "合计")
id 显示的列顺序 name 列标题 fixed  是否固定 autoBottomName 是否自定义合计名称 用于显示固定的内容
### 第一类数据 实体 bean
```java
@SmartTable(name="提货统计",count = true)
public class DelivReportBean {

    @SmartColumn(id =1,name = "到达部门",fixed = true,autoBottomName = "合计")
    private String discDeptName;
    @SmartColumn(id =2,name = "运单件数",autoCount = true)
    private int orderCount;    
}
List<DelivReportBean> list = getDate();
mReportTable.setData(list);
```
### 第二类数据 Json 字符串  通过 JsonHelper.jsonToMapList 来上生成数据
#### 1.  继承 IJsonFormat  对数据进行自定义处理。
```java
/**
 * 生成类继承 IJsonFormat  对数据进行自定义处理。
 */
 public class NormalReportFormat implements IJsonFormat {
    private String reportType;

    public NormalReportFormat(String reportType) {
        this.reportType = reportType;
    }

    @Override // 格式化列名  类似  amountFreight -->  运费
    public String getKeyName(String key) {
        if ("滞留票据".equals(reportType)){
            String keyName =  ReportKeyNameUtils.getKeyName(key);
            return keyName.equals("总运费") ? "开票部门滞留运费": keyName;
        }else {
            return ReportKeyNameUtils.getKeyName(key);
        }
    }

    @Override  // 是否显示该列数据  key  为格式化后的值  --> 运费
    public boolean isShow(String key) {
        return LetterUtils.isChinese(key);
    }

    @Override  //用于格式化特殊字段的值  例如时间转换  数据字典转换   key  为格式化后的值  --> 运费
    public String getKeyValue(String key, Object value) {
        return (String) value;
    }
    
    @Override //是否系统自动排列所有列的顺序 第一优先级  key  为格式化后的值  --> 运费
    public boolean isSystemCompare() {
        return true;
    }

    @Override //由于报表的特殊性  该自动排列的 Map 由用户定义  如果返回null将使用第二优先级排列  
    				Map<Integer, String> key  为格式化后的值  --> 运费
    public Map<Integer, String> getSystemCompareMap() {
        return ReportSystemCompareMap.getSystemCompareMap();
    }
    
    @Override  //排列所有列的顺序 第二优先级  该 List 由用户自己排列  key  为格式化后的值  --> 运费
    public List<String> compare(List<String> keys) {
        return keys;
    }

    @Override  //排列所有列的顺序 第三优先级  排列顺序有用户自己定义规则
    public int compare(String key, String key1) {
        return LetterUtils.getStringMax(key,key1);
    }

    
}
```
#### 2.  定义 ReportSystemCompareMap 类。
```java
/**
 * Map<Integer,String>  Integer必需从 0 自增长。
 */
public class ReportSystemCompareMap {
    public static Map<Integer,String> getSystemCompareMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"运单号码");
        map.put(1,"到货时间");
        map.put(2,"开票日期");
        map.put(3,"滞留天数");
        map.put(4,"开票部门");
        map.put(5,"当前部门");
        map.put(6,"到达部门");
        map.put(7,"提付应收合计");
        map.put(8,"转货地");
        map.put(9,"发货人");
        map.put(10,"发货手机");
        map.put(11,"发货地址");
        map.put(12,"收货人");
        map.put(13,"收货手机");
        map.put(14,"收货地址");
        map.put(15,"关联单号");
        map.put(16,"货物名称");
        map.put(17,"运单数量");
        map.put(18,"件数");
        map.put(19,"重量");
        map.put(20,"体积");
        map.put(21,"货号");
        map.put(22,"会员编号");
        map.put(23,"代收款");
        map.put(24,"代收运费");
        map.put(25,"提付运费");
        map.put(26,"现付运费");
        map.put(27,"现付月结运费");
        map.put(28,"提付月结运费");
        map.put(29,"回单运费");
        map.put(30,"扣付运费");
        map.put(31,"运费");
        map.put(32,"运费结算方式");
        map.put(33,"保值费");
        map.put(34,"提付保值费");
        map.put(35,"现付保值费");
        map.put(36,"月结保值费");
        map.put(37,"回单付保值费");
        map.put(38,"垫付费");
        map.put(39,"现返垫付费");
        map.put(40,"欠返垫付费");
        map.put(41,"接货费");
        map.put(42,"接货费结算方式");
        map.put(43,"提付接货费");
        map.put(44,"现付接货费");
        map.put(45,"月结接货费");
        map.put(46,"回单付接货费");
        map.put(47,"送货费");
        map.put(48,"送货费结算方式");
        map.put(49,"提付送货费");
        map.put(50,"现付送货费");
        map.put(51,"月结送货费");
        map.put(52,"月结送货费");
        map.put(53,"回单付送货费");
        map.put(54,"中转费");
        map.put(55,"中转费结算方式");
        map.put(56,"提付中转费");
        map.put(57,"现付中转费");
        map.put(58,"月结中转费");
        map.put(59,"月结中转费");
        map.put(60,"回单付中转费");
        map.put(61,"佣金");
        map.put(62,"佣金结算方式");
        map.put(63,"现返佣金");
        map.put(64,"欠返佣金");
        map.put(65,"保费");
        map.put(66,"短途费");
        map.put(67,"提付合计");
        map.put(68,"现付合计");
        map.put(69,"现付月结合计");
        map.put(70,"提付月结");
        map.put(71,"回单合计");
        map.put(72,"扣付合计");
        map.put(73,"费用合计");
        map.put(74,"总值");
        map.put(75,"工本费");
        map.put(76,"是否转货");
        map.put(77,"是否送货");
        map.put(78,"是否带回单");
        map.put(79,"业务员");
        map.put(80,"录单员");
        map.put(81,"客户备注");
        map.put(82,"网点名称");
        map.put(83,"回款日期");
        map.put(84,"当日收入");
        map.put(85,"当日支出");
        map.put(86,"当日应回");
        map.put(87,"现付收入");
        map.put(88,"提付收入");
        map.put(89,"代收款收入");
        map.put(90,"代收运费收入");
        map.put(91,"垫付费收入");
        map.put(92,"月结收入");
        map.put(93,"提付月结收入");
        map.put(94,"回单付收入");
        map.put(95,"日常支出");
        map.put(96,"佣金支出");
        map.put(97,"中转费支出");
        map.put(98,"到达部门");
        map.put(99,"总票数");
        map.put(100,"总件数");
        map.put(101,"总运费");
        map.put(102,"到货票数");
        map.put(103,"到货件数");
        map.put(104,"到货运费");
        map.put(105,"开票网店滞留票数");
        map.put(106,"开票网店滞留件数");
        map.put(107,"开票部门滞留运费");
        map.put(108,"库区滞留票数");
        map.put(109,"库区滞留件数");
        map.put(110,"库区滞留运费");
        map.put(111,"分拨在途票数");
        map.put(112,"分拨在途件数");
        map.put(113,"分拨在途运费");
        return map;
    }
}
```
#### 3.处理报表数据及合计数据并把数据给 table
```java
   List<Object> dates = JsonHelper.setJsonFormat(new NormalReportFormat(String type)).jsonReportDateToMapList(Sttring reportJson);
   List<Object> total = JsonHelper.setJsonFormat(new NormalReportFormat(String type)).jsonReportTotalToMapList(Sttring totalJson);
   MapTableData tableData = MapTableData.create(mCurrentReportType,dates);
   MapTableData totalData = MapTableData.create("",total);
   table.setData(tableData,totalData);
```

#### 4.注意事项
通过 Json 直接生成报表数据 reportJson 必须为 JSONArray  totalJson必须为 JSONObject  且生成的类须一致，这里没有校验是否一致。
不一致显示会有问题。


