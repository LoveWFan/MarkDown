package wangle.com.markdown.parser.style.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import wangle.com.markdown.util.DeviceUtils;

/**
 * Created by 忘了12138 on 2018/3/5.
 */

public class MyAppCompatTextView extends AppCompatTextView {

    private ViewTarget<MyAppCompatTextView, GlideDrawable> viewTarget;

    public MyAppCompatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewTarget = new ViewTarget<MyAppCompatTextView, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                MyAppCompatTextView myAppCompatTextView = getView();
                myAppCompatTextView.setSpan(resource);
            }
        };
    }

    public ViewTarget<MyAppCompatTextView, GlideDrawable> getTarget() {
        return viewTarget;
    }

    @SuppressLint("NewApi")
    public void setSpan(GlideDrawable resource) {
        int w = DeviceUtils.SCREEN_WIDTH_PIXELS;
        int hh=resource.getIntrinsicHeight();
        int ww=resource.getIntrinsicWidth() ;
        int high=hh*(w-50)/ww;
        Rect rect = new Rect(20, 20,w-30,high);
        resource.setBounds(rect);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getText());
        ImageSpan imageSpan = new ImageSpan(resource,ImageSpan.ALIGN_BASELINE);
        spannableStringBuilder.setSpan(imageSpan,1,spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(spannableStringBuilder);
    }

}