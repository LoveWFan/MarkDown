package wangle.com.markdown;

import android.app.Application;

import wangle.com.markdown.util.DeviceUtils;

/**
 * Created by 忘了12138 on 2018/3/1.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DeviceUtils.init(getApplicationContext());
    }

}
