package com.hua.beautifulimage.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.entity.Pictures;
import com.hua.beautifulimage.utils.Constants;
import com.hua.beautifulimage.utils.ImageLoaderUtils;

import java.util.List;

/**
 * 图片列表适配器
 */
public class ShowPictureAdapter extends RecyclerView.Adapter<ShowPictureAdapter.ShowPictureHolder> {

    private Context mContext;
    private List<Pictures.Picture> mList;
    private OnItemClickListener mOnItemClickListener;


    public ShowPictureAdapter(Context mContext, List<Pictures.Picture> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ShowPictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_picture, parent, false);
        return new ShowPictureHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShowPictureHolder holder, final int position) {
        String uri = Constants.BASE_IMAGE_URL + mList.get(position).getSrc();
        ImageLoaderUtils.imageLoader(uri, holder.picture, holder.progressBar);
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.picture, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class ShowPictureHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        ImageView picture;

        ProgressBar progressBar;

        public ShowPictureHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_show_picture_card_view);
            picture = (ImageView) itemView.findViewById(R.id.item_show_picture_img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_show_picture_progress_bar);
        }
    }
}
