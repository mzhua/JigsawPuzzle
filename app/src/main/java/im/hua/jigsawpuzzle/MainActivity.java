package im.hua.jigsawpuzzle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.utils.image.ImageProvider;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_TAKE_PICTURE = 1233;
    public static final int REQUEST_CODE_PICK_FROM_GALLERY = 1234;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.fab_camera)
    FloatingActionButton mFabCamera;
    @BindView(R.id.fab_gallery)
    FloatingActionButton mFabGallery;
    @BindView(R.id.image_view)
    ImageView mImageView;

    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initSpinner();
    }

    private void initSpinner() {
        mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, new String[]{"2x2", "3x3", "4x4"}));
    }

    @OnClick(R.id.fab_camera)
    public void takePicture(View view) {
        mImagePath = ImageProvider.takePicture(this, REQUEST_CODE_TAKE_PICTURE);
    }

    @OnClick(R.id.fab_gallery)
    public void pickFromGallery(View view) {
        ImageProvider.pickFromLocal(this, REQUEST_CODE_PICK_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKE_PICTURE:
                    mImageView.setImageURI(Uri.parse(mImagePath));
                    break;
                case REQUEST_CODE_PICK_FROM_GALLERY:
                    mImagePath = ImageProvider.resolveLocalResult(this, data);
                    mImageView.setImageURI(Uri.parse(mImagePath));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start:
                if (!TextUtils.isEmpty(mImagePath)) {
                    Intent intent = new Intent(this, PuzzleActivity.class);
                    intent.putExtra(PuzzleActivity.EXTRA_KEY_IMAGE_PATH, mImagePath);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
