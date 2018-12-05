package com.sinotech.view.form.data.format.sequence;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinotech.view.form.core.TableConfig;
import com.sinotech.view.form.data.format.IFormat;


/**
 * Created by huang on 2017/11/7.
 *
 *序号格式化
 */

public interface ISequenceFormat extends IFormat<Integer> {


   void draw(Canvas canvas, int sequence, Rect rect, TableConfig config);

}
