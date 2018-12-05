package com.sinotech.view.form.data.format.count;

import java.math.BigDecimal;

/**
 * Created by huang on 2017/11/6.
 */

public class DecimalCountFormat<T> implements ICountFormat<T,Double> {

    private BigDecimal totalBigCount = new BigDecimal(0);

    @Override
    public void count(T t) {
        Number number = (Number) t;
        totalBigCount = totalBigCount.add(new BigDecimal(number.toString()));
    }

    @Override
    public Double getCount() {
        return totalBigCount.doubleValue();
    }


    @Override
    public String getCountString() {
        return  totalBigCount.toString();
    }

    @Override
    public void clearCount() {
        totalBigCount = new BigDecimal(0);
    }
}
