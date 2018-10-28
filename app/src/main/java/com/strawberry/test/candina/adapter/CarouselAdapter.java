package com.strawberry.test.candina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strawberry.test.candina.R;
import com.strawberry.test.candina.model.InternalData;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselHolder> {
    private List<InternalData> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public CarouselAdapter(List<InternalData> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public CarouselHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_carousel, parent, false);
        return new CarouselHolder(view);
    }

    @Override
    public void onBindViewHolder(CarouselHolder holder, int position) {
        InternalData item = listData.get(position);
        holder.carouselImageView.setImageResource(item.getRandomPictureResource());
    }

    public void setListData(ArrayList<InternalData> listData) {
        this.listData.clear();
        this.listData.addAll(listData);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class CarouselHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View container;
        CircleImageView carouselImageView;

        public CarouselHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_carousel_root);
            carouselImageView = itemView.findViewById(R.id.item_carousel_image);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}
