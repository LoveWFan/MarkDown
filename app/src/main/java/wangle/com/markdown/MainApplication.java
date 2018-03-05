package wangle.com.markdown;

import android.app.Application;
import android.content.Context;

import wangle.com.markdown.util.DeviceUtils;

/**
 * Created by 忘了12138 on 2018/3/1.
 */
public class MainApplication extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        DeviceUtils.init(getApplicationContext());
        sContext = getApplicationContext();
    }
    public static Context getContext(){
        return sContext;
    }
}
