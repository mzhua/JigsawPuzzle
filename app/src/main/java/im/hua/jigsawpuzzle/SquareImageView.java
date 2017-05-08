package im.hua.jigsawpuzzle;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by hua on 2017/5/7.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {
    private static final int BASE_ORIENTATION_HORIZONTAL = 0;

    private static final int BASE_ORIENTATION_VERTICAL = 1;

    private int mBaseOrientation = BASE_ORIENTATION_VERTICAL;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.SquareImageView);
        mBaseOrientation = ta.getInt(R.styleable.SquareImageView_baseOrientation, BASE_ORIENTATION_HORIZONTAL);
        ta.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = 0;
        switch (mBaseOrientation) {
            case BASE_ORIENTATION_VERTICAL:
                size = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case BASE_ORIENTATION_HORIZONTAL:
                size = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }
        setMeasuredDimension(size, size);
    }
}
