package im.hua.jigsawpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/5/7.
 */

public class PuzzleGenerator {

    private static void getPuzzleGenerator(List<PuzzleItem> puzzleItems) {
        int index = 0;
        int size = (int) Math.pow(puzzleItems.size(), 0.5);

        PuzzleItem emptyItem = puzzleItems.get(puzzleItems.size() - 1);

        for (int i = 0; i < puzzleItems.size(); i++) {
            index = (int) (Math.random() * size * size);
            emptyItem = swap(puzzleItems.get(index), emptyItem);
        }

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < puzzleItems.size(); i++) {
            data.add(puzzleItems.get(i).getPosition());
        }
        if (canResolve(data, emptyItem)) {
            return;
        } else {
            getPuzzleGenerator(puzzleItems);
        }

    }

    private static boolean canResolve(List<Integer> data,PuzzleItem emptyItem) {
        int blankPosition = emptyItem.getPosition();
        int size = (int) Math.pow(data.size(), 0.5);
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            if (((blankPosition - 1) / size) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                return getInversions(data) % 2 == 1;
            }
        }
    }

    private static int getInversions(List<Integer> data){
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }

    /**
     *
     * @param item
     * @param emptyItem
     * @return  the new empty item
     */
    private static PuzzleItem swap(PuzzleItem item, PuzzleItem emptyItem) {
        PuzzleItem swap = new PuzzleItem();
        swap.setBitmap(item.getBitmap());
        swap.setX(item.getX());
        swap.setY(item.getY());
        swap.setPosition(item.getPosition());

        item.setBitmap(emptyItem.getBitmap());
        item.setX(emptyItem.getX());
        item.setY(emptyItem.getY());
        item.setPosition(emptyItem.getPosition());

        emptyItem.setBitmap(swap.getBitmap());
        emptyItem.setX(swap.getX());
        emptyItem.setY(swap.getY());
        emptyItem.setPosition(swap.getPosition());

        return item;
    }

    public static List<PuzzleItem> generate(@IntRange(from = 2, to = 4) int size, String imagePath, int desireWidth, int desireHeight) {
        Bitmap bitmap = compressBitmap(imagePath, desireWidth, desireHeight);

        List<PuzzleItem> puzzleItems = new ArrayList<>();
        int position = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                PuzzleItem puzzleItem = cutItemFromOrigin(x, y, bitmap, size);
                int originPosition = position++;
                puzzleItem.setOriginPosition(originPosition);
                puzzleItem.setPosition(originPosition);
                puzzleItems.add(puzzleItem);
            }
        }
        PuzzleItem last = puzzleItems.get(size * size - 1);
        last.setBitmap(null);

        getPuzzleGenerator(puzzleItems);

        return puzzleItems;
    }

    private static Bitmap compressBitmap(String imagePath, int desireWidth, int desireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePath, options);

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSize = 1;
        while (outWidth / sampleSize > desireWidth || outHeight / sampleSize > desireHeight) {
            sampleSize *= 2;
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    private static PuzzleItem cutItemFromOrigin(int x, int y, @NonNull Bitmap originBitmap, @IntRange(from = 2, to = 4) int size) {
        int width = originBitmap.getWidth() / size;
        int height = originBitmap.getHeight() / size;
        Bitmap bitmap = Bitmap.createBitmap(originBitmap, x * width, y * height, width, height);
        return new PuzzleItem(x, y, bitmap);
    }

}
