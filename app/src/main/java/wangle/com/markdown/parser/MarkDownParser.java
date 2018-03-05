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

public class MarkDownParser {
    private BufferedReader mBufferedReader;
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
     *收集 String -> spannable
     * @return
     * @throws IOException
     */
    private List<Spannable> parse() throws IOException {
        List<Line> lineList = collect();
        return parse(lineList);
    }

    private List<Spannable> parse(List<Line> lineList) throws IOException {
        List<Spannable> spannableList = new ArrayList<>();
        for (Line line : lineList){
            if(mParseTagImpl.parseLine(line)){
                Spannable spannable = parseStyle(line);
                spannableList.add(spannable);
            }

        }
        return spannableList;
    }

    private Spannable parseStyle(Line line) {
        return line.getStyle();
    }

    private List<Line> collect() throws IOException{
        String lineSource;
        int lintCount=0;
        List<Line> lineList = new ArrayList<>();

        int maxLineCount = DeviceUtils.SCREEN_HEIGHT_PIXELS-DeviceUtils.STATUSBAR_HEIGHT_PIXELS / DeviceUtils.FONT_HEIGHT_PIXELS;
        while ((lineSource = mBufferedReader.readLine()) != null  && lintCount< maxLineCount) {
            Line l = new Line(lineSource);
            lineList.add(l);
        }
        return lineList;
    }
}
