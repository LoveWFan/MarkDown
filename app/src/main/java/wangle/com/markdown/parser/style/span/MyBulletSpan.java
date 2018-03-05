package wangle.com.markdown.parser.style.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

/**
 * Created by 忘了12138 on 2018/3/2.
 */

/**
 * 基本按照BulletSpan编写，实现有序和无序列表的显示
 */
public class MyBulletSpan implements LeadingMarginSpan, ParcelableSpan {
    //有序列表的前缀
    private CharSequence mOlPrefix = null;

    private final int mGapWidth;
    private final boolean mWantColor;
    private final int mColor;

    private static final int BULLET_RADIUS = 10;
    private static Path sBulletPath = null;
    public static final int STANDARD_GAP_WIDTH = 2;

    public MyBulletSpan() {
        mGapWidth = STANDARD_GAP_WIDTH;
        mWantColor = false;
        mColor = 0;
    }

    public MyBulletSpan(int gapWidth) {
        mGapWidth = gapWidth;
        mWantColor = false;
        mColor = 0;
    }

    public MyBulletSpan(int gapWidth, int color) {
        mGapWidth = gapWidth;
        mWantColor = true;
        mColor = color;
    }


    public MyBulletSpan(int gapWidth, int color,CharSequence prefix) {
        mGapWidth = gapWidth;
        mWantColor = true;
        mColor = color;
        mOlPrefix = prefix;
    }

    public MyBulletSpan(Parcel src) {
        mGapWidth = src.readInt();
        mWantColor = src.readInt() != 0;
        mColor = src.readInt();
    }

    @Override
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    /** @hide */
    public int getSpanTypeIdInternal() {
        return 100;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /** @hide */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(mGapWidth);
        dest.writeInt(mWantColor ? 1 : 0);
        dest.writeInt(mColor);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (first){
            return 2 * BULLET_RADIUS + mGapWidth;
        }
        else return 2 * BULLET_RADIUS;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout l) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();
            int oldcolor = 0;

            if (mWantColor) {
                oldcolor = p.getColor();
                p.setColor(mColor);
            }
            if (mOlPrefix == null){
                p.setStyle(Paint.Style.FILL);

                if (c.isHardwareAccelerated()) {
                    if (sBulletPath == null) {
                        sBulletPath = new Path();
                        // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                        sBulletPath.addCircle(10.0f, 0.0f, 1.2f * BULLET_RADIUS, Path.Direction.CW);
                    }

                    c.save();
                    c.translate(x + dir * BULLET_RADIUS, (top + bottom) / 2.0f);
                    c.drawPath(sBulletPath, p);
                    c.restore();
                } else {
                    c.drawCircle(x + dir * BULLET_RADIUS, (top + bottom) / 2.0f, BULLET_RADIUS, p);
                }

                if (mWantColor) {
                    p.setColor(oldcolor);
                }
            }else{
                p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//粗体
                c.drawText(mOlPrefix.toString(), x + dir * BULLET_RADIUS,baseline, p);
            }

            p.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//正常
            p.setStyle(style);
        }
    }
}
