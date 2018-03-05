package wangle.com.markdown.parser.style;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;

import wangle.com.markdown.parser.style.span.FontSpan;
import wangle.com.markdown.parser.style.span.MyBulletSpan;

/**
 * Created by 忘了12138 on 2018/3/2.
 */
/**
 * 创建 标签 对应 格式 的实现类
 */
public class ParseStyleImpl implements IParseStyle{
    public static final float NORMAL_SCALEFACTOR = 1.0f; // H3
    public static final float H1_SCALEFACTOR = 2.5f; // H1
    public static final float H2_SCALEFACTOR = 2.0f; // H2
    public static final float H3_SCALEFACTOR = 1.5f; // H3

    @Override
    public SpannableStringBuilder parseH1(CharSequence charSequence) {
        return parseH(charSequence,H1_SCALEFACTOR,0xdf6B6D6B,Typeface.BOLD);
    }

    @Override
    public SpannableStringBuilder parseH2(CharSequence charSequence) {
        return parseH(charSequence,H2_SCALEFACTOR,0xdf6B6D6B,Typeface.BOLD);
    }

    @Override
    public SpannableStringBuilder parseH3(CharSequence charSequence) {
        return parseH(charSequence,H3_SCALEFACTOR,0xdf6B6D6B,Typeface.BOLD);
    }

    @Override
    public SpannableStringBuilder parseUl(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        MyBulletSpan bulletSpan  = new MyBulletSpan(40,0xdf6B6D6B);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder parseOl(CharSequence prefix,CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        MyBulletSpan bulletSpan  = new MyBulletSpan(66,0xdf6B6D6B,prefix);
        spannableStringBuilder.setSpan(bulletSpan, 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder parseItalic(CharSequence charSequence) {
        return parseH(charSequence,NORMAL_SCALEFACTOR,-1,Typeface.ITALIC);
    }

    @Override
    public SpannableStringBuilder parseBold(CharSequence charSequence) {
        return parseH(charSequence,NORMAL_SCALEFACTOR,0xdf6B6D6B,Typeface.BOLD);
    }

    @Override
    public SpannableStringBuilder parseBoldItalic(CharSequence charSequence) {
        return parseH(charSequence,NORMAL_SCALEFACTOR,0xdf6B6D6B,Typeface.BOLD_ITALIC);
    }

    @Override
    public SpannableStringBuilder parseImage(CharSequence title,CharSequence url) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(title.toString());

        ImageSpan imageSpan = new ImageSpan(new ColorDrawable(Color.TRANSPARENT),url.toString(),ImageSpan.ALIGN_BASELINE);
        spannableStringBuilder.setSpan(imageSpan,0,spannableStringBuilder.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public SpannableStringBuilder parseLink(CharSequence title, CharSequence url) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(title);
        spannableStringBuilder.setSpan(new URLSpan(url.toString()), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    @NonNull
    private SpannableStringBuilder parseH(CharSequence charSequence, float scaleFactor, int color,int style) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        FontSpan fontSpan  = new FontSpan(scaleFactor,color, style);
        spannableStringBuilder.setSpan(fontSpan, 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }
}
