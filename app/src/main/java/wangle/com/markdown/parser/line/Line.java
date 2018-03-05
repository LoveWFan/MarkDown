package wangle.com.markdown.parser.line;

import android.text.SpannableStringBuilder;

/**
 * Created by 忘了12138 on 2018/3/1.
 */
/**
 * 代表每一行文本
 *
 */
public class Line {

    public static final int LINE_NORMAL = 0; // 普通文本

    public static final int LINE_TYPE_H1 = 1; // H1
    public static final int LINE_TYPE_H2 = 2; // H2
    public static final int LINE_TYPE_H3 = 3; // H3

    public static final int LINE_TYPE_UL = 4; // 无序列表
    public static final int LINE_TYPE_OL = 5; // 有序列表


    private String mSource; // 源文本
    private SpannableStringBuilder mStyle; // 样式
    private int mType; // 种类

    public Line(String source) {
        this.mSource = source;
        mType = LINE_NORMAL;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public SpannableStringBuilder getStyle() {
        return mStyle;
    }

    public void setStyle(SpannableStringBuilder style) {
        mStyle = style;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
