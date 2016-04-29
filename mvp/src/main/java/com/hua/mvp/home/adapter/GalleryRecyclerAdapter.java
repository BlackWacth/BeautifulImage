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
import android.widget.TextView;

import com.hua.mvp.R;
import com.hua.mvp.global.C;
import com.hua.mvp.home.adapter.listener.OnItemClickListener;
import com.hua.mvp.home.entity.Gallery;
import com.hua.mvp.utils.ImageLoaderUtils;

import java.util.List;

/**
 *
 */
public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.GalleryRecyclerHolder>{

    private List<Gallery.Tngou> mList;
    private OnItemClickListener<Gallery.Tngou> mOnItemClickListener;

    public GalleryRecyclerAdapter(Context mContext, List<Gallery.Tngou> mList) {
        this.mList = mList;
        setHasStableIds(true);
    }

    @Override
    public GalleryRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryRecyclerHolder holder, final int position) {
        final Gallery.Tngou tngou = mList.get(position);
        holder.title.setText(tngou.getTitle() + " : " + position);
        String imageUrl = C.ImageUrl.BASE_IMAGE_URL + tngou.getImg();
        ImageLoaderUtils.imageLoader(imageUrl, holder.img, holder.progressBar);

        if(mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position, tngou);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<Gallery.Tngou> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class GalleryRecyclerHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        CardView cardView;
        ProgressBar progressBar;

        public GalleryRecyclerHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
            cardView = (CardView) itemView.findViewById(R.id.item_card_view);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }
}
