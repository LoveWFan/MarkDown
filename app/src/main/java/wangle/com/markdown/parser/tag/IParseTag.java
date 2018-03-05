package wangle.com.markdown.parser.tag;

import wangle.com.markdown.parser.line.Line;

/**
 * Created by 忘了12138 on 2018/3/1.
 */

/**
 * 解析md文件 标签 的接口
 *
 */
public interface IParseTag {
    public static final int NORMAL = 0;// 普通文本
    public static final int H1 = 1;// H1
    public static final int H2 = 2;// H2
    public static final int H3 = 3;// H3
    public static final int UL = 4;// 无序列表
    public static final int OL = 5;// 有序列表
    public static final int ITALIC = 6;//斜体
    public static final int BOLD = 7;//粗体
    public static final int BOLD_ITALIC = 8;//粗斜体
    public static final int LINK = 9;//链接
    public static final int IMAGE = 10;//图片

    boolean parseNormal(Line line);

    boolean parseH1(Line line);

    boolean parseH2(Line line);

    boolean parseH3(Line line);

    boolean parseUl(Line line);

    boolean parseOl(Line line);

    boolean parseItalic(Line line);

    boolean parseBold(Line line);

    boolean parseBoldItalic(Line line);


    boolean parseLink(Line line);

    boolean parseImage(Line line);

    //解析一行
    boolean parseLine(Line line);
    //解析行内的标签
    boolean parseInLine(Line line);
}
