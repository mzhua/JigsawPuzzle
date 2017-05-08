package im.hua.jigsawpuzzle;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.hua.utils.screen.ScreenSizeUtil;

public class PuzzleActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_IMAGE_PATH = "imagePath";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mImagePath;

    private PuzzleAdapter mPuzzleAdapter;
    private List<PuzzleItem> mPuzzleItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        mImagePath = intent.getStringExtra(EXTRA_KEY_IMAGE_PATH);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = ScreenSizeUtil.getScreenSize(this).widthPixels;

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPuzzleItems = PuzzleGenerator.generate(3, mImagePath, mRecyclerView.getWidth(), mRecyclerView.getHeight());
                mPuzzleAdapter = new PuzzleAdapter(mPuzzleItems);
                mPuzzleAdapter.setOnItemClickListener(new PuzzleAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position, PuzzleItem item) {

                    }
                });
                mRecyclerView.setAdapter(mPuzzleAdapter);
            }
        });

    }

}
