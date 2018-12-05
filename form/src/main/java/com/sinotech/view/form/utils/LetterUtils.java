package com.sinotech.view.form.utils;


import com.sinotech.view.form.pinyin.PinYin;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huang on 2017/11/1.
 */

public class LetterUtils {

    public static String ToNumberSystem26(int n) {
        String s = "";
        while (n > 0) {
            int m = n % 26;
            if (m == 0) m = 26;
            s = (char) (m + 64) + s;
            n = (n - m) / 26;
        }
        return s;
    }


    public static boolean isBasicType(Object data) {
        if (data != null && data instanceof Number) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(Object data) {
        return !(data instanceof Float || data instanceof Double);
    }

    public static int getStringMax(Object t1, Object t2) {
        String t1s = PinYin.toPinYin((String)t1);
        String t2s = PinYin.toPinYin((String)t2);
        int maxRuselt = 0;
        if (isChinese(t1s) && isChinese(t2s)){
            int minlength = t1s.length() > t2s.length() ?  t2s.length(): t1s.length() ;
            for (int i = 0; i < minlength; i++) {
                if (t1s.charAt(i) - t2s.charAt(i) != 0){
                    maxRuselt = t1s.charAt(i) - t2s.charAt(i);
                    break;
                }
            }
        }else {
            if (t1 != null && t2 != null){
                if (t1s.length() > 0 && t2s.length() > 0){
                    maxRuselt = t1s.charAt(0) - t2s.charAt(0);
                }
            }
        }
        return -maxRuselt ;

    }
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()){
            flg = true;
        }
        return flg;
    }

    public static int getNumMax(Object t1 ,Object t2){
        BigDecimal t1B = new BigDecimal(t1.toString());
        BigDecimal t2B = new BigDecimal(t2.toString());
        double maxRuselt = t1B.subtract(t2B).doubleValue();
        return  maxRuselt > 0 ? -1 : 1;
    }
}
