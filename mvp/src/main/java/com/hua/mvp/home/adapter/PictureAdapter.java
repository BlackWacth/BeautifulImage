package com.hua.mvp.home.adapter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.hua.mvp.R;
import com.hua.mvp.global.C;
import com.hua.mvp.home.adapter.listener.OnItemClickListener;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.utils.ImageLoaderUtils;

import java.util.List;

/**
 * 图片适配器
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private Context mContext;
    private List<Pictures.Picture> mList;
    private OnItemClickListener<Pictures.Picture> mOnItemClickListener;


    public PictureAdapter(Context mContext, List<Pictures.Picture> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public List<Pictures.Picture> getList() {
        return mList;
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(final PictureHolder holder, final int position) {
        final Pictures.Picture picture = mList.get(position);
        String imageUrl = C.ImageUrl.BASE_IMAGE_URL + picture.getSrc();
        ImageLoaderUtils.imageLoader(imageUrl, holder.picture, holder.progressBar);
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.picture, position, picture);
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

    public void setOnItemClickListener(OnItemClickListener<Pictures.Picture> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class PictureHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        ImageView picture;

        ProgressBar progressBar;

        public PictureHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_show_picture_card_view);
            picture = (ImageView) itemView.findViewById(R.id.item_show_picture_img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_show_picture_progress_bar);
        }
    }
}
