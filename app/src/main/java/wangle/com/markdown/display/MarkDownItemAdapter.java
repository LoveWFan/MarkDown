package wangle.com.markdown.display;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import wangle.com.markdown.R;
import wangle.com.markdown.parser.style.image.MyAppCompatTextView;

/**
 * item显示适配器
 */
public class MarkDownItemAdapter extends RecyclerView.Adapter<MarkDownItemAdapter.ViewHolder> {

    private final Context mContext;
    private List<Spannable> mData;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyAppCompatTextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textView);
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
        String url = "https://static.baydn.com/media/media_store/image/f1672263006c6e28bb9dee7652fa4cf6.jpg";
        Glide.with(holder.itemView.getContext())
                .load(url)
                .into(holder.mTextView.getTarget());

    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

}
