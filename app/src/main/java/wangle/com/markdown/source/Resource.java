package wangle.com.markdown.source;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by 忘了12138 on 2018/3/1.
 */

/**
 * 资源类，目前是从raw目录下读取md文件
 */
public class Resource {
    public static InputStream getResource(Context context, int resId) {
        final InputStream stream = context.getResources().openRawResource(resId);
        return stream;
    }

    public static InputStream getResource(Context context, String filePath) {
        return null;
    }
}
