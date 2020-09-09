package com.sinotech.view.form.data.table;

import com.sinotech.view.form.data.column.ArrayColumn;
import com.sinotech.view.form.data.column.Column;
import com.sinotech.view.form.data.column.MapColumn;
import com.sinotech.view.form.data.format.IFormat;
import com.sinotech.view.form.data.format.draw.IDrawFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * the more diligent, the more luckier you are !
 * ---------------------------------------------
 * Created by StudyAbc on 2020/9/9  10:49.
 *
 * @desc
 */
public class SortTableData extends TableData<Object> {

    private MapTableData.FilterColumnIntercept mIntercept;

    /**
     * 创建Map表格数据
     *
     * @param tableName 表格名
     * @param mapList   Map数组
     */
    public static SortTableData create(String tableName, List<Object> mapList) {
        return create(tableName, mapList, null);
    }

    /**
     * 创建Map表格数据
     *
     * @param tableName 表格名
     * @param mapList   Map数组
     * @param keyFormat map中key格式化
     */
    public static SortTableData create(String tableName, List<Object> mapList, IFormat<String> keyFormat) {
        return create(tableName, mapList, keyFormat,"");
    }

    public static SortTableData create(String tableName, List<Object> mapList, IFormat<String> keyFormat, String detailsKey) {
        if (mapList != null) {
            List<Column> columns = new ArrayList<>();
            getSortMapColumn(columns, Column.INVAL_VALUE, Column.INVAL_VALUE, mapList, keyFormat, detailsKey);
            return new SortTableData(tableName, mapList, columns);
        }
        return null;
    }

    /**
     * 获取Map中所有字段
     * 暂时只支持Map中List数据解析 不支持数组[]
     */
    private static void getSortMapColumn(List<Column> columns, String fieldName, String parentKey, List<Object> mapList, IFormat<String> keyFormat, String detailsKey) {
        if (mapList != null && mapList.size() > 0) {
            Object o = mapList.get(0);
            if (o != null) {
                if (o instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) o;
                    //暂时只能解析json每个层级一个Array
                    boolean isOneArray = true;
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Object val = entry.getValue();
                        if (ArrayColumn.isList(val)) {
                            if (isOneArray) {
                                List<Object> list = ((List) val);
                                getSortMapColumn(columns, fieldName + key + ".", key, list, keyFormat, detailsKey);
                                isOneArray = false;
                            }
                        } else {
                            String columnName = keyFormat == null ? key : keyFormat.format(key);
                            if (detailsKey != null && detailsKey.length() > 0) {
                                if (detailsKey.equals(columnName)){
                                    continue;
                                }
                            }
                            MapColumn<Object> column = new MapColumn<>(columnName, fieldName + key);
                            columns.add(column);
                        }
                    }
                } else {
                    String columnName = keyFormat == null ? parentKey : keyFormat.format(parentKey);
                    MapColumn<Object> column = new MapColumn<>(columnName, fieldName, false);
                    columns.add(column);
                }
            }
        }
    }


    private SortTableData(String tableName, List t, List<Column> columns) {
        super(tableName, t, columns);
    }

    /**
     * 设置绘制样式
     *
     * @param drawFormat
     */
    public void setDrawFormat(IDrawFormat drawFormat) {
        for (Column column : getColumns()) {
            column.setDrawFormat(drawFormat);
        }
    }

    /**
     * 设置格式化
     */
    public void setFormat(IFormat format) {
        for (Column column : getColumns()) {
            column.setFormat(format);
        }
    }

    /**
     * 设置最小宽度
     *
     * @param minWidth
     */
    public void setMinWidth(int minWidth) {
        for (Column column : getColumns()) {
            column.setMinWidth(minWidth);
        }
    }

    /**
     * 设置最小高度
     *
     * @param minHeight
     */
    public void setMinHeight(int minHeight) {
        for (Column column : getColumns()) {
            column.setMinHeight(minHeight);
        }
    }

    /**
     * 过滤列拦截器
     * 拦截则不会表格显示出来该列
     */
    public interface FilterColumnIntercept {
        /**
         * 是否拦截
         *
         * @param column     列
         * @param columnName 列名
         * @return 是否拦截
         */
        boolean onIntercept(Column column, String columnName);
    }

    /**
     * 获取过滤拦截器
     *
     * @return 过滤拦截器
     */
    public MapTableData.FilterColumnIntercept getFilterColumnIntercept() {
        return mIntercept;
    }

    /**
     * 设置过滤拦截器
     * 拦截则不会表格显示出来该列
     */
    public void setFilterColumnIntercept(MapTableData.FilterColumnIntercept intercept) {
        this.mIntercept = intercept;
        if (mIntercept != null) {
            for (int i = getColumns().size() - 1; i >= 0; i--) {
                Column column = getColumns().get(i);
                if (mIntercept.onIntercept(column, column.getColumnName())) {
                    getColumns().remove(i);
                }
            }
        }
    }
}
