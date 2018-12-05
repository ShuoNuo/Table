package com.sinotech.view.form.data.format.sequence;


import com.sinotech.view.form.utils.LetterUtils;

/**
 * Created by huang on 2017/11/7.
 */

public class LetterSequenceFormat extends BaseSequenceFormat{

    @Override
    public String format(Integer position) {
        return LetterUtils.ToNumberSystem26(position);
    }
}
