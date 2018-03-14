package wangle.com.markdown.parser;

import android.text.Spannable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import wangle.com.markdown.parser.line.Line;
import wangle.com.markdown.parser.tag.ParseTagImpl;
import wangle.com.markdown.util.DeviceUtils;

/**
 * Created by 忘了12138 on 2018/3/1.
 */

/**
 * MarkDown解析
 */
public class MarkDownParser {
    private BufferedReader mBufferedReader;
    //解析md文件 标签 的实现类
    private ParseTagImpl mParseTagImpl;

    MarkDownParser(BufferedReader mBufferedReader) {
        this.mBufferedReader = mBufferedReader;
        this.mParseTagImpl = new ParseTagImpl();
    }

    MarkDownParser(InputStream inputStream) {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    MarkDownParser(String text) {
        this(new BufferedReader(new StringReader(text == null ? "" : text)));
    }

    /**
     * 解析markdown文本并返回数据
     *
     * @param inputStream 输入流
     * @return 富文本列表
     */
    public static List<Spannable> fromMarkdown(InputStream inputStream) {
        MarkDownParser parser = new MarkDownParser(inputStream);
        try {
            return parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 收集 String -> spannable
     *
     * @return
     * @throws IOException
     */
    private List<Spannable> parse() throws IOException {
        List<Line> lineList = collect();
        return parse(lineList);
    }

    private List<Spannable> parse(List<Line> lineList) throws IOException {
        List<Spannable> spannableList = new ArrayList<>();
        for (Line line : lineList) {
            if (mParseTagImpl.parseLine(line)) {
                Spannable spannable = parseStyle(line);
                spannableList.add(spannable);
            }

        }
        return spannableList;
    }

    /**
     * 解析每一行的格式
     *
     * @param line 代表每一行
     * @return Spannable供TextView显示
     */
    private Spannable parseStyle(Line line) {
        return line.getStyle();
    }

    /**
     * 读取md文件，收集每一行的信息
     *
     * @return 包含每一行信息的列表
     * @throws IOException
     */
    private List<Line> collect() throws IOException {
        String lineSource;
        int lintCount = 0;
        List<Line> lineList = new ArrayList<>();

        int maxLineCount = DeviceUtils.SCREEN_HEIGHT_PIXELS - DeviceUtils.STATUSBAR_HEIGHT_PIXELS / DeviceUtils.FONT_HEIGHT_PIXELS;
        while ((lineSource = mBufferedReader.readLine()) != null && lintCount < maxLineCount) {
            Line l = new Line(lineSource);
            lineList.add(l);
        }
        return lineList;
    }
}
