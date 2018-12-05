package com.sinotech.view.form.pinyin;


import android.text.TextUtils;

/**
 * 汉字转拼音
 * Created by 江沨 on 2016/12/3.
 */
public class PinYin {
    public PinYin() {
    }

    /**
     * LWH 2016-12-3
     *
     * @param value hanzi
     * @return pinyin
     */
    public static String toPinYin(String value) {
        String pinYin = "";
        if (TextUtils.isEmpty(value)) {
            return pinYin;
        }
        char[] chars = value.toCharArray();
        for (char cha : chars) {
            pinYin += toPinyin(cha);
        }
        return pinYin;
    }

    /**
     * LWH 2016-12-10
     * 第一位是否是汉字
     *
     * @param value 字符串
     * @return true  是汉字
     */
    public static boolean isChenese(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        char[] chars = value.toCharArray();
        return isChinese(chars[0]);
    }

    /**
     * 1汉字=2字符长度
     * 算出总字符长度
     * @param value 字符串
     * @return 总字符长度
     */
    public static int lenthOfChar(String value) {
        int length = 0;
        char[] chars = value.toCharArray();
        for (char cha : chars) {
            if (isChinese(cha)) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * return pinyin if c is chinese in uppercase, String.valueOf(c) otherwise.
     */
    private static String toPinyin(char c) {
        if (isChinese(c)) {
            if (c == PinYinData.CHAR_12295) {
                return PinYinData.PINYIN_12295;
            } else {
                return PinYinData.PINYIN_TABLE[getPinyinCode(c)];
            }
        } else {
            return String.valueOf(c);
        }
    }

    /**
     * return whether c is chinese
     */
    public static boolean isChinese(char c) {
        return (PinYinData.MIN_VALUE <= c && c <= PinYinData.MAX_VALUE
                && getPinyinCode(c) > 0)
                || PinYinData.CHAR_12295 == c;
    }

    private static int getPinyinCode(char c) {
        int offset = c - PinYinData.MIN_VALUE;
        if (0 <= offset && offset < PinYinData.PINYIN_CODE_1_OFFSET) {
            return decodeIndex(PinYinCode1.PINYIN_CODE_PADDING, PinYinCode1.PINYIN_CODE, offset);
        } else if (PinYinData.PINYIN_CODE_1_OFFSET <= offset
                && offset < PinYinData.PINYIN_CODE_2_OFFSET) {
            return decodeIndex(PinYinCode2.PINYIN_CODE_PADDING, PinYinCode2.PINYIN_CODE,
                    offset - PinYinData.PINYIN_CODE_1_OFFSET);
        } else {
            return decodeIndex(PinYinCode3.PINYIN_CODE_PADDING, PinYinCode3.PINYIN_CODE,
                    offset - PinYinData.PINYIN_CODE_2_OFFSET);
        }
    }

    private static short decodeIndex(byte[] paddings, byte[] indexes, int offset) {
        //CHECKSTYLE:OFF
        int index1 = offset / 8;
        int index2 = offset % 8;
        short realIndex;
        realIndex = (short) (indexes[offset] & 0xff);
        //CHECKSTYLE:ON
        if ((paddings[index1] & PinYinData.BIT_MASKS[index2]) != 0) {
            realIndex = (short) (realIndex | PinYinData.PADDING_MASK);
        }
        return realIndex;
    }
}
