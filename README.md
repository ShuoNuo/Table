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
```java
  List<Object> list =  JsonHelper.setJsonFormat(new IJsonFormat() {
            @Override
            public String getKeyName(String key) {
                //获取列标题的名称  可以根据对应的字段返回对应的表题。
                return key;
            }

            @Override
            public boolean isShow(String key) {
                //根据 字段名来判断是否显示
                return true;
            }

            @Override
            public String getKeyValue(String key, Object value) {
                //根据 字段名来处理显示的数据   可用来格式化数据
                return (String) value;
            }

            @Override
            public int compare(String key, String key1) {
                //根据 字段名处理数据的先后顺序
                return 0;
            }
        }).jsonToMapList(String json);
MapTableData tableData = MapTableData.create("开票信息统计", list);
mReportTable.setTableData(tableData);
```
3. 设置数据可自定义合计  totaldate
```java
   table.setData(list,totaldate);
```

4. 注意事项
该图表改造默认接收 list 规则数据，不包括 类种类，可使用但可能有 bug;
totaldate  合计为默认为  String 类型  该 bean 应该与 List 中的实体类一致，否则无法解析


