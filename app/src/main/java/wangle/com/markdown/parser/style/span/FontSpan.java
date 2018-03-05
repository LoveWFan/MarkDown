package wangle.com.markdown.parser.style.span;

import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.StyleSpan;

/**
 * Created by 忘了12138 on 2018/3/1.
 */

/**
 * 继承于StyleSpan
 * 处理字体大小以及颜色的Span
 */
public class FontSpan extends StyleSpan implements ParcelableSpan {
    private float mScaleFactor;//字体放大因子
    private int mColor;//字体颜色
    public FontSpan(float scaleFactor,int color,int style) {
        super(style);
        this.mScaleFactor = scaleFactor;
        this.mColor = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        updateMeasureState(ds);
        if (mColor >= 0){
            ds.setColor(mColor);
        }

    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        super.updateMeasureState(paint);
        float textSize = paint.getTextSize() * mScaleFactor;
        paint.setTextSize(textSize);
    }
}
