package wangle.com.markdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;

import java.util.List;

import wangle.com.markdown.display.MarkDownItemAdapter;
import wangle.com.markdown.parser.MarkDownParser;
import wangle.com.markdown.source.Resource;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Spannable> spannableList = MarkDownParser.fromMarkdown(Resource.getResource(this, R.raw.source));

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MarkDownItemAdapter(this,spannableList));
    }
}
