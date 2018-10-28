package com.strawberry.test.candina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.strawberry.test.candina.R;
import com.strawberry.test.candina.model.InternalData;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    private List<InternalData> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public GalleryAdapter(List<InternalData> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gallery, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {
        InternalData item = listData.get(position);
        holder.galleryImageView.setImageResource(item.getPictureResource());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View container;
        RoundedImageView galleryImageView;

        public GalleryHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_gallery_root);
            galleryImageView = itemView.findViewById(R.id.item_gallery_image);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}
