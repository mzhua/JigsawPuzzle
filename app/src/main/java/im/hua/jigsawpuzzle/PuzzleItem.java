package im.hua.jigsawpuzzle;

import android.graphics.Bitmap;

/**
 * Created by hua on 2017/5/7.
 */

public class PuzzleItem {
    private int mX;
    private int mY;
    private Bitmap mBitmap;
    private int mOriginPosition;
    private int mPosition;

    public PuzzleItem() {
    }

    public PuzzleItem(int x, int y, Bitmap bitmap, int position) {
        mX = x;
        mY = y;
        mBitmap = bitmap;
        mPosition = position;
    }

    public PuzzleItem(int x, int y, Bitmap bitmap) {
        mX = x;
        mY = y;
        mBitmap = bitmap;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getOriginPosition() {
        return mOriginPosition;
    }

    public void setOriginPosition(int originPosition) {
        mOriginPosition = originPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PuzzleItem item = (PuzzleItem) o;

        if (mX != item.mX) return false;
        if (mY != item.mY) return false;
        if (mOriginPosition != item.mOriginPosition) return false;
        if (mPosition != item.mPosition) return false;
        return mBitmap != null ? mBitmap.equals(item.mBitmap) : item.mBitmap == null;

    }

    @Override
    public int hashCode() {
        int result = mX;
        result = 31 * result + mY;
        result = 31 * result + (mBitmap != null ? mBitmap.hashCode() : 0);
        result = 31 * result + mOriginPosition;
        result = 31 * result + mPosition;
        return result;
    }
}
