package com.strawberry.test.candina.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.strawberry.test.candina.R;
import com.strawberry.test.candina.adapter.CarouselAdapter;
import com.strawberry.test.candina.adapter.GalleryAdapter;
import com.strawberry.test.candina.design.ItemOffsetDecoration;
import com.strawberry.test.candina.model.CarouselData;
import com.strawberry.test.candina.model.GalleryData;
import com.strawberry.test.candina.model.InternalData;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private static final String EXTRA_IMAGE_RESOURCE = "EXTRA_IMAGE_RESOURCE";
    RecyclerView listCarousel, listGallery;
    CarouselAdapter carouselAdapter;
    GalleryAdapter galleryAdapter;
    ArrayList carouselData, galleryData;
    Handler timerHandler;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        sqLiteDatabase = getApplicationContext().openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        carouselData = (ArrayList) CarouselData.getCarouselListData();
        listCarousel = findViewById(R.id.list_carousel);
        listCarousel.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        carouselAdapter = new CarouselAdapter(carouselData, getApplicationContext());
        listCarousel.addItemDecoration(itemDecoration);
        listCarousel.setAdapter(carouselAdapter);
        carouselAdapter.setItemClickCallback(new CarouselAdapter.ItemClickCallback() {
            @Override
            public void onItemClick(int p) {
                InternalData item = (InternalData) carouselData.get(p);
                int givenPictureResource = item.getRandomPictureResource();
                setPicture(givenPictureResource);
            }
        });
        galleryData = (ArrayList) GalleryData.getGalleryListData();
        listGallery = findViewById(R.id.list_gallery);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        listGallery.setLayoutManager(layoutManager);
        galleryAdapter = new GalleryAdapter(galleryData, getApplicationContext());
        listGallery.addItemDecoration(itemDecoration);
        listGallery.setAdapter(galleryAdapter);
        galleryAdapter.setItemClickCallback(new GalleryAdapter.ItemClickCallback() {
            @Override
            public void onItemClick(int p) {
                InternalData item = (InternalData) galleryData.get(p);
                int givenPictureResource = item.getPictureResource();
                setPicture(givenPictureResource);
            }
        });
        startChanger();
    }

    private void startChanger() {
        timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Here you can update your adapter data
                carouselData = (ArrayList) CarouselData.getCarouselListData();
                carouselAdapter.setListData(carouselData);
                carouselAdapter.notifyDataSetChanged();
                timerHandler.postDelayed(this, 5000); //run every second
            }
        };
        timerHandler.postDelayed(timerRunnable, 5000);
    }

    public void setPicture(int pictureResourse) {
        Intent intent = new Intent(GalleryActivity.this, PictureActivity.class);
        intent.putExtra(EXTRA_IMAGE_RESOURCE, pictureResourse);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            sqLiteDatabase.execSQL("DROP TABLE logTable");
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS logTable(USERNAME TEXT, PASSWORD TEXT)");
            Intent intent = new Intent(GalleryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
