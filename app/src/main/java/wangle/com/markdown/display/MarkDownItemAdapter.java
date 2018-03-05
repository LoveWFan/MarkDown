package wangle.com.markdown.display;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import wangle.com.markdown.R;
import wangle.com.markdown.parser.style.image.MyAppCompatTextView;
import wangle.com.markdown.util.DeviceUtils;

/**
 * item显示适配器
 */
public class MarkDownItemAdapter extends RecyclerView.Adapter<MarkDownItemAdapter.ViewHolder> {

    private final Context mContext;
    private List<Spannable> mData;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyAppCompatTextView mTextView;
        private ProgressBar mProgressBar;
        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textView);
            mProgressBar = view.findViewById(R.id.progressBar);
        }
    }

    public MarkDownItemAdapter(Context context, List<Spannable> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position));
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        holder.mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //自定义Glide ViewTarget 实现ImageSpan后台线程填充
        ImageSpan[] imageSpans = mData.get(position).getSpans(0, mData.get(position).length(), ImageSpan.class);
        for (ImageSpan imageSpan : imageSpans){
            holder.mProgressBar.setVisibility(View.VISIBLE);
            holder.mTextView.setProgressBar(holder.mProgressBar);
            Glide.with(holder.itemView.getContext())
                    .load(imageSpan.getSource())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            setImageSpan(holder,resource);
                            return false;
                        }
                    })
                    .preload();
        }

    }


    @SuppressLint("NewApi")
    public void setImageSpan(ViewHolder holder,GlideDrawable resource) {
        int w = DeviceUtils.SCREEN_WIDTH_PIXELS;
        int hh=resource.getIntrinsicHeight();
        int ww=resource.getIntrinsicWidth() ;
        int high=hh*(w-50)/ww;
        Rect rect = new Rect(0, 20,w,high);
        resource.setBounds(rect);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(holder.mTextView.getText());
        ImageSpan imageSpan = new ImageSpan(resource,ImageSpan.ALIGN_BASELINE);
        spannableStringBuilder.setSpan(imageSpan,0,spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(holder.mTextView.getText());
        spannableStringBuilder.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTextView.setText(spannableStringBuilder);
        if (holder.mProgressBar != null){
            holder.mProgressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

}
