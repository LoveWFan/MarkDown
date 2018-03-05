package wangle.com.markdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import wangle.com.markdown.display.MarkDownItemAdapter;
import wangle.com.markdown.parser.MarkDownParser;
import wangle.com.markdown.source.Resource;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Spannable> spannableList = MarkDownParser.fromMarkdown(Resource.getResource(this, R.raw.source));

        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MarkDownItemAdapter(this,spannableList));

        mProgressBar.setVisibility(View.GONE);
    }
}
