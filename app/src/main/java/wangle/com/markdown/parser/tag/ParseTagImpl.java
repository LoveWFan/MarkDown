package wangle.com.markdown.parser.tag;

import android.text.SpannableStringBuilder;
import android.util.SparseArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wangle.com.markdown.parser.line.Line;
import wangle.com.markdown.parser.style.IParseStyle;
import wangle.com.markdown.parser.style.ParseStyleImpl;

/**
 * Created by 忘了12138 on 2018/3/1.
 */
/**
 * 解析md文件 标签 的接口
 *
 */
public class ParseTagImpl implements IParseTag {


    private static final Matcher sH1 = Pattern.compile("^\\s*#\\s*([^#]*)$").matcher("");
    private static final Matcher sH2 = Pattern.compile("^\\s*#{2}\\s*([^#]*)$").matcher("");
    private static final Matcher sH3 = Pattern.compile("^\\s*#{3}\\s*([^#]*)$").matcher("");

    private static final Matcher sUl = Pattern.compile("^\\s*[-]\\s+(.*)").matcher("");
    private static final Matcher sOl = Pattern.compile("^\\s*(\\d+\\.)\\s+(.*)").matcher("");

    private static final Matcher sItalic = Pattern.compile("[^*]*((\\*)([^*].*?)\\2)").matcher("");
    private static final Matcher sBold = Pattern.compile("[^*]*((\\*)\\2([^*].*?)\\2\\2)").matcher("");
    private static final Matcher sBoldItalic = Pattern.compile("[^*]*((\\*)\\2\\2([^*].*?)\\2\\2\\2)").matcher("");
    private static final Matcher sLink = Pattern.compile(".*?(\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");
    private static final Matcher sImage = Pattern.compile(".*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");

    private static final SparseArray<Matcher> sMatcher = new SparseArray<>();// matcher缓冲区

    private IParseStyle mParseStyle = new ParseStyleImpl();
    static {
        sMatcher.put(IParseTag.H1, sH1);
        sMatcher.put(IParseTag.H2, sH2);
        sMatcher.put(IParseTag.H3, sH3);
        sMatcher.put(IParseTag.UL, sUl);
        sMatcher.put(IParseTag.OL, sOl);
        sMatcher.put(IParseTag.ITALIC, sItalic);
        sMatcher.put(IParseTag.BOLD, sBold);
        sMatcher.put(IParseTag.BOLD_ITALIC, sBoldItalic);
        sMatcher.put(IParseTag.LINK, sLink);
        sMatcher.put(IParseTag.IMAGE, sImage);
    }


    @Override
    public boolean parseNormal(Line line) {
        line.setType(Line.LINE_NORMAL);
        line.setStyle(new SpannableStringBuilder(line.getSource()));
        parseInLine(line);
        line.setStyle(line.getStyle());
        return true;
    }

    @Override
    public boolean parseH1(Line line) {
        Matcher matcher = obtain(IParseTag.H1, line.getSource());
        if (matcher != null && matcher.find()) {
            line.setType(Line.LINE_TYPE_H1);
            line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));

            parseInLine(line);
            line.setStyle(mParseStyle.parseH1(line.getStyle()));
            return true;
        }
        return false;
    }

    private Matcher obtain(int tag, CharSequence source) {
        Matcher m = sMatcher.get(tag, null);
        if (m != null) {
            m.reset(source);
        }
        return m;
    }

    @Override
    public boolean parseH2(Line line) {
        Matcher matcher = obtain(IParseTag.H2, line.getSource());
        if (matcher != null && matcher.find()) {
            line.setType(Line.LINE_TYPE_H2);
            line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
            parseInLine(line);
            line.setStyle(mParseStyle.parseH2(line.getStyle()));
            return true;
        }
        return false;
    }

    @Override
    public boolean parseH3(Line line) {
        Matcher matcher = obtain(IParseTag.H3, line.getSource());
        if (matcher != null && matcher.find()) {
            line.setType(Line.LINE_TYPE_H3);
            line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
            parseInLine(line);
            line.setStyle(mParseStyle.parseH3(line.getStyle()));
            return true;
        }
        return false;
    }

    @Override
    public boolean parseUl(Line line) {
        Matcher matcher = obtain(IParseTag.UL, line.getSource());
        if (matcher != null && matcher.find()) {
            line.setType(Line.LINE_TYPE_UL);
            line.setStyle(SpannableStringBuilder.valueOf(matcher.group(1)));
            parseInLine(line);
            line.setStyle(mParseStyle.parseUl(line.getStyle()));
            return true;
        }
        return false;
    }

    @Override
    public boolean parseOl(Line line) {
        Matcher matcher = obtain(IParseTag.OL, line.getSource());
        if (matcher != null && matcher.find()) {
            line.setType(Line.LINE_TYPE_OL);
            line.setStyle(SpannableStringBuilder.valueOf(matcher.group(2)));
            parseInLine(line);
            line.setStyle(mParseStyle.parseOl(matcher.group(1),line.getStyle()));
            return true;
        }
        return false;
    }

    @Override
    public boolean parseItalic(Line line) {
        SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
        if (builder != null) {
            Matcher matcher = obtain(IParseTag.ITALIC, builder);
            while (matcher.find()) {
                SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
                builder.delete(matcher.start(1), matcher.end(1));
                builder.insert(matcher.start(1), mParseStyle.parseItalic(sb));
                parseItalic(line);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parseBold(Line line) {
        SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
        if (builder != null) {
            Matcher matcher = obtain(IParseTag.BOLD, builder);
            while (matcher.find()) {
                SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
                builder.delete(matcher.start(1), matcher.end(1));
                builder.insert(matcher.start(1), mParseStyle.parseBold(sb));
                parseBold(line);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parseBoldItalic(Line line) {
        SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
        if (builder != null){
            Matcher matcher = obtain(IParseTag.BOLD_ITALIC, builder);
            while (matcher.find()) {
                SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
                builder.delete(matcher.start(1), matcher.end(1));
                builder.insert(matcher.start(1), mParseStyle.parseBoldItalic(sb));
                parseBoldItalic(line);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean parseLink(Line line) {
        SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
        if (builder != null){
            Matcher matcher = obtain(IParseTag.LINK, builder);
            while (matcher.find()) {
                SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
                builder.delete(matcher.start(1), matcher.end(1));
                builder.insert(matcher.start(1), mParseStyle.parseLink(matcher.group(2),sb));
                parseLink(line);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean parseImage(Line line) {
        SpannableStringBuilder builder = (SpannableStringBuilder) line.getStyle();
        if (builder != null){
            Matcher matcher = obtain(IParseTag.IMAGE, builder);
            while (matcher.find()) {
                SpannableStringBuilder sb = (SpannableStringBuilder) builder.subSequence(matcher.start(3), matcher.end(3));
                builder.delete(matcher.start(1), matcher.end(1));
                builder.insert(matcher.start(1), mParseStyle.parseImage(matcher.group(2),sb));
                parseImage(line);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean parseLine(Line line) {
        boolean flag = parseH1(line);
        flag = flag || parseH2(line);
        flag = flag || parseH3(line);
        flag = flag || parseUl(line);
        flag = flag || parseOl(line);
        flag = flag || parseImage(line);
        flag = flag || parseLink(line);

        return flag || parseNormal(line);
    }

    @Override
    public boolean parseInLine(Line line) {
        boolean flag = parseImage(line);
        flag = flag || parseLink(line);
        flag = flag || parseBoldItalic(line);
        flag = flag || parseBold(line);
        flag = flag || parseItalic(line);
        return flag;
    }
}
