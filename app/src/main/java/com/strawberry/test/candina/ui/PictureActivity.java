package com.strawberry.test.candina.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.strawberry.test.candina.R;

public class PictureActivity extends AppCompatActivity {
    private static final String EXTRA_IMAGE_RESOURCE = "EXTRA_IMAGE_RESOURCE";

    ImageView imagePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        int pictureResourse = getIntent().getIntExtra(EXTRA_IMAGE_RESOURCE, 0);
        imagePicture = findViewById(R.id.image_picture);
        imagePicture.setImageResource(pictureResourse);
    }
}
