package com.hua.beautifulimage.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.entity.Gallery;
import com.hua.beautifulimage.utils.Constants;
import com.hua.beautifulimage.utils.ImageLoaderUtils;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageRecyclerHolder>{

    private Context mContext;
    private List<Gallery.Tngou> mList;
    private OnItemClickListener mOnItemClickListener;

    public ImageRecyclerAdapter(Context mContext, List<Gallery.Tngou> mList) {
        this.mContext = mContext;
        this.mList = mList;
        setHasStableIds(true);
    }

    @Override
    public ImageRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new ImageRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRecyclerHolder holder, final int position) {
        holder.title.setText(mList.get(position).getTitle() + " : " + position);
        String imageUrl = Constants.BASE_IMAGE_URL+mList.get(position).getImg();
        ImageLoaderUtils.imageLoader(imageUrl, holder.img);

        if(mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
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

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    class ImageRecyclerHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        CardView cardView;

        public ImageRecyclerHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
            cardView = (CardView) itemView.findViewById(R.id.item_card_view);
        }
    }
}

