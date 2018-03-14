package wangle.com.markdown.parser.style;

import android.text.SpannableStringBuilder;

/**
 * Created by 忘了12138 on 2018/3/2.
 */

/**
 * 创建 标签 对应 格式 的接口
 */
public interface IParseStyle {

    SpannableStringBuilder parseH1(CharSequence charSequence);

    SpannableStringBuilder parseH2(CharSequence charSequence);

    SpannableStringBuilder parseH3(CharSequence charSequence);

    SpannableStringBuilder parseUl(CharSequence charSequence);

    SpannableStringBuilder parseOl(CharSequence prefix, CharSequence charSequence);

    SpannableStringBuilder parseItalic(CharSequence charSequence);

    SpannableStringBuilder parseBold(CharSequence charSequence);

    SpannableStringBuilder parseBoldItalic(CharSequence charSequence);

    SpannableStringBuilder parseImage(CharSequence title, CharSequence url);

    SpannableStringBuilder parseLink(CharSequence title, CharSequence url);
}
