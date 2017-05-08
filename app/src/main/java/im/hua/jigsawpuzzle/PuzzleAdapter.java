package im.hua.jigsawpuzzle;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hua on 2017/5/7.
 */

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.ViewHolder> {
    private List<PuzzleItem> mPuzzleItems;
    private int mSize;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, PuzzleItem item);
    }

    public PuzzleAdapter() {
    }

    public PuzzleAdapter(List<PuzzleItem> puzzleItems) {
        mPuzzleItems = puzzleItems;
        mSize = (int) Math.pow(mPuzzleItems.size(), 0.5);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puzzle, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PuzzleItem item = mPuzzleItems.get(position);
        Bitmap bitmap = item.getBitmap();
        holder.mIvPuzzleItem.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fromPosition = holder.getAdapterPosition();
                int toPosition = getBlankItemPosition();
                Log.d("PuzzleAdapter", fromPosition + ":" + toPosition);
                if (isMoveable(holder.getAdapterPosition())) {
                    swap(item);
//                        notifyItemMoved(fromPosition, toPosition);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private boolean isMoveable(int position) {
        int blankItemPosition = getBlankItemPosition();
        if (Math.abs(blankItemPosition - position) == mSize) {
            return true;
        }

        //同行相差为1
        if ((blankItemPosition / mSize == position / mSize) && Math.abs(blankItemPosition - position) == 1) {
            return true;
        }

        return false;
    }

    private void swap(PuzzleItem item) {
        int blankPosition = getBlankItemPosition();
        int itemPosition = mPuzzleItems.indexOf(item);

        PuzzleItem blankItem = getBlankItem();

        PuzzleItem swap = new PuzzleItem();
        swap.setBitmap(blankItem.getBitmap());
        swap.setX(blankItem.getX());
        swap.setY(blankItem.getY());
        swap.setPosition(blankItem.getPosition());
        swap.setOriginPosition(blankItem.getOriginPosition());

        mPuzzleItems.set(itemPosition, blankItem);
        mPuzzleItems.set(blankPosition, item);
    }

    private int getBlankItemPosition() {
        return mPuzzleItems.indexOf(getBlankItem());
    }

    private PuzzleItem getBlankItem() {
        for (PuzzleItem puzzleItem : mPuzzleItems) {
            if (puzzleItem.getBitmap() == null) {
                return puzzleItem;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return null == mPuzzleItems ? 0 : mPuzzleItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_puzzle_item)
        ImageView mIvPuzzleItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
